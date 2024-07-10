package com.flixsync.model.dto.tvshow;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TvShowInputDTO {
    @Size(max = 255, message = "title: too many characters")
    @Schema(description = "The show's title")
    private String title;

    @Positive(message = "minutes: must be greater than zero")
    @Schema(description = "The average amount of minutes each episode of the show has", example = "48")
    private Long minutesPerEpisode;

    @Schema(description = "A brief summary of the show (no spoilers)")
    private String summary;

    @Override
    public String toString() {
        return '{' +
                "title: '" + title + '\'' +
                ", minutesPerEpisode: " + minutesPerEpisode +
                ", summary: '" + summary + '\'' +
                '}';
    }
}
