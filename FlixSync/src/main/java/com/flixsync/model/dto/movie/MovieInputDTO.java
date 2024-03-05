package com.flixsync.model.dto.movie;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
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
public class MovieInputDTO {
    @NotNull
    @Size(max = 255)
    @Schema(description = "The movie's name")
    private String name;

    @NotNull
    @PositiveOrZero
    @Schema(description = "The amount of hours of the movie")
    private long hours;

    @NotNull
    @PositiveOrZero
    @Schema(description = "The amount of minutes of the movie")
    private long minutes;

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
