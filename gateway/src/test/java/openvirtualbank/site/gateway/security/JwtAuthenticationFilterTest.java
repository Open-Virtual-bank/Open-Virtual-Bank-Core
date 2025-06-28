package openvirtualbank.site.gateway.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;

import openvirtualbank.site.gateway.global.security.JwtTokenProvider;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JwtAuthenticationFilterTest {

	@Autowired
	private WebTestClient webTestClient;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@LocalServerPort
	private int port;

	@Test
	@DisplayName("만료된 토큰시 401 에러")
	public void expiredTokenTest() {
		String url = "http://localhost:" + port + "/gateway-test";
		String token = jwtTokenProvider.createJwt("test", "test_role", -3000L);

		webTestClient.get()
			.uri(url)
			.header("Authorization", "Bearer " + token)
			.exchange()
			.expectStatus().isUnauthorized();
	}

	@Test
	@DisplayName("잘못된 형식의 토큰일때 401 에러")
	public void unavailableTokenTest() {
		String url = "http://localhost:" + port + "/gateway-test";
		String token = jwtTokenProvider.createJwt("test", "test_role", -3000L);

		webTestClient.get()
			.uri(url)
			.header("Authorization", "hello " + token) //wrong token header
			.exchange()
			.expectStatus().isUnauthorized();
	}

}
