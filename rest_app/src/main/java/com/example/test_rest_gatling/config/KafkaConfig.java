package com.example.test_rest_gatling.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic sensorTopic()
    {
        return TopicBuilder.name("sensor")
                .partitions(1)
                .replicas(1)
                .build();
    }

}
