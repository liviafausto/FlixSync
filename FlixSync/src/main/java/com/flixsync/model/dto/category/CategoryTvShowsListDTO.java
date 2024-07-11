package com.flixsync.model.dto.category;

import com.flixsync.model.dto.tvshow.TvShowOutputDTO;
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
public class CategoryTvShowsListDTO {
    @Schema(description = "The category's unique identifier", example = "1")
    private Integer id;

    @Schema(description = "The category's name", example = "Crime")
    private String name;

    @Schema(description = "The list of TV shows that are part of this category")
    private List<TvShowOutputDTO> tvShows;

    public CategoryTvShowsListDTO(CategoryEntity entity){
        BeanUtils.copyProperties(entity, this);
        List<TvShowOutputDTO> tvShows = entity.getTvShows().stream().map(TvShowOutputDTO::new).toList();
        this.setTvShows(tvShows);
    }

    @Override
    public String toString() {
        return '{' +
                "id: " + id +
                ", name: '" + name + '\'' +
                ", tvShows: " + tvShows.toString() +
                '}';
    }
}
