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
public class EpisodePK implements Serializable, Comparable<EpisodePK> {
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
        return Objects.equals(getTvShow(), episodePK.getTvShow())
                && Objects.equals(getSeason(), episodePK.getSeason())
                && Objects.equals(getNumber(), episodePK.getNumber());
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

    @Override
    public int compareTo(EpisodePK other) {
        final boolean sameTvShow = this.getTvShow().equals(other.getTvShow());
        final boolean sameSeason = this.getSeason().equals(other.getSeason());

        if(sameTvShow && sameSeason) {
            return this.getNumber().compareTo(other.getNumber());
        } else if(sameTvShow) {
            return this.getSeason().compareTo(other.getSeason());
        } else {
            return this.getTvShow().getId().compareTo(other.getTvShow().getId());
        }
    }

    public String formatted(){
        return tvShow.getTitle() + " - "
                + getSeasonFormatted() + " "
                + getEpisodeFormatted();
    }

    public String getSeasonFormatted(){
        if(season < 10)
            return "S0" + season;
        else
            return "S" + season;
    }

    public String getEpisodeFormatted(){
        if(number < 10)
            return "E0" + number;
        else
            return "E" + number;
    }
}
