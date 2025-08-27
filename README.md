# British Spoken Time (Spring Boot, Java 21)

A small Spring Boot service and library that "speaks" time in two styles:
- British (colloquial): e.g. "quarter to ten", "half past seven", with special cases for "midnight" and "noon".
- Digital: e.g. "six thirty two", using "o'clock" when minutes are 0.

## Build and run
- Run the app:
  - mvn spring-boot:run
- Build a jar:
  - mvn -DskipTests package && java -jar target/british-spoken-time-0.0.1-SNAPSHOT.jar

## API
- Base path: /api/v1/time
- POST /speak and GET /speak
  - Params/body:
    - time: string, required, format HH:mm (e.g. 07:35)
    - style: BRITISH or DIGITAL (optional, defaults to BRITISH)
  - Response: { time, style, spoken }

Examples:
- GET /api/v1/time/speak?time=07:35&style=BRITISH -> twenty five to eight
- POST { "time": "06:32", "style": "DIGITAL" } -> six thirty two

OpenAPI UI: Once the app is running, visit /swagger-ui.html

## Testing
- Run tests: mvn test
- Parameterized tests: This project includes JUnit 5 parameterized tests using @CsvSource and @ValueSource to cover a range of time inputs for both speakers.

## Code quality
- Coverage (JaCoCo): mvn verify generates coverage report at target/site/jacoco/index.html
- Spotless (Google format): mvn validate runs Spotless; it fails the build on violations.

## Design notes
- Strategy pattern: TimeSpeaker is the strategy; BritishTimeSpeaker and DigitalTimeSpeaker are concrete strategies selected via SpeakingStyle. The API layer selects the appropriate strategy from a registry.

## Project structure
- core: Time speaking strategies and utilities
- api: REST endpoints, validation, and problem details
- config: Strategy registry wiring
- utils: Number-to-words utility
