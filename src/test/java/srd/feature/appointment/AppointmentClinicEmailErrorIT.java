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
        AppointmentRequest request = new AppointmentRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john.doe@example.com");
        request.setMobile("09123456789");
        request.setService("General Consultation");
        request.setPreferredDate("2024-12-10");
        request.setPreferredTime("10:00 AM");
        request.setNotes("Regular checkup");
        request.setCaptchaToken("valid-captcha-token");

        when(captchaService.verify(anyString())).thenReturn(true);

        ResultActions ra = testApiConfigurationError(request, POST_APPOINTMENT);
        
        ra.andExpect(status().isInternalServerError())
          .andExpect(jsonPath("$.code").value("CONFIGURATION_ERROR"))
          .andExpect(jsonPath("$.message").value("Clinic email is not configured (clinic.notification.email)"));
    }

}