package com.flixsync.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class EpisodePK implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "id_tv_show")
    private TvShowEntity tvShow;

    @Column(name = "season")
    private Integer season;

    @Column(name = "number")
    private Integer number;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EpisodePK episodePK)) return false;
        return Objects.equals(getTvShow(), episodePK.getTvShow()) && Objects.equals(getSeason(), episodePK.getSeason()) && Objects.equals(getNumber(), episodePK.getNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTvShow(), getSeason(), getNumber());
    }

    @Override
    public String toString() {
        return '{' +
                "tvShowId: " + tvShow.getId() +
                ", season: " + season +
                ", number: " + number +
                '}';
    }
}