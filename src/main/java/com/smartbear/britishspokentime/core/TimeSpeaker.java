package com.smartbear.britishspokentime.core;

import java.time.LocalTime;

public interface TimeSpeaker {
  SpeakingStyle style();

  /**
   * @param time LocalTime in 24h
   * @return spoken form in lower case, no trailing punctuation
   */
  String speak(LocalTime time);
}
