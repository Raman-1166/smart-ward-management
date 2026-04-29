package com.ward.system.controller.mvc;

import com.ward.system.model.Status;
import com.ward.system.service.ComplaintService;
import com.ward.system.service.UserService;
import com.ward.system.service.WardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private WardService wardService;

    @Autowired
    private UserService userService;

    @Autowired
    private com.ward.system.service.ServiceDirectoryService serviceDirectoryService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalUsers", userService.getAllUsers().size());
        model.addAttribute("totalComplaints", complaintService.getAllComplaintsList().size());
        model.addAttribute("pendingComplaints", complaintService.filterByStatus(Status.PENDING).size());
        model.addAttribute("resolvedComplaints", complaintService.filterByStatus(Status.RESOLVED).size());
        model.addAttribute("recentComplaints", complaintService.getAllComplaintsList());
        return "admin-dashboard";
    }

    // Ward Management
    @GetMapping("/wards")
    public String listWards(Model model) {
        model.addAttribute("wards", wardService.getAllWardsList());
        return "admin/ward-list";
    }

    @GetMapping("/wards/new")
    public String wardForm(Model model) {
        model.addAttribute("ward", new com.ward.system.model.Ward());
        return "admin/ward-form";
    }

    @GetMapping("/wards/edit/{id}")
    public String editWard(@PathVariable Long id, Model model) {
        com.ward.system.model.Ward ward = wardService.getWardById(id);
        if (ward == null) {
            return "redirect:/admin/wards?error=WardNotFound";
        }
        model.addAttribute("ward", ward);
        return "admin/ward-form";
    }

    @PostMapping("/wards/save")
    public String saveWard(@ModelAttribute com.ward.system.model.Ward ward) {
        wardService.saveWard(ward);
        return "redirect:/admin/wards";
    }

    @GetMapping("/wards/delete/{id}")
    public String deleteWard(@PathVariable Long id) {
        wardService.deleteWard(id);
        return "redirect:/admin/wards";
    }

    // Service Management for a Ward
    @GetMapping("/wards/{wardId}/services")
    public String listServices(@PathVariable Long wardId, Model model) {
        com.ward.system.model.Ward ward = wardService.getWardById(wardId);
        if (ward == null) {
            return "redirect:/admin/wards?error=WardNotFound";
        }
        model.addAttribute("ward", ward);
        model.addAttribute("services", serviceDirectoryService.getServicesByWard(wardId));
        return "admin/service-list";
    }

    @GetMapping("/wards/{wardId}/services/new")
    public String serviceForm(@PathVariable Long wardId, Model model) {
        com.ward.system.model.Ward ward = wardService.getWardById(wardId);
        if (ward == null) {
            return "redirect:/admin/wards?error=WardNotFound";
        }
        com.ward.system.model.ServiceEntity service = new com.ward.system.model.ServiceEntity();
        service.setWard(ward);
        model.addAttribute("service", service);
        model.addAttribute("wardId", wardId);
        model.addAttribute("categories", com.ward.system.model.Category.values());
        return "admin/service-form";
    }

    @PostMapping("/wards/{wardId}/services/save")
    public String saveService(@PathVariable Long wardId, @ModelAttribute com.ward.system.model.ServiceEntity service) {
        service.setWard(wardService.getWardById(wardId));
        serviceDirectoryService.saveService(service);
        return "redirect:/admin/wards/" + wardId + "/services";
    }

    @GetMapping("/wards/{wardId}/services/delete/{serviceId}")
    public String deleteService(@PathVariable Long wardId, @PathVariable Long serviceId) {
        serviceDirectoryService.deleteService(serviceId);
        return "redirect:/admin/wards/" + wardId + "/services";
    }

    // Global Service List
    @GetMapping("/services")
    public String listAllServices(Model model) {
        model.addAttribute("services", serviceDirectoryService.getAllServices());
        return "admin/service-list-all";
    }

    // Citizen/User Management
    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/user-list";
    }

    @GetMapping("/complaints")
    public String adminComplaints(@RequestParam(defaultValue = "0") int page, 
                                 @RequestParam(required = false) Status status,
                                 Model model) {
        if (status != null) {
            model.addAttribute("complaintsPage", complaintService.getComplaintsByStatus(status, PageRequest.of(page, 10)));
            model.addAttribute("currentStatus", status);
        } else {
            model.addAttribute("complaintsPage", complaintService.getAllComplaints(PageRequest.of(page, 10)));
        }
        return "admin-complaints";
    }

    @PostMapping("/complaints/{id}/status")
    public String updateComplaintStatus(@PathVariable Long id, @RequestParam Status status, jakarta.servlet.http.HttpServletRequest request) {
        complaintService.updateStatus(id, status);
        String referer = request.getHeader("Referer");
        if (referer != null) {
            return "redirect:" + referer;
        }
        return "redirect:/admin/complaints";
    }
}
