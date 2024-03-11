package com.flixsync.documentation;

import com.flixsync.config.ExceptionResponse;
import com.flixsync.exceptions.EntityNotFoundException;
import com.flixsync.exceptions.InvalidParameterException;
import com.flixsync.model.dto.category.CategoryOutputDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface CategoryControllerDoc {
    @Operation(
            summary = "Find all categories",
            description = "Finds all the categories of the database separated in pages, ordered by the category's id."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Returns a page containing the specified amount of categories"),
                    @ApiResponse(responseCode = "400", description = "Invalid parameter provided", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
//                    @ApiResponse(responseCode = "403", description = "You don't have permission to access this resource"),
                    @ApiResponse(responseCode = "500", description = "An unexpected error occurred", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    })
            }
    )
    @GetMapping
    ResponseEntity<Page<CategoryOutputDTO>> findAll(@RequestParam(name="page", defaultValue = "0") @PositiveOrZero Integer page,
                                                    @RequestParam(name="amount", defaultValue = "10") @Positive Integer amount);


    @Operation(
            summary = "Find a category by id",
            description = "Finds the category associated with a specific id."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Returns the data of a category", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryOutputDTO.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Invalid id provided", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Category not found", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
//                    @ApiResponse(responseCode = "403", description = "You don't have permission to access this resource"),
                    @ApiResponse(responseCode = "500", description = "An unexpected error occurred", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
            }
    )
    @GetMapping("/{id}")
    ResponseEntity<CategoryOutputDTO> findById(@PathVariable(name = "id") @Positive Integer id) throws EntityNotFoundException;


    @Operation(
            summary = "Register a category",
            description = "Registers a new category in the database using the provided data."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Returns the registered data of the category", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryOutputDTO.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Invalid parameter provided", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
//                    @ApiResponse(responseCode = "403", description = "You don't have permission to access this resource"),
                    @ApiResponse(responseCode = "500", description = "An unexpected error occurred", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
            }
    )
    @PostMapping
    ResponseEntity<CategoryOutputDTO> save(@RequestParam(name = "name") @NotBlank String name);


    @Operation(
            summary = "Update a category by id",
            description = "Updates the name of a specified category."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Returns the data of the updated category", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryOutputDTO.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Invalid id provided", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Category not found", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
//                    @ApiResponse(responseCode = "403", description = "You don't have permission to access this resource"),
                    @ApiResponse(responseCode = "500", description = "An unexpected error occurred", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
            }
    )
    @PutMapping("/{id}")
    ResponseEntity<CategoryOutputDTO> update(@PathVariable(name = "id") @Positive Integer id,
                                             @RequestParam(name = "name") @NotBlank String name)
            throws EntityNotFoundException, InvalidParameterException;


    @Operation(
            summary = "Remove a category by id",
            description = "Removes the category associated with a specified id from the database."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "The category was successfully removed from the database"),
                    @ApiResponse(responseCode = "400", description = "Invalid id provided", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Category not found", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
//                    @ApiResponse(responseCode = "403", description = "You don't have permission to access this resource"),
                    @ApiResponse(responseCode = "500", description = "An unexpected error occurred", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
            }
    )
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable(name = "id") @Positive Integer id) throws EntityNotFoundException;

}
