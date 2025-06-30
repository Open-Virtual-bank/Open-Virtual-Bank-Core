package openvirtualbank.site.domain.global.error;

import com.fasterxml.jackson.databind.ObjectMapper;

import openvirtualbank.site.domain.global.error.dto.RequestDto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GlobalExceptionHandlerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@DisplayName("MethodArgumentNotValidException 처리")
	void handleMethodArgumentNotValid() throws Exception {

		String requestJson = objectMapper.writeValueAsString(new RequestDto());

		mockMvc.perform(post("/test/valid")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.status").value("FAILURE"))
			.andExpect(jsonPath("$.error.code").value("GLB-01"));
	}

	@Test
	@DisplayName("HttpMessageNotReadableException 처리 - RequestBody가 비어있는 경우")
	void handleEmptyRequestBody() throws Exception {

		mockMvc.perform(post("/test/missing-body")
				.contentType(MediaType.APPLICATION_JSON)
				.content(""))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.status").value("FAILURE"))
			.andExpect(jsonPath("$.error.code").value("GLB-02"));
	}

	@Test
	@DisplayName("NoResourceFoundException 처리")
	void handleNoResourceFoundException() throws Exception {

		mockMvc.perform(post("/test/NoUrl"))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.status").value("FAILURE"))
			.andExpect(jsonPath("$.error.code").value("GLB-03"));
	}

	@Test
	@DisplayName("MissingServletRequestParameterException 처리")
	void handleMissingServletRequestParameter() throws Exception {

		mockMvc.perform(get("/test/missing-param"))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.status").value("FAILURE"))
			.andExpect(jsonPath("$.error.code").value("GLB-04"));
	}
}
