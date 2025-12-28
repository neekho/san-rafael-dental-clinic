package srd.feature.appointment;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;
import com.srd.clinic.dto.AppointmentRequest;
import srd.ClinicApplicationTest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class AppointmentBusinessErrorIT extends ClinicApplicationTest {

    @Test
    void appointment_ShouldReturnError_WhenCaptchaIsInvalid() throws Exception {
        // Use tomorrow's date to avoid past date validation issues
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
            .captchaToken("invalid-captcha-token")
            .build();

        when(captchaService.verify(anyString())).thenReturn(false);

        ResultActions ra = testApiError(request, POST_APPOINTMENT);
        
        ra.andExpect(status().isBadRequest())
          .andExpect(jsonPath("$.code").value("ERROR"))
          .andExpect(jsonPath("$.message").value("Invalid CAPTCHA"));
        
    }
    
}
