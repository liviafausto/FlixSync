package com.flixsync.model.dto.episode;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EpisodeInputDTO {
    @NotNull(message = "season: can't be null")
    @Positive(message = "season: must be greater than zero")
    @Schema(description = "The episode's season number", example = "1")
    private Integer season;

    @NotNull(message = "number: can't be null")
    @Positive(message = "number: must be greater than zero")
    @Schema(description = "The episode's number", example = "1")
    private Integer number;

    @Size(max = 255, message = "name: too many characters")
    @Schema(description = "The episode's name", example = "Genesis 1")
    private String name;

    @Positive(message = "minutes: must be greater than zero")
    @Schema(description = "The episode's duration in minutes", example = "44")
    private Long minutes;

    @Schema(description = "The episode's release date, formatted as 'YYYY-MM-DD'", example = "2019-09-26")
    private LocalDate releaseDate;

    @Size(max = 100, message = "director: too many characters")
    @Schema(description = "The episode's main director", example = "Robert King")
    private String director;

    @Schema(description = "A brief summary of the episode (no spoilers)", example = "The Catholic Church hires forensic psychologist Kristen Bouchard to determine...")
    private String summary;

    @Override
    public String toString() {
        return '{' +
                "season: " + season +
                ", number: " + number +
                ", name: '" + name + '\'' +
                ", minutes: " + minutes +
                ", releaseDate: " + releaseDate +
                ", director: '" + director + '\'' +
                ", summary: '" + summary + '\'' +
                '}';
    }
}
