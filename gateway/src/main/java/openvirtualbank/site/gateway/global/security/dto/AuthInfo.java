package openvirtualbank.site.gateway.global.security.dto;

public class AuthInfo {
	private final String username;
	private final String role;

	public AuthInfo(String username, String role) {
		this.username = username;
		this.role = role;
	}

	public String getId() {
		return username;
	}

	public String getRole() {
		return this.role;
	}
}
