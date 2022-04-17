package com.fclemonschool.kafkademo.service;

import com.fclemonschool.kafkademo.model.Student;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

public interface StudentProducerService {
    ListenableFuture<SendResult<String, Student>> sendStudent(String topic, Student student);
    ListenableFuture<SendResult<String, Student>> sendStudent(Student student);
}
