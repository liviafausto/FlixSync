package com.flixsync.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flixsync.model.dto.tvshow.TvShowInputDTO;
import com.flixsync.utils.DurationUtils;
import io.hypersistence.utils.hibernate.type.interval.PostgreSQLIntervalType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.beans.BeanUtils;

import java.time.Duration;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Tv_Show")
@Table(name = "Tv_Show")
public class TvShowEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Type(PostgreSQLIntervalType.class)
    @Column(name = "average_duration")
    private Duration averageDuration;

    @Column(name = "summary")
    private String summary;

    @Column(name = "seasons")
    private Integer seasons;

    @JsonIgnore
    @ManyToMany(mappedBy = "tvShows")
    private Set<CategoryEntity> categories;

    @JsonIgnore
    @OneToMany(mappedBy = "episodeId.tvShow")
    private Set<EpisodeEntity> episodes;

    public TvShowEntity(TvShowInputDTO input){
        BeanUtils.copyProperties(input, this);
        Duration averageDuration = DurationUtils.getDuration(null, input.getMinutesPerEpisode());
        this.setAverageDuration(averageDuration);
        this.setSeasons(0);
        this.setCategories(new HashSet<>());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TvShowEntity tvShow)) return false;
        return Objects.equals(getId(), tvShow.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return '{' +
                "id: " + id +
                ", title: '" + title + '\'' +
                ", averageDuration: '" + DurationUtils.format(averageDuration) + '\'' +
                ", summary: '" + summary + '\'' +
                ", seasons: " + seasons +
                ", categories: " + categories.toString() +
                '}';
    }
}
