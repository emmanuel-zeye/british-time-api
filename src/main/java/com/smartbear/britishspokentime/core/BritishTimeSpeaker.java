package com.smartbear.britishspokentime.core;

import com.smartbear.britishspokentime.utils.WordUtils;
import java.time.LocalTime;

/**
 * Speaks time in common British colloquial form, e.g. "quarter to ten", "half past seven". Handles
 * special cases like midnight and noon.
 */
public final class BritishTimeSpeaker implements TimeSpeaker {

  @Override
  public SpeakingStyle style() {
    return SpeakingStyle.BRITISH;
  }

  @Override
  public String speak(LocalTime time) {
    int h = time.getHour();
    int m = time.getMinute();

    // Special labels
    if (h == 0 && m == 0) return "midnight";
    if (h == 12 && m == 0) return "noon"; // 12:00 → noon

    if (m == 0) return WordUtils.hourAsTwelveHour(h) + " o'clock";
    if (m == 15) return "quarter past " + WordUtils.hourAsTwelveHour(h);
    if (m == 30) return "half past " + WordUtils.hourAsTwelveHour(h);
    if (m == 45) return "quarter to " + WordUtils.hourAsTwelveHour(h + 1);

    // “Past” for 1..29, “to” for 31..59
    if (m < 30) {
      String mins = (m == 1) ? "one" : WordUtils.number(m);
      return mins + " past " + WordUtils.hourAsTwelveHour(h);
    }
    int to = 60 - m;
    String mins = (to == 1) ? "one" : WordUtils.number(to);
    return mins + " to " + WordUtils.hourAsTwelveHour(h + 1);
  }
}
