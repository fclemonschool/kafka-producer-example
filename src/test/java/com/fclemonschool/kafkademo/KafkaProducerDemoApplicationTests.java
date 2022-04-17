package com.fclemonschool.kafkademo;

import com.fclemonschool.kafkademo.model.Student;
import com.fclemonschool.kafkademo.service.StudentConsumerService;
import com.fclemonschool.kafkademo.service.StudentProducerService;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9093", "port=9093" })
class KafkaProducerDemoApplicationTests {
    @Value("${test.topic}")
    private String topic;

    private StudentProducerService studentProducerService;

    private StudentConsumerService studentConsumerService;

    @Autowired
    public void setStudentProducerService(StudentProducerService studentProducerService) {
        this.studentProducerService = studentProducerService;
    }

    @Autowired
    public void setStudentConsumerService(StudentConsumerService studentConsumerService) {
        this.studentConsumerService = studentConsumerService;
    }

    @Test
    void kafkaProducerConsumerTest()
            throws Exception {
        studentProducerService.sendStudent(topic, new Student(1, "hi", "ho"));
        studentConsumerService.getLatch().await(10000, TimeUnit.MILLISECONDS);

        assertThat(studentConsumerService.getLatch().getCount(), equalTo(0L));
        assertThat(studentConsumerService.getPayload(), containsString("embedded-test-topic"));
    }
}
