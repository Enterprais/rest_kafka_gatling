# rest_kafka_gatling
Test REST and Kafka service with Galtling.

To start this project [docker](https://docs.docker.com/engine/install/), [maven](https://maven.apache.org/install.html) and [jdk-17](https://docs.oracle.com/en/java/javase/17/install/#Java-Platform%2C-Standard-Edition) required.

- First start docker container with kafka and kafdrop.

    ```bash
    cd docker
    docker compose up
    # kafdrop on localhost:9000
    ```
- Start REST server.

    ```bash
    cd rest_app
    mvn spring-boot:run
    # server start on localhost:8080 and listen /api/sensor/
    ```
    - Test REST api requst template.
    ```bash
    curl -X POST -H "Content-Type: application/json" -d
    {
        "name": "STRING",
        "dateTime": "yyyy-MM-dd'T'HH:mm:ss.SSS",
        "value": INT
    }
    localhost:8080/api/sensor
    ```
- Start Gatling test and see file with results.
    ```bash
    cd gatling_app
    mvn gatling:test
    ```


