package openvirtualbank.site.gateway.global.security.jwt;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {
	private final String TOKEN_HEAD = "Bearer ";
	private final JwtTokenProvider jwtTokenProvider;

	public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
		super(Config.class);
		this.jwtTokenProvider = jwtTokenProvider;
	}

	/**
	 * JWT 토큰 검증 및 reactive context에 담기
	 * @param config
	 * @return
	 */
	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			String token = extractToken(exchange.getRequest());

			log.info("token={}", jwtTokenProvider.createJwt("test", "test_role", 60000L));

			if (token == null || !jwtTokenProvider.validateToken(token)) {
				exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
				return exchange.getResponse().setComplete();
			}

			String id = jwtTokenProvider.getId(token);
			String role = jwtTokenProvider.getRole(token);

			ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
				.header("X-User-Id", id)
				.header("X-User-Role", role)
				.build();

			return chain.filter(exchange.mutate().request(modifiedRequest).build());
		};
	}

	/**
	 * HTTP 헤더에서 Token 파싱
	 * @param request
	 * @return
	 */
	private String extractToken(ServerHttpRequest request) {
		String bearer = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
		if (bearer != null && bearer.startsWith(TOKEN_HEAD)) {
			return bearer.substring(7);
		}
		return null;
	}

	public static class Config {
	}
}



