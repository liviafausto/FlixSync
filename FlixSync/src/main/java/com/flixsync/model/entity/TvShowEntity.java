package com.flixsync.model.entity;

import com.flixsync.utils.MovieDuration;
import io.hypersistence.utils.hibernate.type.interval.PostgreSQLIntervalType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.time.Duration;

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

    @Override public String toString() {
        return '{' +
                "id: " + id +
                ", title: '" + title + '\'' +
                ", averageDuration: " + MovieDuration.format(averageDuration) +
                ", summary: '" + summary + '\'' +
                ", seasons: " + seasons +
                '}';
    }
}
