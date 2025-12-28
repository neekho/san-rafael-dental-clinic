package srd.feature.appointment;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.ResultActions;

import com.srd.clinic.dto.AppointmentRequest;

import srd.ClinicApplicationTest;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource(properties = {
        "clinic.notification.email="
})
class AppointmentClinicEmailErrorIT extends ClinicApplicationTest {

    @Test
    void appointment_ShouldReturnError_WhenClinicEmailIsNotConfigured() throws Exception {
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

        when(captchaService.verify(anyString())).thenReturn(true);

        ResultActions ra = testApiConfigurationError(request, POST_APPOINTMENT);
        
        ra.andExpect(status().isInternalServerError())
          .andExpect(jsonPath("$.code").value("CONFIGURATION_ERROR"))
          .andExpect(jsonPath("$.message").value("Clinic email is not configured (clinic.notification.email)"));
    }

}