package srd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.srd.clinic.service.AppointmentService;
import com.srd.clinic.service.CaptchaService;
import com.srd.clinic.service.EmailService;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = com.srd.clinic.ClinicApplication.class)
@AutoConfigureMockMvc
public abstract class ClinicApplicationTest {

  @Autowired
	protected MockMvc mockMvc;

	@Autowired
	protected ObjectMapper objectMapper;

  @Autowired
  protected AppointmentService appointmentService;

  @MockBean
  protected CaptchaService captchaService;

  @MockBean
  protected EmailService emailService;

  public static final String POST_APPOINTMENT = "/api/v1/appointments";

  protected <T> ResultActions testApiSuccess(T request, String path) throws Exception {
    return mockMvc.perform(post(path)
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(request))
      .servletPath(path))
    .andExpect(status().isOk())
    .andDo(print());
  }

  protected <T> ResultActions testApiError(T request, String path) throws Exception {
return mockMvc.perform(post(path)
  .contentType(MediaType.APPLICATION_JSON)
  .content(objectMapper.writeValueAsString(request))
  .servletPath(path))
 .andExpect(status().isBadRequest())
 .andDo(print());
}

  protected <T> ResultActions testApiValidationError(T request, String path) throws Exception {
return mockMvc.perform(post(path)
  .contentType(MediaType.APPLICATION_JSON)
  .content(objectMapper.writeValueAsString(request))
  .servletPath(path))
 .andExpect(status().isBadRequest())
 .andDo(print());
}

  protected <T> ResultActions testApiConfigurationError(T request, String path) throws Exception {
return mockMvc.perform(post(path)
  .contentType(MediaType.APPLICATION_JSON)
  .content(objectMapper.writeValueAsString(request))
  .servletPath(path))
 .andExpect(status().isInternalServerError())
 .andDo(print());
}
  
}
