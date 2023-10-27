package com.example.gatling;

import com.example.dao.SensorDataDAOImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.gatling.javaapi.core.OpenInjectionStep;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.time.Duration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class SensorRestSimulation extends Simulation {

    private final String URL_BASE = "http://localhost:8080";

    public SensorRestSimulation() {
        setUp(scenarioBuilder.injectOpen(postEndpointInjectionProfile())
                .protocols(setupProtocolForSimulation()));
    }

    private HttpProtocolBuilder setupProtocolForSimulation() {
        return http.baseUrl(URL_BASE)
                .header("content-type", "application/json");
    }

    private Iterator<Map<String, Object>> feedData() {
        Iterator<Map<String, Object>> iterator;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        SensorDataDAOImpl sensorDataDAO = new SensorDataDAOImpl();

        iterator = Stream.generate(() -> {
            Map<String, Object> jsontMap = new HashMap<>();

            try {
                jsontMap.put("data", objectMapper.writeValueAsString(sensorDataDAO.getSensorData()));
            }
            catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            return jsontMap;
        }).iterator();

        return iterator;
    }

    private ScenarioBuilder scenarioBuilder = scenario("test-rest-api").feed(feedData())
            .exec(
            http("rest_request")
                    .post("/api/sensor")
                    .body(StringBody("#{data}"))
                    .check(status().is(200)));


    private OpenInjectionStep.RampRate.RampRateOpenInjectionStep postEndpointInjectionProfile() {
        int totalDesiredUserCount = 200;
        double userRampUpPerInterval = 20;
        double rampUpIntervalSeconds = 10;
        int totalRampUptimeSeconds = 60;
        int steadyStateDurationSeconds = 20;

        return rampUsersPerSec(userRampUpPerInterval / (rampUpIntervalSeconds / 60)).to(totalDesiredUserCount)
                .during(Duration.ofSeconds(totalRampUptimeSeconds + steadyStateDurationSeconds));
    }

}
