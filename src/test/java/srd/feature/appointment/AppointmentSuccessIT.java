package srd.feature.appointment;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import com.srd.clinic.dto.AppointmentRequest;

import srd.ClinicApplicationTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class AppointmentSuccessIT extends ClinicApplicationTest {

    @Test
    void appointment_ShouldReturnSuccess() throws Exception {
        // Given - Create a valid appointment request
        AppointmentRequest request = AppointmentRequest.builder()
            .firstName("John")
            .lastName("Doe")
            .email("john.doe@example.com")
            .mobile("09123456789")
            .service("Dental Consultation")
            .preferredDate("2025-12-25")
            .preferredTime("10:00")
            .notes("Regular checkup")
            .captchaToken("valid-captcha-token")
            .build();

        // Mock the captcha service to return true (valid captcha)
        when(captchaService.verify(anyString())).thenReturn(true);
        
        // Mock the email service to do nothing (successful email send)
        doNothing().when(emailService).sendAppointmentEmailHtml(any(AppointmentRequest.class), any(String[].class));

        // When - Make the appointment request
        ResultActions ra = testApiSuccess(request, POST_APPOINTMENT);
        
        // Then - Expect success response
        ra.andExpect(jsonPath("$.status").value("success"))
          .andExpect(jsonPath("$.message").value("Appointment submitted successfully"));
    }
    
}
