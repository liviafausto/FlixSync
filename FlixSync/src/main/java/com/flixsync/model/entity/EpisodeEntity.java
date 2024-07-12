package com.flixsync.model.entity;

import com.flixsync.utils.DurationUtils;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@Entity(name = "Episode")
@Table(name = "Episode")
public class EpisodeEntity {
    @EmbeddedId
    private EpisodePK episodeId;

    @Column(name = "name")
    private String name;

    @Column(name = "duration")
    private Duration duration;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "director")
    private String director;

    @Column(name = "summary")
    private String summary;

    public EpisodeEntity(){
        episodeId = new EpisodePK();
    }

    public TvShowEntity getTvShow(){
        return episodeId.getTvShow();
    }

    public void setTvShow(TvShowEntity tvShow){
        episodeId.setTvShow(tvShow);
    }

    public Integer getSeason(){
        return episodeId.getSeason();
    }

    public void setSeason(Integer season){
        episodeId.setSeason(season);
    }

    public Integer getNumber(){
        return episodeId.getNumber();
    }

    public void setNumber(Integer number){
        episodeId.setNumber(number);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EpisodeEntity that)) return false;
        return Objects.equals(getEpisodeId(), that.getEpisodeId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getEpisodeId());
    }

    @Override
    public String toString() {
        return '{' +
                "episodeId: " + episodeId.toString() +
                ", name: '" + name + '\'' +
                ", duration: '" + DurationUtils.format(duration) + '\'' +
                ", releaseDate: " + releaseDate +
                ", director: '" + director + '\'' +
                ", summary: '" + summary + '\'' +
                '}';
    }
}
