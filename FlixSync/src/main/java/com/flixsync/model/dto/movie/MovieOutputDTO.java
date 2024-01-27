package com.flixsync.model.dto.movie;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieOutputDTO {
    @Schema(description = "The movie's unique identifier")
    private Integer id;

    @Schema(description = "The movie's name")
    private String name;

    @Schema(description = "The movie's duration, formatted as 'X hours XX minutes'", example = "2 hours 28 minutes")
    private String duration;

    @Schema(description = "The movie's release date, formatted as 'YYYY-MM-DD'", example = "2010-09-24")
    private LocalDate releaseDate;

    @Schema(description = "The movie's main director")
    private String director;

    @Schema(description = "A brief summary of the movie (no spoilers)")
    private String summary;
}
