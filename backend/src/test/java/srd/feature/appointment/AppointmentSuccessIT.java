package srd.feature.appointment;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import com.srd.clinic.dto.AppointmentRequest;

import srd.ClinicApplicationTest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class AppointmentSuccessIT extends ClinicApplicationTest {

    @Test
    void appointment_ShouldReturnSuccess() throws Exception {
        // Given - Create a valid appointment request with tomorrow's date
        String tomorrowDate = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        
        AppointmentRequest request = AppointmentRequest.builder()
            .firstName("John")
            .lastName("Doe")
            .email("john.doe@example.com")
            .mobile("09123456789")
            .service("Dental Consultation")
            .preferredDate(tomorrowDate)
            .preferredTime("10:00")
            .notes("Regular checkup")
            .captchaToken("valid-captcha-token")
            .build();

        // Mock the captcha service to return true (valid captcha)
        when(captchaService.verify(anyString())).thenReturn(true);
        
        // Mock the email service to return a completed future (successful async email send)
        when(emailService.sendAppointmentEmailHtml(any(AppointmentRequest.class), any(String[].class)))
            .thenReturn(CompletableFuture.completedFuture(null));

        // When - Make the appointment request
        ResultActions ra = testApiSuccess(request, POST_APPOINTMENT);
        
        // Then - Expect success response
        ra.andExpect(jsonPath("$.status").value("success"))
          .andExpect(jsonPath("$.message").value("Appointment submitted successfully"));
    }
    
}
