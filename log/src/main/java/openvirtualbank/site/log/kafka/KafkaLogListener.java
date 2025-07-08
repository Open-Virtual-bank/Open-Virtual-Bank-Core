package openvirtualbank.site.log.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import openvirtualbank.site.log.cloudwatch.service.LogConsumerService;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaLogListener {

	private final LogConsumerService logConsumerService;

	@KafkaListener(topics = "log-module", groupId = "log-consumer-group")
	public void listen(String message) {
		logConsumerService.loggingIntegration(message);
		logConsumerService.loggingEachModule(message);
	}
}

