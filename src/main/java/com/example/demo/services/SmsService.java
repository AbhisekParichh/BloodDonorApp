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

    public boolean sendUrgentRequest(String toPhoneNumber, String donorName, String bloodType) {
        String messageBody = "URGENT: " + donorName + ", a hospital urgently needs " + bloodType + " blood. Please log into the Blood Donor App or contact your local hospital immediately. Your donation can save a life today!";
        return sendSms(toPhoneNumber, messageBody);
    }

    public boolean sendSms(String toPhoneNumber, String messageBody) {
        if (fromPhoneNumber != null && fromPhoneNumber.startsWith("whatsapp:") && !toPhoneNumber.startsWith("whatsapp:")) {
            toPhoneNumber = "whatsapp:" + toPhoneNumber;
        }

        // If credentials are empty or dummy, mock the SMS
        if (accountSid == null || accountSid.isEmpty() || accountSid.equals("YOUR_TWILIO_SID")) {
            System.out.println("=========================================");
            System.out.println("MOCK SMS TRIGGERED (No API Key provided)");
            System.out.println("TO: " + toPhoneNumber);
            System.out.println("MESSAGE: " + messageBody);
            System.out.println("=========================================");
            return true;
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
            
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            System.err.println("CRITICAL ERROR: Twilio API Call failed!");
            String errorMsg = e.getMessage();
            System.err.println("Message: " + errorMsg);
            if (e instanceof org.springframework.web.client.HttpStatusCodeException) {
                String errorBody = ((org.springframework.web.client.HttpStatusCodeException) e).getResponseBodyAsString();
                System.err.println("Twilio Error Body: " + errorBody);
            }
            return false;
        }
    }
}
