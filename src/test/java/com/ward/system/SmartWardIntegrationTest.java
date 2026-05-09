package com.ward.system;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
public class SmartWardIntegrationTest {

        @Autowired
        private MockMvc mockMvc;

        private final ObjectMapper objectMapper = new ObjectMapper();

        private String citizenEmail = "citizen@test.com";
        private String citizenPassword = "password";
        private Long wardId = 1L;

        @BeforeEach
        void registerCitizen() throws Exception {
                // Register a citizen if not already present
                mockMvc.perform(post("/register")
                                .param("name", "Test Citizen")
                                .param("email", citizenEmail)
                                .param("password", citizenPassword)
                                .param("wardId", wardId.toString())
                                .param("role", "CITIZEN"))
                                .andExpect(status().is3xxRedirection());
        }

        @Test
        void fullWorkflow() throws Exception {
                // 1. Login as citizen
                ResultActions login = mockMvc.perform(post("/login")
                                .param("email", citizenEmail)
                                .param("password", citizenPassword))
                                .andExpect(status().is3xxRedirection());

                // Extract session cookie
                String session = login.andReturn().getResponse().getHeader("Set-Cookie");

                // 2. Get wards list (public endpoint)
                mockMvc.perform(get("/wards"))
                                .andExpect(status().isOk())
                                .andExpect(content().string(org.hamcrest.Matchers.containsString("Vijay Nagar")));

                // 3. Submit a complaint as citizen
                mockMvc.perform(post("/complaints")
                                .cookie(new Cookie("JSESSIONID", session))
                                .param("category", "GARBAGE")
                                .param("description", "Test complaint from integration")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                                .andExpect(status().isOk());

                // 4. Retrieve my complaints
                mockMvc.perform(get("/my-complaints")
                                .cookie(new Cookie("JSESSIONID", session)))
                                .andExpect(status().isOk())
                                .andExpect(content().string(org.hamcrest.Matchers
                                                .containsString("Test complaint from integration")));

                // 5. Submit feedback
                mockMvc.perform(post("/feedback")
                                .cookie(new Cookie("JSESSIONID", session))
                                .param("rating", "5")
                                .param("comment", "Great service")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                                .andExpect(status().isOk());

                // 6. Logout citizen
                mockMvc.perform(get("/logout"))
                                .andExpect(status().is3xxRedirection());

                // 7. Login as admin
                ResultActions adminLogin = mockMvc.perform(post("/login")
                                .param("email", "admin@smartward.com")
                                .param("password", "password"))
                                .andExpect(status().is3xxRedirection());
                String adminSession = adminLogin.andReturn().getResponse().getHeader("Set-Cookie");

                // 8. Admin: get all complaints
                mockMvc.perform(get("/admin/complaints")
                                .cookie(new Cookie("JSESSIONID", adminSession)))
                                .andExpect(status().isOk())
                                .andExpect(content().string(org.hamcrest.Matchers
                                                .containsString("Test complaint from integration")));

                // 9. Admin: analytics endpoint
                mockMvc.perform(get("/api/admin/analytics")
                                .cookie(new Cookie("JSESSIONID", adminSession)))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        }
}
