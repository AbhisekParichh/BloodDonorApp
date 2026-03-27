// package com.example.demo.controllers;

// import com.example.demo.models.Donor;
// import com.example.demo.services.DonorService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @Controller
// @RequestMapping("/recipients")
// public class RecipientController {

//     @Autowired
//     private DonorService donorService;
    

//     @GetMapping
//     public String showRecipients(@RequestParam(value = "searchBy", required = false) String searchBy,
//                                  @RequestParam(value = "query", required = false) String query,
//                                  Model model) {
//         List<Donor> recipients;
//         if ("bloodType".equals(searchBy)) {
//             recipients = donorService.findByBloodType(query);
//         } else if ("location".equals(searchBy)) {
//             recipients = donorService.findByLocation(query);
//         } else {
//             recipients = donorService.getAllDonors();
//         }
//         model.addAttribute("recipients", recipients);
//         return "recipients";
//     }
// }
