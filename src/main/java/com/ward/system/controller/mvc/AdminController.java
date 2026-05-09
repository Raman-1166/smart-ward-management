package com.ward.system.controller.mvc;

import com.ward.system.model.Category;
import com.ward.system.model.ServiceEntity;
import com.ward.system.model.Status;
import com.ward.system.model.Ward;
import com.ward.system.service.ComplaintService;
import com.ward.system.service.ServiceDirectoryService;
import com.ward.system.service.UserService;
import com.ward.system.service.WardService;
import jakarta.servlet.http.HttpServletRequest;
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
    private ServiceDirectoryService serviceDirectoryService;

    // ─── Dashboard ────────────────────────────────────────────────────────────

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalUsers", userService.getAllUsers().size());
        model.addAttribute("totalComplaints", complaintService.getAllComplaintsList().size());
        model.addAttribute("pendingComplaints", complaintService.filterByStatus(Status.PENDING).size());
        model.addAttribute("resolvedComplaints", complaintService.filterByStatus(Status.RESOLVED).size());
        model.addAttribute("recentComplaints", complaintService.getAllComplaintsList());
        return "admin-dashboard";
    }

    // ─── Ward Management ──────────────────────────────────────────────────────

    @GetMapping("/wards")
    public String listWards(Model model) {
        model.addAttribute("wards", wardService.getAllWardsList());
        return "admin/ward-list";
    }

    @GetMapping("/wards/new")
    public String wardForm(Model model) {
        model.addAttribute("ward", new Ward());
        return "admin/ward-form";
    }

    @GetMapping("/wards/edit/{id}")
    public String editWard(@PathVariable Long id, Model model) {
        model.addAttribute("ward", wardService.getWardById(id));
        return "admin/ward-form";
    }

    @PostMapping("/wards/save")
    public String saveWard(@ModelAttribute Ward ward) {
        wardService.saveWard(ward);
        return "redirect:/admin/wards";
    }

    /** DELETE via POST form — avoids CSRF-vulnerable GET-based deletion */
    @PostMapping("/wards/delete/{id}")
    public String deleteWard(@PathVariable Long id) {
        wardService.deleteWard(id);
        return "redirect:/admin/wards";
    }

    // ─── Service Management ───────────────────────────────────────────────────

    @GetMapping("/wards/{wardId}/services")
    public String listServices(@PathVariable Long wardId, Model model) {
        model.addAttribute("ward", wardService.getWardById(wardId));
        model.addAttribute("services", serviceDirectoryService.getServicesByWard(wardId));
        return "admin/service-list";
    }

    @GetMapping("/wards/{wardId}/services/new")
    public String serviceForm(@PathVariable Long wardId, Model model) {
        Ward ward = wardService.getWardById(wardId);
        ServiceEntity service = new ServiceEntity();
        service.setWard(ward);
        model.addAttribute("service", service);
        model.addAttribute("wardId", wardId);
        model.addAttribute("categories", Category.values());
        return "admin/service-form";
    }

    @GetMapping("/wards/{wardId}/services/edit/{serviceId}")
    public String editService(@PathVariable Long wardId, @PathVariable Long serviceId, Model model) {
        model.addAttribute("service", serviceDirectoryService.getServiceById(serviceId));
        model.addAttribute("wardId", wardId);
        model.addAttribute("categories", Category.values());
        return "admin/service-form";
    }

    @PostMapping("/wards/{wardId}/services/save")
    public String saveService(@PathVariable Long wardId, @ModelAttribute ServiceEntity service) {
        service.setWard(wardService.getWardById(wardId));
        serviceDirectoryService.saveService(service);
        return "redirect:/admin/wards/" + wardId + "/services";
    }

    /** DELETE via POST form — avoids CSRF-vulnerable GET-based deletion */
    @PostMapping("/wards/{wardId}/services/delete/{serviceId}")
    public String deleteService(@PathVariable Long wardId, @PathVariable Long serviceId) {
        serviceDirectoryService.deleteService(serviceId);
        return "redirect:/admin/wards/" + wardId + "/services";
    }

    // ─── Global Service List ──────────────────────────────────────────────────

    @GetMapping("/services")
    public String listAllServices(Model model) {
        model.addAttribute("services", serviceDirectoryService.getAllServices());
        return "admin/service-list-all";
    }

    // ─── User Management ──────────────────────────────────────────────────────

    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/user-list";
    }

    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }

    // ─── Complaint Management ─────────────────────────────────────────────────

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
        model.addAttribute("statuses", Status.values());
        return "admin-complaints";
    }

    @PostMapping("/complaints/{id}/status")
    public String updateComplaintStatus(@PathVariable Long id,
                                        @RequestParam Status status,
                                        HttpServletRequest request) {
        complaintService.updateStatus(id, status);
        String referer = request.getHeader("Referer");
        return referer != null ? "redirect:" + referer : "redirect:/admin/complaints";
    }
}
