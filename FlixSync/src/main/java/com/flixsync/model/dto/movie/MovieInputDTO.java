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
    @NotNull(message = "name: can't be null")
    @Size(max = 255, message = "name: too many characters")
    @Schema(description = "The movie's name")
    private String name;

    @NotNull(message = "hours: can't be null")
    @PositiveOrZero(message = "hours: must be greater than zero")
    @Schema(description = "The amount of hours of the movie", example = "2")
    private long hours;

    @NotNull(message = "minutes: can't be null")
    @PositiveOrZero(message = "minutes: must be greater than zero")
    @Schema(description = "The amount of minutes of the movie", example = "28")
    private long minutes;

    @NotNull(message = "releaseDate: can't be null")
    @Schema(description = "The movie's release date, formatted as 'YYYY-MM-DD'", example = "2010-09-24")
    private LocalDate releaseDate;

    @NotNull(message = "director: can't be null")
    @Size(max = 100, message = "director: too many characters")
    @Schema(description = "The movie's main director")
    private String director;

    @Schema(description = "A brief summary of the movie (no spoilers)")
    private String summary;
}
