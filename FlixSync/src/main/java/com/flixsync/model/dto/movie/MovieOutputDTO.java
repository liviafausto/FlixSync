package com.flixsync.model.dto.movie;

import com.flixsync.model.dto.category.CategoryOutputDTO;
import com.flixsync.utils.MovieDuration;
import com.flixsync.model.entity.MovieEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.util.List;

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

    @Schema(description = "The list of categories that are associated with this movie")
    private List<CategoryOutputDTO> categories;

    public MovieOutputDTO(MovieEntity entity){
        BeanUtils.copyProperties(entity, this);
        String durationString = MovieDuration.format(entity.getDuration());
        this.setDuration(durationString);
        List<CategoryOutputDTO> categoriesOutput = entity.getCategories().stream().map(CategoryOutputDTO::new).toList();
        this.setCategories(categoriesOutput);
    }

    @Override
    public String toString() {
        return '{' +
                "id: " + id +
                ", name: '" + name + '\'' +
                ", duration: '" + duration + '\'' +
                ", releaseDate: " + releaseDate +
                ", director: '" + director + '\'' +
                ", summary: '" + summary + '\'' +
                ", categories: " + categories.toString() +
                '}';
    }
}
