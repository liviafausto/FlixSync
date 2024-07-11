package com.flixsync.model.dto.tvshow;

import com.flixsync.model.dto.category.CategoryOutputDTO;
import com.flixsync.model.entity.TvShowEntity;
import com.flixsync.utils.DurationUtils;
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
public class TvShowOutputDTO {
    @Schema(description = "The show's unique identifier", example = "1")
    private Integer id;

    @Schema(description = "The show's title", example = "Evil")
    private String title;

    @Schema(description = "The show's average duration per episode, formatted as 'X hours XX minutes'", example = "48 minutes")
    private String averageDuration;

    @Schema(description = "A brief summary of the show (no spoilers)", example = "A skeptical clinical psychologist joins a priest-in-training and a blue collar contractor...")
    private String summary;

    @Schema(description = "The show's total number of seasons", example = "4")
    private Integer seasons;

    @Schema(description = "The list of categories that are associated with this TV show")
    private List<CategoryOutputDTO> categories;

    public TvShowOutputDTO(TvShowEntity entity){
        BeanUtils.copyProperties(entity, this);
        String averageDurationString = DurationUtils.format(entity.getAverageDuration());
        this.setAverageDuration(averageDurationString);
        List<CategoryOutputDTO> categoriesOutput = entity.getCategories().stream().map(CategoryOutputDTO::new).toList();
        this.setCategories(categoriesOutput);
    }

    @Override
    public String toString() {
        return '{' +
                "id: " + id +
                ", title: '" + title + '\'' +
                ", averageDuration: '" + averageDuration + '\'' +
                ", summary: '" + summary + '\'' +
                ", seasons: " + seasons +
                ", categories: " + categories.toString() +
                '}';
    }
}
