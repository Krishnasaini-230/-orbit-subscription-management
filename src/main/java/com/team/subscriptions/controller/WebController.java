package com.team.subscriptions.controller;

import com.team.subscriptions.model.*;
import com.team.subscriptions.service.BusinessService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.math.BigDecimal;

@Controller
public class WebController {
    private final BusinessService service;
    public WebController(BusinessService service){this.service=service;}

    @GetMapping("/") public String dashboard(Model m){
        m.addAttribute("active",service.activeSubscriptions()); m.addAttribute("collected",service.collected());
        m.addAttribute("outstanding",service.outstanding()); m.addAttribute("overdue",service.overdueCount());
        m.addAttribute("subscriptions",service.subscriptions()); m.addAttribute("invoices",service.invoices());
        return "dashboard";
    }
    @GetMapping("/customers") public String customers(Model m){m.addAttribute("customers",service.customers()); return "customers";}
    @PostMapping("/customers") public String saveCustomer(@Valid @ModelAttribute Customer c, RedirectAttributes ra){
        service.saveCustomer(c); ra.addFlashAttribute("success","Customer saved."); return "redirect:/customers";
    }
    @GetMapping("/products") public String products(Model m){m.addAttribute("products",service.products()); return "products";}
    @PostMapping("/products") public String saveProduct(@Valid @ModelAttribute Product p, RedirectAttributes ra){
        service.saveProduct(p); ra.addFlashAttribute("success","Product saved."); return "redirect:/products";
    }
    @GetMapping("/plans") public String plans(Model m){m.addAttribute("plans",service.plans()); return "plans";}
    @PostMapping("/plans") public String savePlan(@Valid @ModelAttribute Plan p, RedirectAttributes ra){
        service.savePlan(p); ra.addFlashAttribute("success","Plan saved."); return "redirect:/plans";
    }
    @GetMapping("/subscriptions") public String subscriptions(Model m){
        m.addAttribute("subscriptions",service.subscriptions()); m.addAttribute("customers",service.customers()); m.addAttribute("plans",service.plans());
        m.addAttribute("statuses",SubscriptionStatus.values()); return "subscriptions";
    }
    @PostMapping("/subscriptions") public String saveSubscription(@RequestParam Long customerId,@RequestParam Long planId,
      @RequestParam Integer quantity, RedirectAttributes ra){
        Subscription s=new Subscription(); s.setCustomer(service.customer(customerId)); s.setPlan(service.plan(planId)); s.setQuantity(quantity);
        s.setStatus(SubscriptionStatus.DRAFT); service.saveSubscription(s); ra.addFlashAttribute("success","Subscription created as Draft."); return "redirect:/subscriptions";
    }
    @PostMapping("/subscriptions/{id}/transition") public String transition(@PathVariable Long id,@RequestParam SubscriptionStatus status,RedirectAttributes ra){
        try {service.transition(id,status); ra.addFlashAttribute("success","Subscription updated.");}
        catch(RuntimeException e){ra.addFlashAttribute("error",e.getMessage());}
        return "redirect:/subscriptions";
    }
    @PostMapping("/subscriptions/{id}/invoice") public String createInvoice(@PathVariable Long id,RedirectAttributes ra){
        try {service.createInvoice(id); ra.addFlashAttribute("success","Invoice generated.");}
        catch(RuntimeException e){ra.addFlashAttribute("error",e.getMessage());}
        return "redirect:/";
    }
    @GetMapping("/invoices") public String invoices(Model m){m.addAttribute("invoices",service.invoices()); return "invoices";}
    @PostMapping("/invoices/{id}/payments") public String payment(@PathVariable Long id,@RequestParam BigDecimal amount,
      @RequestParam String method,@RequestParam(required=false) String note,RedirectAttributes ra){
        try {service.recordPayment(id,amount,method,note); ra.addFlashAttribute("success","Payment recorded.");}
        catch(RuntimeException e){ra.addFlashAttribute("error",e.getMessage());}
        return "redirect:/invoices";
    }
}
