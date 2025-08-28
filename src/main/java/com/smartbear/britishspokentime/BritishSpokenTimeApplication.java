package com.smartbear.britishspokentime;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
    info =
        @Info(
            title = "British Spoken Time API",
            version = "v1",
            description = "Convert HH:mm times into spoken English in different styles"))
public class BritishSpokenTimeApplication {

  public static void main(String[] args) {
    SpringApplication.run(BritishSpokenTimeApplication.class, args);
  }
}
