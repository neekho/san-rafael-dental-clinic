# San Rafael Dental Clinic Appointment System

A comprehensive appointment booking system for San Rafael Dental Clinic, featuring a modern Next.js frontend and a robust Spring Boot backend with email/SMS notifications and Google reCAPTCHA integration.

## 🎯 What is this application?

This is a full-stack dental clinic appointment booking system that allows patients to:
- Book dental appointments online through a modern web interface
- Select from various dental services (Cleaning, Tooth Extraction, Root Canal Treatment, etc.)
- Receive email and SMS confirmation notifications
- Experience secure form submission with Google reCAPTCHA v3 protection

The system automatically notifies clinic staff via email and SMS when new appointments are submitted.

## 🛠️ Tech Stack

### Backend (Spring Boot)
- **Java 21** with **Spring Boot 3.5.8**
- **Maven** for dependency management
- **Spring Web** - REST API endpoints
- **Spring Mail** - Email notifications
- **Spring Validation** - Request validation
- **Lombok** - Code generation
- **Springdoc OpenAPI** - API documentation (Swagger UI)
- **Resilience4j** - Rate limiting
- **Spring Boot Actuator** - Application monitoring

### Frontend (Next.js)
- **Next.js 16** with **React 19**
- **TypeScript** for type safety
- **Tailwind CSS** for styling
- **Radix UI** components for accessible UI
- **React Hook Form** with **Zod** validation
- **Sonner** for toast notifications
- **Lucide React** for icons

### External Services
- **Google reCAPTCHA v3** - Bot protection and security
- **Gmail SMTP** - Email delivery service
- **Semaphore SMS API** - SMS notification service (Philippines-based)

## 📋 API Endpoints

### POST `/api/v1/appointments`
Creates a new appointment booking.

**Rate Limit**: 5 requests per second

#### Sample Request:
```bash
curl -X POST http://localhost:8080/api/v1/appointments \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Juan",
    "lastName": "Dela Cruz",
    "email": "juan.delacruz@example.com",
    "mobile": "09171234567",
    "service": "Cleaning",
    "preferredDate": "2025-01-15",
    "preferredTime": "10:00",
    "notes": "First visit to the clinic",
    "captchaToken": "03AGdBq26BxmQ..."
  }'
```

#### Sample Response (Success):
```json
{
  "status": "success",
  "message": "Appointment submitted successfully"
}
```

#### Sample Response (Validation Error):
```json
{
  "status": "error",
  "message": "Validation failed",
  "errors": [
    {
      "field": "email",
      "message": "Please provide a valid email address"
    }
  ]
}
```

#### Sample Response (Rate Limit Exceeded):
```json
{
  "status": "error",
  "message": "Rate limit exceeded. Please try again later."
}
```

### Available Services:
- Cleaning
- Tooth Extraction
- Root Canal Treatment
- Dental Filling
- Teeth Whitening
- Braces Consultation
- Oral Surgery
- Dental Implants
- Dentures
- Orthodontic Treatment

## 🚀 Getting Started

### Prerequisites
- **Java 21** or higher
- **Node.js 18** or higher
- **Maven 3.6+**
- **Gmail account** (for email notifications)
- **Semaphore account** (for SMS notifications - optional)
- **Google reCAPTCHA keys** (v3)

### Backend Setup

1. **Clone and navigate to backend directory:**
   ```bash
   cd backend
   ```

2. **Configure environment variables:**
   Create a `.env` file in the backend directory or set environment variables:
   ```bash
   # Required
   CAPTCHA_SECRET=your_recaptcha_secret_key
   NOTIFICATION_EMAIL=clinic@example.com,admin@example.com
   SPRING_MAIL_USERNAME=your_gmail_address@gmail.com
   SPRING_MAIL_PASSWORD=your_gmail_app_password
   
   # Optional (for SMS notifications)
   NOTIFICATION_SMS_NUMBER=+639171234567
   SMS_PROVIDER_API_KEY=your_semaphore_api_key
   ```

3. **Build and run the backend:**
   ```bash
   # Build the application
   mvn clean package
   
   # Run the application
   java -jar target/san-rafael-dental-clinic-1.0.0.jar
   
   # OR run directly with Maven
   mvn spring-boot:run
   ```

4. **Verify backend is running:**
   - API: http://localhost:8080
   - Swagger UI: http://localhost:8080/swagger-ui/index.html
   - Health Check: http://localhost:8080/actuator/health

### Frontend Setup

1. **Navigate to frontend directory:**
   ```bash
   cd frontend
   ```

2. **Install dependencies:**
   ```bash
   npm install
   # or
   pnpm install
   ```

3. **Configure Google reCAPTCHA:**
   Add your reCAPTCHA site key to your frontend environment configuration.

4. **Start the development server:**
   ```bash
   npm run dev
   # or
   pnpm dev
   ```

5. **Verify frontend is running:**
   - Development: http://localhost:3000

### Production Build

**Backend:**
```bash
cd backend
mvn clean package
java -jar target/san-rafael-dental-clinic-1.0.0.jar
```

**Frontend:**
```bash
cd frontend
npm run build
npm start
```

## 🔧 Testing

### Backend Tests
```bash
cd backend

# Run unit tests
mvn test

# Run integration tests
mvn verify

# Run all tests
mvn clean verify
```

### Frontend Testing
```bash
cd frontend
npm run lint
```

## 📚 API Documentation

After starting the backend server, visit:
- **Swagger UI**: http://localhost:8080/swagger-ui/index.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

## 🔐 Security Features

- **Rate Limiting**: 5 requests per second per endpoint
- **Google reCAPTCHA v3**: Bot protection and spam prevention
- **Input Validation**: Comprehensive server-side validation
- **CORS Configuration**: Secure cross-origin resource sharing
- **Environment Variables**: Secure configuration management

## 🏥 Clinic Integration

The system integrates with clinic workflows by:
- Sending appointment notifications to multiple clinic email addresses
- Providing SMS alerts to clinic staff mobile numbers
- Including all appointment details in notifications
- Supporting various dental services and appointment times

## 🌐 External Service Configuration

### Gmail SMTP Setup:
1. Enable 2-factor authentication on your Gmail account
2. Generate an App Password for the application
3. Use the App Password in `SPRING_MAIL_PASSWORD`

### Semaphore SMS Setup (Philippines):
1. Create account at https://semaphore.co/
2. Get API key from dashboard
3. Add API key to `SMS_PROVIDER_API_KEY`

### Google reCAPTCHA Setup:
1. Visit https://www.google.com/recaptcha/admin
2. Create a new site (v3)
3. Add your domain/localhost for testing
4. Use site key in frontend, secret key in backend

## 📞 Support

For technical support or questions about this dental clinic appointment system, please refer to the API documentation or contact the development team.
