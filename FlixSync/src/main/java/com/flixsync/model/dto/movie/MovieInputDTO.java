package com.flixsync.model.dto.movie;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "name: can't be blank")
    @Size(max = 255, message = "name: too many characters")
    @Schema(description = "The movie's name")
    private String name;

    @PositiveOrZero(message = "hours: must be greater than zero")
    @Schema(description = "The amount of hours of the movie", example = "2")
    private Long hours;

    @PositiveOrZero(message = "minutes: must be greater than zero")
    @Schema(description = "The amount of minutes of the movie", example = "28")
    private Long minutes;

    @Schema(description = "The movie's release date, formatted as 'YYYY-MM-DD'", example = "2010-09-24")
    private LocalDate releaseDate;

    @NotBlank(message = "director: can't be blank")
    @Size(max = 100, message = "director: too many characters")
    @Schema(description = "The movie's main director")
    private String director;

    @Schema(description = "A brief summary of the movie (no spoilers)")
    private String summary;

    @Override
    public String toString() {
        return '{' +
                "name: '" + name + '\'' +
                ", hours: " + hours +
                ", minutes: " + minutes +
                ", releaseDate: " + releaseDate +
                ", director: '" + director + '\'' +
                ", summary: '" + summary + '\'' +
                '}';
    }
}
