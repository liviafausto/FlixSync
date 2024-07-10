package com.flixsync.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flixsync.model.dto.movie.MovieInputDTO;
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
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Movie")
@Table(name = "Movie")
public class MovieEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Type(PostgreSQLIntervalType.class)
    @Column(name = "duration")
    private Duration duration;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "director")
    private String director;

    @Column(name = "summary")
    private String summary;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "Movie_Category",
            joinColumns = @JoinColumn(name = "id_movie"),
            inverseJoinColumns = @JoinColumn(name = "id_category")
    )
    private Set<CategoryEntity> categories;

    public MovieEntity(MovieInputDTO input){
        BeanUtils.copyProperties(input, this);
        Duration duration = DurationUtils.getDuration(input.getHours(), input.getMinutes());
        this.setDuration(duration);
    }

    @Override
    public String toString() {
        return '{' +
                "id: " + id +
                ", name: '" + name + '\'' +
                ", duration: '" + DurationUtils.format(duration) + '\'' +
                ", releaseDate: " + releaseDate +
                ", director: '" + director + '\'' +
                ", summary: '" + summary + '\'' +
                ", categories: " + categories.toString() +
                '}';
    }
}
