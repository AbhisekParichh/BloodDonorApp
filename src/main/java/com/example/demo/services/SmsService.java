package com.example.demo.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Service
public class SmsService {

    @Value("${twilio.account.sid:}")
    private String accountSid;

    @Value("${twilio.auth.token:}")
    private String authToken;

    @Value("${twilio.phone.number:}")
    private String fromPhoneNumber;

    public String sendUrgentRequest(String toPhoneNumber, String donorName, String bloodType, String recipientName, String recipientPhone) {
        String messageBody = "URGENT: " + donorName + ", " + recipientName + " (" + recipientPhone + ") urgently needs " + bloodType + " blood. Please contact them immediately. Your donation can save a life today!";
        return sendSms(toPhoneNumber, messageBody);
    }

    public String sendSms(String toPhoneNumber, String messageBody) {
        if (fromPhoneNumber != null && fromPhoneNumber.startsWith("whatsapp:") && !toPhoneNumber.startsWith("whatsapp:")) {
            toPhoneNumber = "whatsapp:" + toPhoneNumber;
        }

        // If credentials are empty or dummy, mock the SMS
        if (accountSid == null || accountSid.isEmpty() || accountSid.equals("YOUR_TWILIO_SID_HERE")) {
            System.out.println("=========================================");
            System.out.println("MOCK SMS TRIGGERED (No API Key provided)");
            System.out.println("TO: " + toPhoneNumber);
            System.out.println("MESSAGE: " + messageBody);
            System.out.println("=========================================");
            return "SUCCESS";
        }

        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "https://api.twilio.com/2010-04-01/Accounts/" + accountSid + "/Messages.json";

            System.out.println("Twilio Request: URL=" + url);
            System.out.println("Twilio Request: To=" + toPhoneNumber + ", From=" + fromPhoneNumber);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            String auth = accountSid + ":" + authToken;
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
            headers.set("Authorization", "Basic " + encodedAuth);

            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("To", toPhoneNumber);
            map.add("From", fromPhoneNumber);
            map.add("Body", messageBody);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            
            System.out.println("Twilio Response Code: " + response.getStatusCode());
            String body = response.getBody();
            System.out.println("Twilio Response Body: " + body);
            
            return response.getStatusCode().is2xxSuccessful() ? "SUCCESS" : "Twilio Error: " + response.getStatusCode();
        } catch (Exception e) {
            System.err.println("CRITICAL ERROR: Twilio API Call failed!");
            String errorMsg = e.getMessage();
            System.err.println("Message: " + errorMsg);
            if (e instanceof org.springframework.web.client.HttpStatusCodeException) {
                String errorBody = ((org.springframework.web.client.HttpStatusCodeException) e).getResponseBodyAsString();
                System.err.println("Twilio Error Body: " + errorBody);
                if (errorBody.contains("unverified")) {
                    return "Twilio Sandbox Error: The recipient phone number must be verified in your testing account before sending.";
                }
                String extractedMessage = errorBody;
                if (errorBody.contains("\"message\"")) {
                    try {
                        extractedMessage = errorBody.split("\"message\":")[1].split(",")[0].replace("\"", "").trim();
                    } catch(Exception ignored) {}
                }
                return "Twilio API Error: " + extractedMessage;
            }
            return "Connection Error: " + errorMsg;
        }
    }
}
