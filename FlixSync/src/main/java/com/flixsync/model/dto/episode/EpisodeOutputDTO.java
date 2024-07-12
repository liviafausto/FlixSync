package com.flixsync.model.dto.episode;

import com.flixsync.model.entity.EpisodeEntity;
import com.flixsync.utils.DurationUtils;
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
public class EpisodeOutputDTO {
    @Schema(description = "The associated TV show's unique identifier", example = "1")
    private Integer tvShowId;

    @Schema(description = "The associated TV show's title", example = "Evil")
    private String tvShowTitle;

    @Schema(description = "The episode's season number", example = "1")
    private Integer season;

    @Schema(description = "The episode's number", example = "1")
    private Integer number;

    @Schema(description = "The episode's name", example = "Genesis 1")
    private String name;

    @Schema(description = "The episode's duration, formatted as 'X hours XX minutes'", example = "44 minutes")
    private String duration;

    @Schema(description = "The episode's release date, formatted as 'YYYY-MM-DD'", example = "2019-09-26")
    private LocalDate releaseDate;

    @Schema(description = "The episode's main director", example = "Robert King")
    private String director;

    @Schema(description = "A brief summary of the episode (no spoilers)", example = "The Catholic Church hires forensic psychologist Kristen Bouchard to determine...")
    private String summary;

    public EpisodeOutputDTO(EpisodeEntity entity) {
        BeanUtils.copyProperties(entity, this);
        this.setTvShowId(entity.getTvShow().getId());
        this.setTvShowTitle(entity.getTvShow().getTitle());
        final String durationString = DurationUtils.format(entity.getDuration());
        this.setDuration(durationString);
    }

    @Override public String toString() {
        return '{' +
                "tvShowId: " + tvShowId +
                ", tvShowTitle: '" + tvShowTitle + '\'' +
                ", season: " + season +
                ", number: " + number +
                ", name: '" + name + '\'' +
                ", duration: '" + duration + '\'' +
                ", releaseDate: " + releaseDate +
                ", director: '" + director + '\'' +
                ", summary: '" + summary + '\'' +
                '}';
    }
}
