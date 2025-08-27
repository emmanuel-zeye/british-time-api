package com.smartbear.britishspokentime.api;

import com.smartbear.britishspokentime.api.dto.SpeakRequest;
import com.smartbear.britishspokentime.api.dto.SpeakResponse;
import com.smartbear.britishspokentime.core.SpeakingStyle;
import com.smartbear.britishspokentime.core.TimeSpeaker;
import jakarta.validation.Valid;
import java.time.LocalTime;
import java.util.Map;
import org.springframework.web.bind.annotation.*;

/** REST controller exposing endpoints to "speak" time in different styles. */
@RestController
@RequestMapping("/api/v1/time")
public class TimeSpeakController {

  private final Map<SpeakingStyle, TimeSpeaker> speakers;

  public TimeSpeakController(Map<SpeakingStyle, TimeSpeaker> speakers) {
    this.speakers = speakers;
  }

  @PostMapping("/speak")
  public SpeakResponse speak(@RequestBody @Valid SpeakRequest req) {
    return getSpeakResponse(req);
  }

  @GetMapping("/speak")
  public SpeakResponse speakGet(@Valid @ModelAttribute SpeakRequest req) {
    return getSpeakResponse(req);
  }

  private SpeakResponse getSpeakResponse(SpeakRequest req) {
    var lt = LocalTime.parse(req.time());
    var style = req.style() == null ? SpeakingStyle.BRITISH : req.style();
    var spoken = speakers.get(style).speak(lt);
    return new SpeakResponse(req.time(), style, spoken);
  }
}
