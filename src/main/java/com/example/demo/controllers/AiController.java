package com.example.demo.controllers;

import com.example.demo.services.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    @Autowired
    private GeminiService geminiService;

    @PostMapping("/check-eligibility")
    public ResponseEntity<Map<String, String>> checkEligibility(@RequestBody Map<String, String> payload) {
        String question = payload.get("question");
        if (question == null || question.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Question is required"));
        }
        
        String answer = geminiService.checkEligibility(question);
        return ResponseEntity.ok(Map.of("answer", answer));
    }
}
