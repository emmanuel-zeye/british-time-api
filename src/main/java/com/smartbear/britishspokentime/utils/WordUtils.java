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

  /**
   * Converts a number from 0..59 into its lower-case English words.
   *
   * <p>For 0..20 and multiples of 10 up to 50, a single word is returned; for others a two-word
   * form like "twenty one" is produced.
   *
   * @param n integer in the range 0..59
   * @return English words, e.g. 32 -> "thirty two"
   * @throws IllegalArgumentException if n is negative or greater than 59
   */
  public static String number(int n) {
    if (n < 0 || n > 59) {
      throw new IllegalArgumentException("Number must be 0–59, was: " + n);
    }
    if (n <= 20 || n % 10 == 0) return BASE.get(n); // 0..20, 30, 40, 50
    int tens = (n / 10) * 10;
    int ones = n % 10;
    return BASE.get(tens) + " " + BASE.get(ones);
  }

  /**
   * Converts a 24-hour-based hour into its 12-hour spoken form (1..12) as words.
   *
   * <p>Accepts 0..24 to support cases like "quarter to" where 24 maps to 12.
   *
   * @param hour24 hour in 24h space, 0..23
   * @return word for the 12h hour, e.g. 0->"twelve", 13->"one"
   * @throws IllegalArgumentException if hour24 is outside 0..23
   */
  public static String hourAsTwelveHour(int hour24) {
    if (hour24 < 0 || hour24 > 23) {
      throw new IllegalArgumentException("Hour must be 0–23, was: " + hour24);
    }
    int h = hour24 % 12;
    if (h == 0) h = 12;
    return BASE.get(h);
  }

  private WordUtils() {}
}
