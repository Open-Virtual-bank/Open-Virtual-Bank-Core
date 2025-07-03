package openvirtualbank.site.log.cloudwatch.serviceImpl;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import openvirtualbank.site.log.cloudwatch.dto.LogMessage;
import openvirtualbank.site.log.cloudwatch.service.LogConsumerService;
import software.amazon.awssdk.services.cloudwatchlogs.CloudWatchLogsClient;
import software.amazon.awssdk.services.cloudwatchlogs.model.CreateLogGroupRequest;
import software.amazon.awssdk.services.cloudwatchlogs.model.CreateLogStreamRequest;
import software.amazon.awssdk.services.cloudwatchlogs.model.DescribeLogStreamsRequest;
import software.amazon.awssdk.services.cloudwatchlogs.model.DescribeLogStreamsResponse;
import software.amazon.awssdk.services.cloudwatchlogs.model.InputLogEvent;
import software.amazon.awssdk.services.cloudwatchlogs.model.LogStream;
import software.amazon.awssdk.services.cloudwatchlogs.model.PutLogEventsRequest;
import software.amazon.awssdk.services.cloudwatchlogs.model.ResourceAlreadyExistsException;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogConsumerServiceImpl implements LogConsumerService {

	private final CloudWatchLogsClient logsClient;
	private final ObjectMapper objectMapper = new ObjectMapper();

	private static final String LOG_GROUP = "Open-V-Bank";

	public void loggingIntegration(String message) {
		try {
			LogMessage logMessage = objectMapper.readValue(message, LogMessage.class);
			String logStream = "integration-log-stream";
			writeToCloudWatch(logMessage, logStream);
		} catch (Exception e) {
			log.error("Failed to process log message in loggingIntegration: {}", message, e);
		}
	}

	public void loggingEachModule(String message) {
		try {
			LogMessage logMessage = objectMapper.readValue(message, LogMessage.class);
			String logStream = logMessage.getSrcChannel() + "-log-stream";
			writeToCloudWatch(logMessage, logStream);
		} catch (Exception e) {
			log.error("Failed to process log message in loggingEachModule: {}", message, e);
		}
	}

	private void writeToCloudWatch(LogMessage logMessage, String logStream) throws Exception {
		ensureLogGroupAndStream(logStream);

		String logContent = objectMapper.writeValueAsString(logMessage);

		InputLogEvent logEvent = InputLogEvent.builder()
			.message(logContent)
			.timestamp(System.currentTimeMillis())
			.build();

		PutLogEventsRequest putRequest = PutLogEventsRequest.builder()
			.logGroupName(LOG_GROUP)
			.logStreamName(logStream)
			.logEvents(logEvent)
			.sequenceToken(getSequenceToken(logStream))
			.build();

		logsClient.putLogEvents(putRequest);
	}

	private void ensureLogGroupAndStream(String logStream) {
		try {
			logsClient.createLogGroup(CreateLogGroupRequest.builder()
				.logGroupName(LOG_GROUP).build());
		} catch (ResourceAlreadyExistsException ignored) {
		}

		try {
			logsClient.createLogStream(CreateLogStreamRequest.builder()
				.logGroupName(LOG_GROUP)
				.logStreamName(logStream)
				.build());
		} catch (ResourceAlreadyExistsException ignored) {
		}
	}

	private String getSequenceToken(String logStream) {
		DescribeLogStreamsResponse describe = logsClient.describeLogStreams(
			DescribeLogStreamsRequest.builder()
				.logGroupName(LOG_GROUP)
				.logStreamNamePrefix(logStream)
				.build()
		);

		return describe.logStreams().stream()
			.findFirst()
			.map(LogStream::uploadSequenceToken)
			.orElse(null);
	}
}