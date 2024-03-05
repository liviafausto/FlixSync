package com.flixsync.model.dto.movie;

import com.flixsync.model.entity.MovieEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieOutputDTO {
    @Schema(description = "The movie's unique identifier", example = "1")
    private Integer id;

    @Schema(description = "The movie's name", example = "Inception")
    private String name;

    @Schema(description = "The movie's duration, formatted as 'X hours XX minutes'", example = "2 hours 28 minutes")
    private String duration;

    @Schema(description = "The movie's release date, formatted as 'YYYY-MM-DD'", example = "2010-09-24")
    private LocalDate releaseDate;

    @Schema(description = "The movie's main director", example = "Christopher Nolan")
    private String director;

    @Schema(description = "A brief summary of the movie (no spoilers)", example = "A thief who steals corporate secrets through the use of dream-sharing technology...")
    private String summary;

    public MovieOutputDTO(MovieEntity entity){
        BeanUtils.copyProperties(entity, this);
        final long DURATION_IN_SECONDS = entity.getDuration().getSeconds();
        final long HOURS = DURATION_IN_SECONDS / 3600;
        final long MINUTES = (DURATION_IN_SECONDS % 3600) / 60;
        final String DURATION_STRING = HOURS + " hours " + MINUTES + " minutes";
        this.setDuration(DURATION_STRING);
    }
}
