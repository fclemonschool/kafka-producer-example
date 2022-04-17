package com.fclemonschool.kafkademo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fclemonschool.kafkademo.model.Student;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class StudentConsumerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentConsumerService.class);
    private final CountDownLatch latch = new CountDownLatch(1);
    private String payload = null;

    @KafkaListener(topics = "StudentExample", containerFactory = "studentListenerContainerFactory")
    public void listen(Student student) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info(mapper.writeValueAsString(student));
            }
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Invalid json format.", e);
        }
    }

    @KafkaListener(topics = "${test.topic}")
    public void receive(ConsumerRecord<?, ?> consumerRecord) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("received payload='{}'", consumerRecord);
        }
        setPayload(consumerRecord.toString());
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
