# Dental Clinic Backend (Spring Boot) - Example Project

This is a minimal, **runnable** Spring Boot backend for a dental clinic appointment form.
It includes:
- Appointment endpoint `POST /api/v1/appointments`
- Google reCAPTCHA v3 server-side verification
- Email notifications (Spring Mail)
- SMS notifications (Semaphore integration example)
- Validation and global exception handling
- OpenAPI (Swagger UI) via springdoc

## How to use

1. **Update configuration**
   Edit `src/main/resources/application.properties`:
   - `captcha.secret` → your Google reCAPTCHA secret key
   - `clinic.notification.email` → clinic email recipient
   - Mail settings (`spring.mail.*`) → configure SMTP
   - `semaphore.apiKey` → API key for Semaphore (optional)
   - `clinic.notification.smsNumber` → phone number to receive SMS notifications

2. **Build & Run**
   ```bash
   mvn clean package
   java -jar target/dental-clinic-1.0.0.jar
   ```
   or run from your IDE.

3. **Swagger & API docs**
   After running, visit:
   `http://localhost:8080/swagger-ui/index.html` to view API docs.

4. **Frontend integration**
   - On the frontend, integrate Google reCAPTCHA v3 and send the token in the request body as `captchaToken`.
   - Example request body:
   ```json
   {
     "fullName": "Juan Dela Cruz",
     "email": "juan@example.com",
     "mobile": "09171234567",
     "service": "Cleaning",
     "preferredDate": "2025-11-30",
     "preferredTime": "10:00",
     "notes": "First visit",
     "captchaToken": "03AGdB..."
   }
   ```

## Notes & Next steps
- This project uses a simple text email (SimpleMailMessage). For nicer emails use Thymeleaf templates or external transactional email services.
- If you want a database to persist appointments, add `spring-boot-starter-data-jpa` and an Appointment entity + repository.
- For production, secure secrets using environment variables or a secrets store.

