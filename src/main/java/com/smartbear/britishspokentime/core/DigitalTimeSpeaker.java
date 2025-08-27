package com.smartbear.britishspokentime.core;

import com.smartbear.britishspokentime.utils.WordUtils;
import java.time.LocalTime;

/**
 * Speaks time by reading the hour and minute values, e.g. "six thirty two". Special-cases midnight
 * and noon, and uses "o'clock" when minutes are 0.
 */
public final class DigitalTimeSpeaker implements TimeSpeaker {

  @Override
  public SpeakingStyle style() {
    return SpeakingStyle.DIGITAL;
  }

  @Override
  public String speak(LocalTime time) {
    int h = time.getHour();
    int m = time.getMinute();

    if (h == 0 && m == 0) return "midnight";
    if (h == 12 && m == 0) return "noon";

    String hour = WordUtils.hourAsTwelveHour(h);
    if (m == 0) return hour + " o'clock";

    return hour + " " + WordUtils.number(m);
  }
}
