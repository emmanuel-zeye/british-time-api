package com.smartbear.britishspokentime.api.dto;

import com.smartbear.britishspokentime.core.SpeakingStyle;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "SpeakResponse", description = "Spoken representation of a time")
public record SpeakResponse(
    @Schema(example = "06:32") String time,
    @Schema(example = "DIGITAL") SpeakingStyle style,
    @Schema(example = "six thirty two") String spoken) {}
