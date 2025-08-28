package com.smartbear.britishspokentime;

import static org.assertj.core.api.Assertions.assertThat;

import com.smartbear.britishspokentime.core.BritishTimeSpeaker;
import java.time.LocalTime;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class BritishTimeSpeakerTest {

  private final BritishTimeSpeaker speaker = new BritishTimeSpeaker();

  @ParameterizedTest
  @CsvFileSource(resources = "/british_test_data.csv", numLinesToSkip = 1)
  void givenDigitalTime_whenSpeak_thenReturnBritishTime(String time, String expected) {
    assertThat(speaker.speak(LocalTime.parse(time))).isEqualTo(expected);
  }
}
