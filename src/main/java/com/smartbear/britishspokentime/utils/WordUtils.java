package com.smartbear.britishspokentime.utils;

import java.util.Map;

/** Utility for converting numbers and hours to lower-case English words used by speakers. */
public final class WordUtils {
  private static final Map<Integer, String> BASE =
      Map.ofEntries(
          Map.entry(0, "zero"),
          Map.entry(1, "one"),
          Map.entry(2, "two"),
          Map.entry(3, "three"),
          Map.entry(4, "four"),
          Map.entry(5, "five"),
          Map.entry(6, "six"),
          Map.entry(7, "seven"),
          Map.entry(8, "eight"),
          Map.entry(9, "nine"),
          Map.entry(10, "ten"),
          Map.entry(11, "eleven"),
          Map.entry(12, "twelve"),
          Map.entry(13, "thirteen"),
          Map.entry(14, "fourteen"),
          Map.entry(15, "fifteen"),
          Map.entry(16, "sixteen"),
          Map.entry(17, "seventeen"),
          Map.entry(18, "eighteen"),
          Map.entry(19, "nineteen"),
          Map.entry(20, "twenty"),
          Map.entry(30, "thirty"),
          Map.entry(40, "forty"),
          Map.entry(50, "fifty"));

  public static String number(int n) {
    if (n <= 20 || n % 10 == 0) return BASE.get(n); // 0..20, 30, 40, 50
    int tens = (n / 10) * 10;
    int ones = n % 10;
    return BASE.get(tens) + " " + BASE.get(ones);
  }

  public static String hourAsTwelveHour(int hour24) {
    if (hour24 < 0 || hour24 > 24) {
      throw new IllegalArgumentException("Hour must be 0–23, was: " + hour24);
    }
    int h = hour24 % 12;
    if (h == 0) h = 12;
    return BASE.get(h);
  }

  private WordUtils() {}
}
