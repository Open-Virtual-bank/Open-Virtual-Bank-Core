package openvirtualbank.site.member.join.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.fasterxml.jackson.databind.JsonNode;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmailVertificationTest {

	@LocalServerPort
	private int port;

	private WebTestClient webTestClient;

	@BeforeEach
	void setup() {
		this.webTestClient = WebTestClient.bindToServer()
			.baseUrl("http://localhost:" + port)
			.build();
	}

	@Test
	void EmailVertificationTest() {
		String url = "http://localhost:" + port + "/" + "auth/email/send-code";
		String email = "test@example.com";

		JsonNode sendCodeResponse = webTestClient.post()
			.uri("/auth/email/send-code")
			.contentType(MediaType.APPLICATION_JSON)
			.bodyValue(Map.of("email", email))
			.exchange()
			.expectStatus().isOk() // 상태 코드 검증 (선택)
			.returnResult(JsonNode.class)
			.getResponseBody()
			.blockFirst();// Flux<JsonNode> → 첫 번째 JsonNode

		assertEquals("SUCCESS", sendCodeResponse.get("status").asText());
		int authNumber = sendCodeResponse.get("data").get("authNumber").asInt();
		String uuid = sendCodeResponse.get("data").get("key").asText();

		JsonNode verifyCodeResponse = webTestClient.post()
			.uri("/auth/email/verify-code")
			.contentType(MediaType.APPLICATION_JSON)
			.bodyValue(Map.of(
				"uuid", uuid,
				"AuthNumber", authNumber
			))
			.exchange()
			.expectStatus().isOk()
			.returnResult(JsonNode.class)
			.getResponseBody()
			.blockFirst();

		assertTrue(verifyCodeResponse.get("data").get("status").asBoolean());

	}

}
