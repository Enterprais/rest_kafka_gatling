package com.example.dao;

import com.example.model.SensorData;
import com.github.javafaker.Faker;

import java.time.LocalDateTime;

public class SensorDataDAOImpl implements SensorDataDAO {

    Faker faker = new Faker();
    @Override
    public SensorData getSensorData() {
        SensorData data = new SensorData();
        data.setName(faker.color().name());
        data.setValue(faker.number().numberBetween(0,100));
        data.setDateTime(LocalDateTime.now());
        return data;
    }
}
