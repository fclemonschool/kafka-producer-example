package com.fclemonschool.kafkademo.service.impl;

import com.fclemonschool.kafkademo.model.Student;
import com.fclemonschool.kafkademo.service.StudentProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
public class StudentProducerServiceImpl implements StudentProducerService {
    private static final String TOPIC = "StudentExample";
    private KafkaTemplate<String, Student> kafkaTemplate;

    @Autowired
    public void setKafkaTemplate(KafkaTemplate<String, Student> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public ListenableFuture<SendResult<String, Student>> sendStudent(String topic, Student student) {
        return kafkaTemplate.send(topic, student);
    }

    @Override
    public ListenableFuture<SendResult<String, Student>> sendStudent(Student student) {
        return kafkaTemplate.send(TOPIC, student);
    }
}
