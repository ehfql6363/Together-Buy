package com.ssafy.TogetherBuyNotification.notification.consumer;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.ssafy.TogetherBuyNotification.notification.service.MessageService;

@Service
public class KafkaMessageConsumer {

    private final MessageService messageService;

    public KafkaMessageConsumer(MessageService messageService) {
        this.messageService = messageService;
    }

    @KafkaListener(topics = "test-topic", groupId = "test-group")
    public void listen(ConsumerRecord<String, String> record, Consumer<String, String> consumer) {
        System.out.println("Received message: " + record.value());

        try {
            // 비즈니스 로직 수행
            messageService.processMessage(record.value());

            // 성공적으로 처리된 경우 Offset을 수동으로 커밋
            consumer.commitSync();
        } catch (Exception e) {
            System.err.println("Message processing failed: " + e.getMessage());
            // 실패한 경우 롤백 가능
        }
    }
}
