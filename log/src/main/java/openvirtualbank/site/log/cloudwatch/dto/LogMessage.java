package openvirtualbank.site.log.cloudwatch.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LogMessage {
	@NotBlank
	private String logLevel;
	@NotBlank
	private String timestamp;
	private String callApiPath;
	private String apiMethod;
	private String txId;
	private String srcIp;
	private String deviceInfo;
	private String memberId;
	private String requestId;
	@NotBlank
	private String srcChannel;
	private String eventType;
	private String ledgerCode;
	private String errorMessage;
}
