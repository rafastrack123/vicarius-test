# Vicarius Test

## Versions

------------------------------------------------------------
Gradle 8.6
Java 17.0.10
Spring Boot 3.2.4
MySQL 8.3.0
------------------------------------------------------------

## How to Run

To run the project, follow these steps:

1. Start the required services using Docker Compose:

    ```bash
    docker-compose up -d
    ```
   This command will start a MySQL database on localhost:3306 with same startup data that can be found at: `src/test/resources/test-data/data.sql`



2. Build the project using Gradle:

    ```bash
    ./gradlew build
    ```
    

## Test

There are two types of tests in this application: **Unit tests** and **integration tests**.

Both types of tests can be found in the following folder: `src/test/java/com/vicariustest`.

**Alert:** Since the integration tests are located in the same folder as the unit tests, the Gradle build will only be successful if the `docker-compose up` command was executed previously.

## Feature Flag

There is a feature flag responsible for enabling the Elastic repository. This feature flag was added to facilitate testing since the decision to choose a repository to use is time-sensitive.

The feature flag can be found in the following file: `src/main/resources/application.properties`, 
under the property: `feature.toggle.elastic.repository.enabled`.



