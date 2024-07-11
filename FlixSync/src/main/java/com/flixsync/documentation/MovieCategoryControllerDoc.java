package com.flixsync.documentation;

import com.flixsync.config.ExceptionResponse;
import com.flixsync.exceptions.EntityNotFoundException;
import com.flixsync.exceptions.InvalidParameterException;
import com.flixsync.model.dto.category.CategoryMoviesListDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface MovieCategoryControllerDoc {
    @Operation(
            summary = "Find movies by category",
            description = "Finds all movies associated with the category's id."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Returns a category and its movies list", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryMoviesListDTO.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Invalid id provided", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "You don't have permission to access this resource", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Category not found", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "An unexpected error occurred", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
            }
    )
    @GetMapping
    ResponseEntity<CategoryMoviesListDTO> findMoviesByCategory(
            @RequestParam(name = "categoryId") @Positive Integer categoryId
    ) throws EntityNotFoundException;

    @Operation(
            summary = "Add movie to category",
            description = "Adds a movie to a category, both specified by id."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Returns the updated category with its movies list"),
                    @ApiResponse(responseCode = "400", description = "Invalid parameter provided", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "You don't have permission to access this resource", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Category/Movie not found", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "An unexpected error occurred", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
            }
    )
    @PutMapping
    ResponseEntity<CategoryMoviesListDTO> addMovie(
            @RequestParam(name = "categoryId") @Positive Integer categoryId,
            @RequestParam(name = "movieId") @Positive Integer movieId
    ) throws EntityNotFoundException, InvalidParameterException;


    @Operation(
            summary = "Remove movie from category",
            description = "Removes a movie from a category, both specified by id."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Returns the updated category with its movies list"),
                    @ApiResponse(responseCode = "400", description = "Invalid parameter provided", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "You don't have permission to access this resource", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Category/Movie not found", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "An unexpected error occurred", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
            }
    )
    @DeleteMapping
    ResponseEntity<CategoryMoviesListDTO> removeMovie(
            @RequestParam(name = "categoryId") @Positive Integer categoryId,
            @RequestParam(name = "movieId") @Positive Integer movieId
    ) throws EntityNotFoundException, InvalidParameterException;
}
