package srd.feature.appointment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.test.web.servlet.ResultActions;
import com.srd.clinic.dto.AppointmentRequest;
import srd.ClinicApplicationTest;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AppointmentValidationIT extends ClinicApplicationTest {

  @ParameterizedTest
  @CsvSource({
      "'', First name is required",
      "John123, 'First name can only contain letters, spaces, hyphens, and apostrophes'",
      "John@#$, 'First name can only contain letters, spaces, hyphens, and apostrophes'"
  })
  void appointment_ShouldReturnValidationError_WhenFirstNameIsInvalid(String firstName, String expectedMessage) throws Exception {
    AppointmentRequest request = createValidRequest().toBuilder()
        .firstName(firstName)
        .build();

    ResultActions ra = testApiValidationError(request, POST_APPOINTMENT);

    ra.andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value(expectedMessage));
  }

  @ParameterizedTest
  @CsvSource({
      "'', Last name is required",
      "Doe123, 'Last name can only contain letters, spaces, hyphens, and apostrophes'",
      "Doe@#$, 'Last name can only contain letters, spaces, hyphens, and apostrophes'"
  })
  void appointment_ShouldReturnValidationError_WhenLastNameIsInvalid(String lastName, String expectedMessage) throws Exception {
    AppointmentRequest request = createValidRequest().toBuilder()
        .lastName(lastName)
        .build();

    ResultActions ra = testApiValidationError(request, POST_APPOINTMENT);

    ra.andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value(expectedMessage));
  }

  @Test
  void appointment_ShouldReturnValidationError_WhenEmailIsInvalid() throws Exception {
    AppointmentRequest request = createValidRequest().toBuilder()
        .email("invalid-email")
        .build();

    ResultActions ra = testApiValidationError(request, POST_APPOINTMENT);

    ra.andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("Invalid email"));
  }

  @ParameterizedTest
  @CsvSource({
      "'', Mobile number is required",
      "' ', Mobile number is required"
  })
  void appointment_ShouldReturnValidationError_WhenMobileIsBlank(String mobile, String expectedMessage) throws Exception {
    AppointmentRequest request = createValidRequest().toBuilder()
        .mobile(mobile)
        .build();

    ResultActions ra = testApiValidationError(request, POST_APPOINTMENT);

    ra.andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value(expectedMessage));
  }

  @ParameterizedTest
  @CsvSource({
      "1234567890, 'Invalid mobile number. Use format: 09XXXXXXXXX'",
      "08123456789, 'Invalid mobile number. Use format: 09XXXXXXXXX'",
      "9123456789, 'Invalid mobile number. Use format: 09XXXXXXXXX'",
      "091234567890, 'Invalid mobile number. Use format: 09XXXXXXXXX'",
      "09123456, 'Invalid mobile number. Use format: 09XXXXXXXXX'"
  })
  void appointment_ShouldReturnValidationError_WhenMobilePatternIsInvalid(String mobile, String expectedMessage) throws Exception {
    AppointmentRequest request = createValidRequest().toBuilder()
        .mobile(mobile)
        .build();

    ResultActions ra = testApiValidationError(request, POST_APPOINTMENT);

    ra.andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value(expectedMessage));
  }

  @ParameterizedTest
  @CsvSource({
      "'', Service is required",
      "' ', Service is required",
      "Invalid Service, 'Invalid service selected. Please choose from the available options'",
      "INVALID_SERVICE, 'Invalid service selected. Please choose from the available options'",
      "consultation_invalid, 'Invalid service selected. Please choose from the available options'",
      "checkup_wrong, 'Invalid service selected. Please choose from the available options'"
  })
  void appointment_ShouldReturnValidationError_WhenServiceIsInvalid(String service, String expectedMessage) throws Exception {
    AppointmentRequest request = createValidRequest().toBuilder()
        .service(service)
        .build();

    ResultActions ra = testApiValidationError(request, POST_APPOINTMENT);

    ra.andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value(expectedMessage));
  }

  @Test
  void appointment_ShouldReturnValidationError_WhenPreferredDateIsBlank() throws Exception {
    AppointmentRequest request = createValidRequest().toBuilder()
        .preferredDate("")
        .build();

    ResultActions ra = testApiValidationError(request, POST_APPOINTMENT);

    ra.andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("Preferred date is required"));
  }

  @Test
  void appointment_ShouldReturnValidationError_WhenPreferredTimeIsBlank() throws Exception {
    AppointmentRequest request = createValidRequest().toBuilder()
        .preferredTime("")
        .build();

    ResultActions ra = testApiValidationError(request, POST_APPOINTMENT);

    ra.andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("Preferred time is required"));
  }

  @Test
  void appointment_ShouldReturnValidationError_WhenNotesExceedMaxLength() throws Exception {
    AppointmentRequest request = createValidRequest().toBuilder()
        .notes("This is a very long note that exceeds the maximum allowed length of 150 characters. " +
            "It should trigger a validation error because it's way too long for the system to handle properly.")
        .build();

    ResultActions ra = testApiValidationError(request, POST_APPOINTMENT);

    ra.andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("Notes cannot exceed 150 characters"));
  }

  @Test
  void appointment_ShouldReturnValidationError_WhenCaptchaTokenIsBlank() throws Exception {
    AppointmentRequest request = createValidRequest().toBuilder()
        .captchaToken("")
        .build();

    ResultActions ra = testApiValidationError(request, POST_APPOINTMENT);

    ra.andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("Captcha token is required"));
  }

  @Test
  void appointment_ShouldReturnValidationError_WhenDateIsInPast() throws Exception {
    AppointmentRequest request = createValidRequest().toBuilder()
        .preferredDate("2023-01-01")
        .build();

    ResultActions ra = testApiValidationError(request, POST_APPOINTMENT);

    ra.andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("Cannot book appointments for past dates"));
  }

  @Test
  void appointment_ShouldReturnValidationError_WhenDateIsNextYear() throws Exception {
    // Mock captcha service to return true so we can test date validation
    when(captchaService.verify(anyString())).thenReturn(true);

    AppointmentRequest request = createValidRequest().toBuilder()
        .preferredDate("2026-02-01") // Use February to avoid January special case
        .build();

    ResultActions ra = testApiValidationError(request, POST_APPOINTMENT);

    ra.andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("Appointments can only be booked within 2025"));
  }

  @Test
  void appointment_ShouldReturnValidationError_WhenDateFormatIsInvalid() throws Exception {
    AppointmentRequest request = createValidRequest().toBuilder()
        .preferredDate("invalid-date")
        .build();

    ResultActions ra = testApiValidationError(request, POST_APPOINTMENT);

    ra.andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("Invalid date format. Use YYYY-MM-DD"));
  }

  @Test
  void appointment_ShouldReturnValidationError_WhenTimeFormatIsInvalid() throws Exception {
    AppointmentRequest request = createValidRequest().toBuilder()
        .preferredTime("25:00")
        .build();

    ResultActions ra = testApiValidationError(request, POST_APPOINTMENT);

    ra.andExpect(status().isBadRequest())
        .andExpect(
            jsonPath("$.message").value("Invalid time format. Use 24-hour format HH:MM (e.g., 14:30, 09:00, 23:59)"));
  }

  @Test
  void appointment_ShouldReturnValidationError_WhenTimeFormatIs12Hour() throws Exception {
    AppointmentRequest request = createValidRequest().toBuilder()
        .preferredTime("10:00 AM")
        .build();

    ResultActions ra = testApiValidationError(request, POST_APPOINTMENT);

    ra.andExpect(status().isBadRequest())
        .andExpect(
            jsonPath("$.message").value("Invalid time format. Use 24-hour format HH:MM (e.g., 14:30, 09:00, 23:59)"));
  }

  private AppointmentRequest createValidRequest() {
    return AppointmentRequest.builder()
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
  }

}
