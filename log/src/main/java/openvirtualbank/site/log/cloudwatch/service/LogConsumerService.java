package openvirtualbank.site.log.cloudwatch.service;

import org.springframework.stereotype.Service;

@Service
public interface LogConsumerService {

	void loggingIntegration(String message);

	void loggingEachModule(String message);
}
