package com.smartbear.britishspokentime.api.dto;

import com.smartbear.britishspokentime.core.SpeakingStyle;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;

public record SpeakRequest(
    @Pattern(regexp = "^[0-2]\\d:[0-5]\\d$", message = "time must be HH:mm (24h)")
        @Schema(example = "07:35")
        String time,
    @Schema(defaultValue = "BRITISH") SpeakingStyle style) {}
