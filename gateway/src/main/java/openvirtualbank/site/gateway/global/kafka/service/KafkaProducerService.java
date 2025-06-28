package openvirtualbank.site.gateway.global.kafka.service;

import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public Mono<String> sendMessage(String topic, String key, String message, String returnMessage) {
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, key, message);

        return Mono.fromFuture(future)
                .map(result -> returnMessage) // 컨트롤러 별로 사용자 응답을 다르게 설정하기 위함
                .onErrorResume(e -> Mono.error(new KafkaException("카프카 메세지 전송 실패", e)));
    }

    //todo 규진이형이 만든 GlobalExceptionHandler에 KafkaException 추가
}

