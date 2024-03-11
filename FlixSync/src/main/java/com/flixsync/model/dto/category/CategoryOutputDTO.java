package com.flixsync.model.dto.category;

import com.flixsync.model.entity.CategoryEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryOutputDTO {
    @Schema(description = "The category's unique identifier", example = "1")
    private Integer id;

    @Schema(description = "The category's name", example = "Science Fiction")
    private String name;

    public CategoryOutputDTO(CategoryEntity entity){
        BeanUtils.copyProperties(entity, this);
    }
}
