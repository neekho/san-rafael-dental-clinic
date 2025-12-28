package srd.feature.appointment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.test.web.servlet.ResultActions;
import com.srd.clinic.dto.AppointmentRequest;
import com.srd.clinic.error.ErrorCode;
import srd.ClinicApplicationTest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AppointmentValidationIT extends ClinicApplicationTest {

  @ParameterizedTest
  @CsvSource({
      "''",
      "John123",
      "John@#$"
  })
  void appointment_ShouldReturnValidationError_WhenFirstNameIsInvalid(String firstName) throws Exception {
    AppointmentRequest request = createValidRequest().toBuilder()
        .firstName(firstName)
        .build();

    ResultActions ra = testApiValidationError(request, POST_APPOINTMENT);

    String expectedMessage = firstName.isEmpty() ? ErrorCode.FIRST_NAME_REQUIRED : ErrorCode.FIRST_NAME_INVALID_CHARACTERS;
    ra.andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value(expectedMessage));
  }

  @ParameterizedTest
  @CsvSource({
      "''",
      "Doe123",
      "Doe@#$"
  })
  void appointment_ShouldReturnValidationError_WhenLastNameIsInvalid(String lastName) throws Exception {
    AppointmentRequest request = createValidRequest().toBuilder()
        .lastName(lastName)
        .build();

    ResultActions ra = testApiValidationError(request, POST_APPOINTMENT);

    String expectedMessage = lastName.isEmpty() ? ErrorCode.LAST_NAME_REQUIRED : ErrorCode.LAST_NAME_INVALID_CHARACTERS;
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
        .andExpect(jsonPath("$.message").value(ErrorCode.EMAIL_INVALID));
  }

  @ParameterizedTest
  @CsvSource({
      "''",
      "' '"
  })
  void appointment_ShouldReturnValidationError_WhenMobileIsBlank(String mobile) throws Exception {
    AppointmentRequest request = createValidRequest().toBuilder()
        .mobile(mobile)
        .build();

    ResultActions ra = testApiValidationError(request, POST_APPOINTMENT);

    ra.andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value(ErrorCode.MOBILE_REQUIRED));
  }

  @ParameterizedTest
  @CsvSource({
      "''",
      "' '",
      "Invalid Service",
      "INVALID_SERVICE",
      "consultation_invalid",
      "checkup_wrong"
  })
  void appointment_ShouldReturnValidationError_WhenServiceIsInvalid(String service) throws Exception {
    AppointmentRequest request = createValidRequest().toBuilder()
        .service(service)
        .build();

    ResultActions ra = testApiValidationError(request, POST_APPOINTMENT);

    String expectedMessage = (service.isEmpty() || service.trim().isEmpty()) ? 
        ErrorCode.SERVICE_REQUIRED : ErrorCode.SERVICE_INVALID_SELECTION;
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
        .andExpect(jsonPath("$.message").value(ErrorCode.DATE_REQUIRED));
  }

  @ParameterizedTest
  @CsvSource({
      "''",
      "' '"
  })
  void appointment_ShouldReturnValidationError_WhenPreferredTimeIsBlank(String preferredTime) throws Exception {
    AppointmentRequest request = createValidRequest().toBuilder()
        .preferredTime(preferredTime)
        .build();

    ResultActions ra = testApiValidationError(request, POST_APPOINTMENT);

    ra.andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value(ErrorCode.TIME_REQUIRED));
  }

  @Test
  void appointment_ShouldReturnValidationError_WhenNotesExceedMaxLength() throws Exception {
    AppointmentRequest request = createValidRequest().toBuilder()
        .notes("This is a very long note that exceeds the maximum allowed length of 150 characters. " +
            "It should trigger a validation error because it's way too long for the system to handle properly.")
        .build();

    ResultActions ra = testApiValidationError(request, POST_APPOINTMENT);

    ra.andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value(ErrorCode.NOTES_MAX_LENGTH_EXCEEDED));
  }

  @Test
  void appointment_ShouldReturnValidationError_WhenCaptchaTokenIsBlank() throws Exception {
    AppointmentRequest request = createValidRequest().toBuilder()
        .captchaToken("")
        .build();

    ResultActions ra = testApiValidationError(request, POST_APPOINTMENT);

    ra.andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value(ErrorCode.CAPTCHA_TOKEN_REQUIRED));
  }

  @Test
  void appointment_ShouldReturnValidationError_WhenDateIsInPast() throws Exception {
    AppointmentRequest request = createValidRequest().toBuilder()
        .preferredDate("2023-01-01")
        .build();

    ResultActions ra = testApiValidationError(request, POST_APPOINTMENT);

    ra.andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value(ErrorCode.DATE_PAST_NOT_ALLOWED));
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
        .andExpect(jsonPath("$.message").value(ErrorCode.DATE_YEAR_LIMIT_EXCEEDED));
  }

  @Test
  void appointment_ShouldReturnValidationError_WhenDateFormatIsInvalid() throws Exception {
    AppointmentRequest request = createValidRequest().toBuilder()
        .preferredDate("invalid-date")
        .build();

    ResultActions ra = testApiValidationError(request, POST_APPOINTMENT);

    ra.andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value(ErrorCode.DATE_INVALID_FORMAT));
  }

  @ParameterizedTest
  @CsvSource({
      "25:00",
      "10:00 AM",
      "10:00 PM",
      "24:30",
      "12:60",
      "invalid-time",
      "1:30",
      "13:5"
  })
  void appointment_ShouldReturnValidationError_WhenTimeFormatIsInvalid(String preferredTime) throws Exception {
    AppointmentRequest request = createValidRequest().toBuilder()
        .preferredTime(preferredTime)
        .build();

    ResultActions ra = testApiValidationError(request, POST_APPOINTMENT);

    ra.andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value(ErrorCode.TIME_INVALID_FORMAT));
  }

  private AppointmentRequest createValidRequest() {
    String tomorrowDate = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    
    return AppointmentRequest.builder()
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
  }

}
