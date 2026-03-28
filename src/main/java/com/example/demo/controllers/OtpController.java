package com.example.demo.controllers;

import com.example.demo.services.SmsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/api/otp")
public class OtpController {

    @Autowired
    private SmsService smsService;

    @PostMapping("/send")
    public String sendOtp(@RequestParam String contactInfo, HttpSession session) {
        // Basic phone cleanup (match backend registration logic)
        if (contactInfo != null && !contactInfo.startsWith("+")) {
            contactInfo = "+91" + contactInfo.replaceAll("[^0-9]", "");
        }

        String otp = String.format("%06d", new Random().nextInt(999999));
        session.setAttribute("OTP_" + contactInfo, otp);
        
        String message = "Your Blood Donor App verification code is: " + otp + ". Valid for 5 minutes.";
        String result = smsService.sendSms(contactInfo, message);

        if ("SUCCESS".equals(result)) {
            return "<div id='otp-status' class='fade-in' style='margin-top: 10px;'>" +
                   "  <label style='color: var(--primary-dark);'>Enter 6-digit OTP sent to " + contactInfo + "</label>" +
                   "  <div class='phone-input-group'>" +
                   "    <input type='text' name='otp_input' id='otp_input' placeholder='000000' required style='text-align: center; letter-spacing: 5px; font-weight: bold;'>" +
                   "    <button type='button' class='button' style='height: auto; padding: 10px; font-size: 0.9rem; margin-top:0;' " +
                   "            hx-post='/api/otp/verify' hx-include='#contactInfo' hx-target='#otp-status'>Verify</button>" +
                   "  </div>" +
                   "</div>";
        } else {
            return "<p style='color: red; font-size: 0.8rem;'>" + result + "</p>";
        }
    }

    @PostMapping("/verify")
    public String verifyOtp(@RequestParam String contactInfo, @RequestParam String otp_input, HttpSession session) {
        // Clean phone same as send
        if (contactInfo != null && !contactInfo.startsWith("+")) {
            contactInfo = "+91" + contactInfo.replaceAll("[^0-9]", "");
        }

        String actualOtp = (String) session.getAttribute("OTP_" + contactInfo);
        
        if (actualOtp != null && actualOtp.equals(otp_input)) {
            session.setAttribute("VERIFIED_" + contactInfo, true);
            return "<p style='color: green; font-weight: bold; margin-top:10px;'>✅ Phone Verified Successfully!</p>" +
                   "<script>document.getElementById('submit-btn').disabled = false;</script>";
        } else {
            return "<p style='color: red; font-weight: bold; margin-top:10px;'>❌ Invalid OTP. Try again.</p>" +
                   "<button type='button' hx-post='/api/otp/send' hx-include='#contactInfo' hx-target='#otp-area' class='button-outline' style='font-size: 0.8rem; padding: 5px 10px;'>Resend OTP</button>";
        }
    }
}
