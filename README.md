# 🩸 Blood Stream Portal

![Blood Stream Portal](https://socialify.git.ci/hackathon-NareshIT/blood-stream-portal/image?description=1&font=Inter&language=1&name=1&owner=1&pattern=Formal&theme=Light)

## 🚀 Live Demo
**The application is fully deployed and live here:** 
👉 **[Blood Stream Portal - Live on Railway](https://blooddonorapp-production.up.railway.app)**

## 🌟 Overview
Blood Stream Portal is a modern, responsive, and robust **Spring Boot** web application designed to connect blood donors with recipients instantly. By integrating real-time chat widgets and SMS notifications, it tackles urgent public blood crises by facilitating rapid matching.

### 🔑 Key Features
*   **Google OAuth2 Authentication:** Secure, frictionless login using Google single sign-on.
*   **AI Eligibility Assistant:** Powered by **Google Gemini AI**, users can chat with an intelligent assistant to determine their blood donation eligibility before interacting with the live dashboard.
*   **Real-time Alerts & Notifications:** Integrated with the **Twilio API**, allowing recipients to send instantaneous, urgent SMS or WhatsApp alerts directly to registered donors when emergency blood is needed.
*   **Modern Glassmorphism UI:** Built with fully responsive, mobile-first design leveraging robust CSS for a clean, premium user experience.
*   **Dynamic Data Filtering:** Allows rapid searching by Location, Blood Type, and Name.

## 🛠️ Tech Stack
*   **Backend:** Java 17, Spring Boot 3.3.5, Spring Security (OAuth2), Spring Data JPA
*   **Frontend:** HTML5, CSS3, Thymeleaf Templates, HTMX (for dynamic asynchronous loading), Vanilla JavaScript
*   **Database:** MySQL (Hosted on Railway)
*   **Integrations:** Twilio SMS/WhatsApp API, Google Gemini AI API, Google Workspace OAuth2

## 💻 Local Developer Setup 

> [!IMPORTANT]
> Because this application relies heavily on secure API integrations (Google, Twilio, Gemini) and an external database, the easiest way to evaluate the application is via the **Live Demo link above**.
> 
> If you must run this locally, please configure the following Environment Variables in your IDE or system first, as the application requires them to start up successfully.

### Prerequisites
*   Java Development Kit (JDK 17+)
*   Maven 3.8+
*   MySQL 8.0+ (if not using an external cloud database)

### Required Environment Variables
To run the server locally, you must provide valid credentials for the APIs:

```env
# Database Credentials
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/BloodDonor
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=your_password

# Google OAuth2 
GOOGLE_CLIENT_ID=your_google_client_id
GOOGLE_CLIENT_SECRET=your_google_client_secret

# AI Setup
GEMINI_API_KEY=your_google_gemini_api_key

# Twilio Configuration
TWILIO_ACCOUNT_SID=your_twilio_sid
TWILIO_AUTH_TOKEN=your_twilio_auth_token
TWILIO_PHONE_NUMBER=whatsapp:+14155238886
```

### Running the App
1. Clone the repository: `git clone https://github.com/hackathon-NareshIT/blood-stream-portal.git`
2. Open in your favorite IDE (IntelliJ, VS Code, Eclipse) and sync Maven dependencies.
3. Ensure the environment variables above are injected into your Run Configuration.
4. Build and run:
```bash
./mvnw spring-boot:run
```
5. Navigate to `http://localhost:8080/` in your browser.

## 🤝 Contributing
Built during the NareshIT Hackathon. Pull requests are welcome for new features and bug fixes.
