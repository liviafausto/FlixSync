package com.flixsync.model.entity;

import com.flixsync.model.dto.movie.MovieInputDTO;
import com.flixsync.utils.MovieDuration;
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

    public MovieEntity(MovieInputDTO input){
        BeanUtils.copyProperties(input, this);
        Duration duration = MovieDuration.getDuration(input.getHours(), input.getMinutes());
        this.setDuration(duration);
    }

    @Override
    public String toString() {
        return "{id: '" + id + "', " +
                "name: '" + name + "', " +
                "duration: '" + MovieDuration.format(duration) + "', " +
                "releaseDate: '" + releaseDate + "', " +
                "director: '" + director + "', " +
                "summary: '" + summary + "'}";
    }
}
