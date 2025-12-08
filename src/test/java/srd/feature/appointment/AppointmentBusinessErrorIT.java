package srd.feature.appointment;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;
import com.srd.clinic.dto.AppointmentRequest;
import srd.ClinicApplicationTest;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class AppointmentBusinessErrorIT extends ClinicApplicationTest {

    @Test
    void appointment_ShouldReturnError_WhenCaptchaIsInvalid() throws Exception {
        AppointmentRequest request = new AppointmentRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john.doe@example.com");
        request.setMobile("09123456789");
        request.setService("General Consultation");
        request.setPreferredDate("2024-12-10");
        request.setPreferredTime("10:00 AM");
        request.setNotes("Regular checkup");
        request.setCaptchaToken("invalid-captcha-token");

        when(captchaService.verify(anyString())).thenReturn(false);

        ResultActions ra = testApiError(request, POST_APPOINTMENT);
        
        ra.andExpect(status().isBadRequest())
          .andExpect(jsonPath("$.code").value("ERROR"))
          .andExpect(jsonPath("$.message").value("Invalid CAPTCHA"));
        
    }
    
}