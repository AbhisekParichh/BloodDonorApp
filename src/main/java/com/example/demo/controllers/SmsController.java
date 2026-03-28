package com.example.demo.controllers;

import com.example.demo.services.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/sms")
public class SmsController {

    @Autowired
    private SmsService smsService;

    @PostMapping("/urgent")
    public ResponseEntity<?> sendUrgentAlert(@RequestBody Map<String, String> payload) {
        String contact = payload.get("contactInfo");
        String name = payload.get("donorName");
        String bloodType = payload.get("bloodType");
        String recipientName = payload.get("recipientName");
        String recipientPhone = payload.get("recipientPhone");

        String result = smsService.sendUrgentRequest(contact, name, bloodType, recipientName, recipientPhone);
        if ("SUCCESS".equals(result)) {
            return ResponseEntity.ok().body(Map.of("message", "SMS Alert Sent Successfully! (Check Terminal if in Mock Mode)"));
        } else {
            return ResponseEntity.status(500).body(Map.of("message", result));
        }
    }
}
