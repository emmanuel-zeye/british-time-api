package com.smartbear.britishspokentime.config;

import com.smartbear.britishspokentime.core.BritishTimeSpeaker;
import com.smartbear.britishspokentime.core.DigitalTimeSpeaker;
import com.smartbear.britishspokentime.core.SpeakingStyle;
import com.smartbear.britishspokentime.core.TimeSpeaker;
import java.util.EnumMap;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpeakerRegistry {

  @Bean
  public Map<SpeakingStyle, TimeSpeaker> speakers() {
    Map<SpeakingStyle, TimeSpeaker> map = new EnumMap<>(SpeakingStyle.class);
    map.put(SpeakingStyle.BRITISH, new BritishTimeSpeaker());
    map.put(SpeakingStyle.DIGITAL, new DigitalTimeSpeaker());
    return Map.copyOf(map);
  }
}
