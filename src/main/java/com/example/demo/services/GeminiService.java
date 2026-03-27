package com.example.demo.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;
import java.util.List;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private static final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=";

    public String checkEligibility(String userQuestion) {
        String url = GEMINI_API_URL + apiKey.trim();
        RestTemplate restTemplate = new RestTemplate();
        
        String systemPrompt = "You are a highly knowledgeable medical assistant for a Blood Donation App. A user will ask if they can donate blood based on their specific condition, medication, or travel history. Answer clearly, concisely, and supportively. If they are eligible, encourage them to donate. If not, explain why and when they might be eligible. Keep your answer under 3 sentences.";
        String combinedPrompt = systemPrompt + "\n\nUser question: " + userQuestion;
        
        Map<String, Object> part = Map.of("text", combinedPrompt);
        Map<String, Object> content = Map.of("parts", List.of(part));
        Map<String, Object> requestBody = Map.of("contents", List.of(content));

        try {
            org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
            headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
            org.springframework.http.HttpEntity<Map<String, Object>> entity = new org.springframework.http.HttpEntity<>(requestBody, headers);
            
            Map response = restTemplate.postForObject(url, entity, Map.class);
            List<Map> candidates = (List<Map>) response.get("candidates");
            
            if (candidates == null || candidates.isEmpty()) {
                return "I apologize, but my AI safety filters prevented me from directly answering that medical question. Please consult a doctor or local blood bank database.";
            }
            
            Map contentMap = (Map) candidates.get(0).get("content");
            List<Map> parts = (List<Map>) contentMap.get("parts");
            return (String) parts.get(0).get("text");
        } catch (org.springframework.web.client.HttpStatusCodeException e) {
            e.printStackTrace();
            return "API Auth Error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString();
        } catch (Exception e) {
            e.printStackTrace();
            return "System Error: " + e.getMessage();
        }
    }
}
