package srd.feature.appointment;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;
import com.srd.clinic.dto.AppointmentRequest;
import srd.ClinicApplicationTest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AppointmentValidationIT extends ClinicApplicationTest {

    @Test
    void appointment_ShouldReturnValidationError_WhenFirstNameIsBlank() throws Exception {
        AppointmentRequest request = createValidRequest();
        request.setFirstName("");

        ResultActions ra = testApiValidationError(request, POST_APPOINTMENT);
        
        ra.andExpect(status().isBadRequest())
          .andExpect(jsonPath("$.message").value("First name is required"));
    }

    @Test
    void appointment_ShouldReturnValidationError_WhenLastNameIsBlank() throws Exception {
        AppointmentRequest request = createValidRequest();
        request.setLastName("");

        ResultActions ra = testApiValidationError(request, POST_APPOINTMENT);
        
        ra.andExpect(status().isBadRequest())
          .andExpect(jsonPath("$.message").value("Last name is required"));
    }

    @Test
    void appointment_ShouldReturnValidationError_WhenEmailIsInvalid() throws Exception {
        AppointmentRequest request = createValidRequest();
        request.setEmail("invalid-email");

        ResultActions ra = testApiValidationError(request, POST_APPOINTMENT);
        
        ra.andExpect(status().isBadRequest())
          .andExpect(jsonPath("$.message").value("Invalid email"));
    }

    @Test
    void appointment_ShouldReturnValidationError_WhenMobileIsBlank() throws Exception {
        AppointmentRequest request = createValidRequest();
        request.setMobile("");

        ResultActions ra = testApiValidationError(request, POST_APPOINTMENT);
        
        ra.andExpect(status().isBadRequest())
          .andExpect(jsonPath("$.message").value("Mobile number is required required"));
    }

    @Test
    void appointment_ShouldReturnValidationError_WhenMobilePatternIsInvalid() throws Exception {
        AppointmentRequest request = createValidRequest();
        request.setMobile("1234567890");

        ResultActions ra = testApiValidationError(request, POST_APPOINTMENT);
        
        ra.andExpect(status().isBadRequest())
          .andExpect(jsonPath("$.message").value("Invalid mobile number"));
    }

    @Test
    void appointment_ShouldReturnValidationError_WhenServiceIsBlank() throws Exception {
        AppointmentRequest request = createValidRequest();
        request.setService("");

        ResultActions ra = testApiValidationError(request, POST_APPOINTMENT);
        
        ra.andExpect(status().isBadRequest())
          .andExpect(jsonPath("$.message").value("Service is required"));
    }

    @Test
    void appointment_ShouldReturnValidationError_WhenPreferredDateIsBlank() throws Exception {
        AppointmentRequest request = createValidRequest();
        request.setPreferredDate("");

        ResultActions ra = testApiValidationError(request, POST_APPOINTMENT);
        
        ra.andExpect(status().isBadRequest())
          .andExpect(jsonPath("$.message").value("Preferred date is required"));
    }

    @Test
    void appointment_ShouldReturnValidationError_WhenPreferredTimeIsBlank() throws Exception {
        AppointmentRequest request = createValidRequest();
        request.setPreferredTime("");

        ResultActions ra = testApiValidationError(request, POST_APPOINTMENT);
        
        ra.andExpect(status().isBadRequest())
          .andExpect(jsonPath("$.message").value("Preferred time is required"));
    }

    @Test
    void appointment_ShouldReturnValidationError_WhenNotesExceedMaxLength() throws Exception {
        AppointmentRequest request = createValidRequest();
        request.setNotes("This is a very long note that exceeds the maximum allowed length of 150 characters. " +
                        "It should trigger a validation error because it's way too long for the system to handle properly.");

        ResultActions ra = testApiValidationError(request, POST_APPOINTMENT);
        
        ra.andExpect(status().isBadRequest())
          .andExpect(jsonPath("$.message").value("Notes cannot exceed 150 characters"));
    }

    @Test
    void appointment_ShouldReturnValidationError_WhenCaptchaTokenIsBlank() throws Exception {
        AppointmentRequest request = createValidRequest();
        request.setCaptchaToken("");

        ResultActions ra = testApiValidationError(request, POST_APPOINTMENT);
        
        ra.andExpect(status().isBadRequest())
          .andExpect(jsonPath("$.message").value("Captcha token is required"));
    }

    private AppointmentRequest createValidRequest() {
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
        return request;
    }
    
}