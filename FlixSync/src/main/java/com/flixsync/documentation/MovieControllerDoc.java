package com.flixsync.documentation;

import com.flixsync.config.ExceptionResponse;
import com.flixsync.exceptions.EntityNotFoundException;
import com.flixsync.exceptions.InvalidParameterException;
import com.flixsync.model.dto.movie.MovieInputDTO;
import com.flixsync.model.dto.movie.MovieOutputDTO;
import com.flixsync.model.dto.movie.MovieUpdateInputDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface MovieControllerDoc {

    @Operation(
            summary = "Find all movies",
            description = "Finds all the movies of the database separated in pages, ordered by the movie's id."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Returns a page containing the specified amount of movies"),
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
    ResponseEntity<Page<MovieOutputDTO>> findAll(@RequestParam(name="page", defaultValue = "0") @PositiveOrZero Integer page,
                                                 @RequestParam(name="amount", defaultValue = "10") @Positive Integer amount);


    @Operation(
            summary = "Find a movie by id",
            description = "Finds the movie associated with a specific id."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Returns the data of a movie", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MovieOutputDTO.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Invalid id provided", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Movie not found", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
//                    @ApiResponse(responseCode = "403", description = "You don't have permission to access this resource"),
                    @ApiResponse(responseCode = "500", description = "An unexpected error occurred", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
            }
    )
    @GetMapping("/{id}")
    ResponseEntity<MovieOutputDTO> findById(@PathVariable(name = "id") @Positive Integer id) throws EntityNotFoundException;


    @Operation(
            summary = "Register a movie",
            description = "Registers a new movie in the database using the provided data."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Returns the registered data of the movie", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MovieOutputDTO.class))
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
    ResponseEntity<MovieOutputDTO> save(@RequestBody @Valid MovieInputDTO movieInput);


    @Operation(
            summary = "Update a movie by id",
            description = "Updates any part of the data of a specified movie, except its own id."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Returns the data of the updated movie", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MovieOutputDTO.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Invalid id provided", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Movie not found", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
//                    @ApiResponse(responseCode = "403", description = "You don't have permission to access this resource"),
                    @ApiResponse(responseCode = "500", description = "An unexpected error occurred", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
            }
    )
    @PutMapping("/{id}")
    ResponseEntity<MovieOutputDTO> update(@PathVariable(name = "id") @Positive Integer id,
                                          @RequestBody @Valid MovieUpdateInputDTO movieUpdateInput)
            throws EntityNotFoundException, InvalidParameterException;


    @Operation(
            summary = "Remove a movie by id",
            description = "Removes the movie associated with a specified id from the database."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "The movie was successfully removed from the database"),
                    @ApiResponse(responseCode = "400", description = "Invalid id provided", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Movie not found", content = {
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
