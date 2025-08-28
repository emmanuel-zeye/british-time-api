package com.smartbear.britishspokentime.api;

import com.smartbear.britishspokentime.api.dto.SpeakRequest;
import com.smartbear.britishspokentime.api.dto.SpeakResponse;
import com.smartbear.britishspokentime.core.SpeakingStyle;
import com.smartbear.britishspokentime.core.TimeSpeaker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalTime;
import java.util.Map;
import org.springframework.web.bind.annotation.*;

/** REST controller exposing endpoints to "speak" time in different styles. */
@RestController
@RequestMapping("/api/v1/time")
@Tag(name = "Time Speak API", description = "Convert HH:mm times into spoken English")
public class TimeSpeakController {

  private final Map<SpeakingStyle, TimeSpeaker> speakers;

  /**
   * Creates a controller with a registry of TimeSpeaker implementations keyed by their style.
   *
   * @param speakers map of SpeakingStyle to concrete TimeSpeaker beans
   */
  public TimeSpeakController(Map<SpeakingStyle, TimeSpeaker> speakers) {
    this.speakers = speakers;
  }

  /**
   * Speak the provided time using the requested style.
   *
   * @param req request containing a 24h time (HH:mm) and optional style (defaults to BRITISH)
   * @return a response with the chosen style and its spoken representation
   */
  @Operation(
      summary = "Speak time (POST)",
      description = "Converts a 12h HH:mm time to spoken English using the selected style.",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "OK",
            content = @Content(schema = @Schema(implementation = SpeakResponse.class))),
        @ApiResponse(responseCode = "400", description = "Validation error", content = @Content)
      })
  @PostMapping("/speak")
  public SpeakResponse speak(
      @RequestBody @Valid @Parameter(description = "Input time and style") SpeakRequest req) {
    return getSpeakResponse(req);
  }

  /**
   * Speak the provided time using GET query parameters.
   *
   * @param req request with query parameters time and optional style
   * @return a response with the chosen style and its spoken representation
   */
  @Operation(
      summary = "Speak time (GET)",
      description = "Converts a 12h HH:mm time to spoken English using the selected style.",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "OK",
            content = @Content(schema = @Schema(implementation = SpeakResponse.class))),
        @ApiResponse(responseCode = "400", description = "Validation error", content = @Content)
      })
  @GetMapping("/speak")
  public SpeakResponse speakGet(@Valid @ModelAttribute SpeakRequest req) {
    return getSpeakResponse(req);
  }

  /**
   * Internal helper to build the response.
   *
   * @param req validated request
   * @return SpeakResponse with input echo and spoken phrase
   */
  private SpeakResponse getSpeakResponse(SpeakRequest req) {
    var lt = LocalTime.parse(req.time());
    var style = req.style() == null ? SpeakingStyle.BRITISH : req.style();
    var spoken = speakers.get(style).speak(lt);
    return new SpeakResponse(req.time(), style, spoken);
  }
}
