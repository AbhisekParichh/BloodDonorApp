package com.example.demo.controllers;

import com.example.demo.models.Donor;
import com.example.demo.services.DonorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
//@RequestMapping("/")
public class DonorController {

    @Autowired
    private DonorService donorService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    // Home page as landing page
    @GetMapping("/")
    public String showHomePage(Model model, @AuthenticationPrincipal OAuth2User principal) {
        if (principal != null) {
            model.addAttribute("name", principal.getAttribute("name"));
        }
        return "home";
    }
    
    // Handle form submission to add a donor and redirect to home
    @GetMapping("/donors")
    public String getAllDonors(Model model) {
        List<Donor> donors = donorService.getAllDonors();
        model.addAttribute("donors", donors);
        return "donors";
    }
    @PostMapping("/donors")
    public String addDonor(@RequestParam String name, @RequestParam String bloodType, @RequestParam String location, @RequestParam String contactInfo) {
        System.out.println("Processing donor registration for: " + name + " with contact: " + contactInfo);
        if (contactInfo != null && !contactInfo.startsWith("+")) {
            contactInfo = "+91" + contactInfo.replaceAll("[^0-9]", "");
        }
        System.out.println("Final contact info to save: " + contactInfo);
        Donor donor = new Donor(name, bloodType, location, contactInfo);
        donorService.addDonor(donor);
        return "redirect:/";  // Redirects to home page
    }
    @PostMapping("/delete/{id}")
    public String deleteDonor(@PathVariable Long id) {
        donorService.deleteDonor(id);
        return "redirect:/donors";
    }
    
    // Recipients page with search feature
    @GetMapping("/recipients")
    public String showRecipients(@RequestParam(value = "searchBy", required = false) String searchBy,
                                 @RequestParam(value = "query", required = false) String query,
                                 Model model) {
        List<Donor> recipients;
        if ("bloodType".equals(searchBy)) {
            recipients = donorService.findByBloodType(query);
        } else if ("location".equals(searchBy)) {
            recipients = donorService.findByLocation(query);
        } else {
            recipients = donorService.getAllDonors();
        }
        model.addAttribute("recipients", recipients);
        return "recipients";
    }
}


/*ackage com.example.demo.controllers;


import com.example.demo.models.Donor;
import com.example.demo.services.DonorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/donors")
public class DonorController {

    @Autowired
    private DonorService donorService;

    @GetMapping
    public String getAllDonors(Model model) {
        List<Donor> donors = donorService.getAllDonors();
        model.addAttribute("donors", donors);
        return "donors";
    }

    @PostMapping
    public String addDonor(@RequestParam String name, @RequestParam String bloodType,@RequestParam String location, @RequestParam String contactInfo) {
        Donor donor = new Donor(name, bloodType, location, contactInfo);
        donorService.addDonor(donor);
        return "redirect:/donors";
    }

    @PostMapping("/delete/{id}")
    public String deleteDonor(@PathVariable Long id) {
        donorService.deleteDonor(id);
        return "redirect:/donors";
    }
}

*/
/*
package com.example.demo.controllers;

import com.example.demo.models.Donor;
import com.example.demo.services.DonorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/donors")
public class DonorController {

    @Autowired
    private DonorService donorService;

    @GetMapping
    public String getAllDonors(Model model) {
        List<Donor> donors = donorService.getAllDonors();
        model.addAttribute("donors", donors);
        return "donors";
    }

    @PostMapping
    public String addDonor(@RequestParam String name, @RequestParam String bloodType, @RequestParam String location, @RequestParam String contactInfo) {
        Donor donor = new Donor(name, bloodType, location, contactInfo);
        donorService.addDonor(donor);
        return "redirect:/";  // Redirects to home page
    }

    @PostMapping("/delete/{id}")
    public String deleteDonor(@PathVariable Long id) {
        donorService.deleteDonor(id);
        return "redirect:/donors";
    }
}
*/
/*
@GetMapping("/donors")
public String showDonorForm() {
    return "donors"; // Render donors.html with the form
}

// Handle form submission to add a donor and redirect to home
@PostMapping
public String addDonor(@RequestParam String name, 
                       @RequestParam String bloodType,
                       @RequestParam String location, 
                       @RequestParam String contactInfo) {
    Donor donor = new Donor(name, bloodType, location, contactInfo);
    donorService.addDonor(donor);
    return "redirect:/"; // Redirect back to home page after form submission
}*/
