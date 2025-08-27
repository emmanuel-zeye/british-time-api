package com.smartbear.britishspokentime;

import static org.assertj.core.api.Assertions.assertThat;

import com.smartbear.britishspokentime.api.dto.SpeakResponse;
import com.smartbear.britishspokentime.core.SpeakingStyle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiE2ETest {

  @LocalServerPort int port;

  private RestClient client() {
    return RestClient.builder()
        .baseUrl("http://localhost:" + port)
        .defaultHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
        .build();
  }

  // GET tests
  @ParameterizedTest
  @CsvFileSource(resources = "/british_test_data.csv", numLinesToSkip = 1)
  void givenDigitalTime_whenCallSpeakEndpoint_thenReturnsBritishTime(String time, String expected) {
    ResponseEntity<SpeakResponse> res =
        client()
            .get()
            .uri(
                uri ->
                    uri.path("/api/v1/time/speak")
                        .queryParam("time", time)
                        .queryParam("style", "BRITISH")
                        .build())
            .retrieve()
            .toEntity(SpeakResponse.class);

    assertThat(res.getStatusCode().is2xxSuccessful()).isTrue();
    assertThat(res.getBody()).isNotNull();
    SpeakResponse body = res.getBody();

    assertThat(body.time()).isEqualTo(time);
    assertThat(body.style()).isEqualTo(SpeakingStyle.BRITISH);
    assertThat(body.spoken()).isEqualTo(expected);
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/digital_test_data.csv", numLinesToSkip = 1)
  void givenDigitalTime_whenCallSpeakEndpoint_thenReturnsDigitalTime(String time, String expected) {
    ResponseEntity<SpeakResponse> res =
        client()
            .get()
            .uri(
                uri ->
                    uri.path("/api/v1/time/speak")
                        .queryParam("time", time)
                        .queryParam("style", "DIGITAL")
                        .build())
            .retrieve()
            .toEntity(SpeakResponse.class);

    assertThat(res.getStatusCode().is2xxSuccessful()).isTrue();
    assertThat(res.getBody()).isNotNull();
    SpeakResponse body = res.getBody();

    assertThat(body.time()).isEqualTo(time);
    assertThat(body.style()).isEqualTo(SpeakingStyle.DIGITAL);
    assertThat(body.spoken()).isEqualTo(expected);
  }

  @Test
  void speak_endpoint_validation_error_returns_problem_detail() {
    // invalid time format triggers @Pattern and our @ExceptionHandler
    ResponseEntity<ProblemDetail> res =
        client()
            .get()
            .uri(
                uri ->
                    uri.path("/api/v1/time/speak")
                        .queryParam("time", "7:3") // invalid HH:mm
                        .build())
            .retrieve()
            .onStatus(HttpStatusCode::isError, (req, resp) -> {})
            .toEntity(ProblemDetail.class);

    assertThat(res.getStatusCode().value()).isEqualTo(400);
    assertThat(res.getBody()).isNotNull();
    ProblemDetail pd = res.getBody();
    assertThat(pd.getTitle()).isEqualTo("Bad Request");
    assertThat(pd.getDetail()).contains("One or more request fields are invalid.");
  }

  // POST tests
  @ParameterizedTest
  @CsvFileSource(resources = "/digital_test_data.csv", numLinesToSkip = 1)
  void speak_endpoint_digital_post_returns_dto(String time, String expected) {
    String bodyJson =
        """
      { "time": "%s", "style": "DIGITAL" }
      """
            .formatted(time);

    ResponseEntity<SpeakResponse> res =
        client()
            .post()
            .uri("/api/v1/time/speak")
            .contentType(MediaType.APPLICATION_JSON)
            .body(bodyJson)
            .retrieve()
            .toEntity(SpeakResponse.class);

    assertThat(res.getStatusCode().is2xxSuccessful()).isTrue();
    assertThat(res.getBody()).isNotNull();
    SpeakResponse body = res.getBody();

    assertThat(body.time()).isEqualTo(time);
    assertThat(body.style()).isEqualTo(SpeakingStyle.DIGITAL);
    assertThat(body.spoken()).isEqualTo(expected);
  }

  @Test
  void speak_endpoint_post_defaults_to_british_when_style_omitted() {
    String bodyJson =
        """
      { "time": "07:30" }
      """;

    ResponseEntity<SpeakResponse> res =
        client()
            .post()
            .uri("/api/v1/time/speak")
            .contentType(MediaType.APPLICATION_JSON)
            .body(bodyJson)
            .retrieve()
            .toEntity(SpeakResponse.class);

    assertThat(res.getStatusCode().is2xxSuccessful()).isTrue();
    assertThat(res.getBody()).isNotNull();
    SpeakResponse body = res.getBody();

    assertThat(body.time()).isEqualTo("07:30");
    assertThat(body.style()).isEqualTo(SpeakingStyle.BRITISH);
    assertThat(body.spoken()).isEqualTo("half past seven");
  }

  @Test
  void speak_endpoint_post_validation_error_returns_problem_detail() {
    // invalid HH:mm -> should trigger @Valid + @Pattern and our @ExceptionHandler
    String invalidJson =
        """
      { "time": "7:3" }
      """;

    ResponseEntity<ProblemDetail> res =
        client()
            .post()
            .uri("/api/v1/time/speak")
            .contentType(MediaType.APPLICATION_JSON)
            .body(invalidJson)
            .retrieve()
            .onStatus(HttpStatusCode::isError, (req, resp) -> {})
            .toEntity(ProblemDetail.class);

    assertThat(res.getStatusCode().value()).isEqualTo(400);
    assertThat(res.getBody()).isNotNull();
    ProblemDetail pd = res.getBody();
    assertThat(pd.getTitle()).isEqualTo("Bad Request");
    assertThat(pd.getDetail()).contains("One or more request fields are invalid.");
  }
}
