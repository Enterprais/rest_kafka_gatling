package com.example.test_rest_gatling.controller;

import com.example.test_rest_gatling.model.SensorData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.kafka.core.KafkaTemplate;

@RestController
@RequestMapping("/api")
public class RESTController {

    private final KafkaTemplate<String, SensorData> kafkaTemplate;

    @Autowired
    public RESTController(KafkaTemplate<String, SensorData> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    @PostMapping(path="/sensor")
    public void addSensorData(@RequestBody SensorData sensorData) {
        kafkaTemplate.send("sensor", sensorData);

    }
}
