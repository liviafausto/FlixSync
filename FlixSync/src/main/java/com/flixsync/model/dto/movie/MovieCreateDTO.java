package com.flixsync.model.dto.movie;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
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
public class MovieCreateDTO {
    @NotNull
    @Size(max = 255)
    @Schema(description = "The movie's name")
    private String name;

    @NotNull
    @Schema(description = "The movie's duration, formatted as 'X hours XX minutes'", example = "2 hours 28 minutes")
    private String duration;

    @NotNull
    @Schema(description = "The movie's release date, formatted as 'YYYY-MM-DD'", example = "2010-09-24")
    private LocalDate releaseDate;

    @NotNull
    @Size(max = 100)
    @Schema(description = "The movie's main director")
    private String director;

    @Schema(description = "A brief summary of the movie (no spoilers)")
    private String summary;
}
