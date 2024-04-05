package com.flixsync.model.dto.category;

import com.flixsync.model.dto.movie.MovieOutputDTO;
import com.flixsync.model.entity.CategoryEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryMoviesListDTO {
    @Schema(description = "The category's unique identifier", example = "1")
    private Integer id;

    @Schema(description = "The category's name", example = "Science Fiction")
    private String name;

    @Schema(description = "The list of movies that are in this category")
    private List<MovieOutputDTO> movies;

    public CategoryMoviesListDTO(CategoryEntity entity){
        BeanUtils.copyProperties(entity, this);
        List<MovieOutputDTO> moviesOutput = entity.getMovies().stream().map(MovieOutputDTO::new).toList();
        this.setMovies(moviesOutput);
    }

    @Override
    public String toString() {
        return "{id: '" + id + '\'' +
                ", name: '" + name + '\'' +
                ", movies: '" + movies.toString() + '\'' +
                '}';
    }
}
