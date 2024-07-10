package com.flixsync.documentation;

import com.flixsync.config.ExceptionResponse;
import com.flixsync.exceptions.EntityNotFoundException;
import com.flixsync.exceptions.InvalidParameterException;
import com.flixsync.model.dto.tvshow.TvShowInputDTO;
import com.flixsync.model.dto.tvshow.TvShowOutputDTO;
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

public interface TvShowControllerDoc {

    @Operation(
            summary = "Find all TV shows",
            description = "Finds all the TV shows of the database separated in pages, ordered by the show's id."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Returns a page containing the specified amount of TV shows"),
                    @ApiResponse(responseCode = "400", description = "Invalid parameter provided", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "You don't have permission to access this resource", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "An unexpected error occurred", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    })
            }
    )
    @GetMapping
    ResponseEntity<Page<TvShowOutputDTO>> findAll(
            @RequestParam(name="page", defaultValue = "0") @PositiveOrZero Integer page,
            @RequestParam(name="amount", defaultValue = "10") @Positive Integer amount
    );

    @Operation(
            summary = "Find a TV show by id",
            description = "Finds the TV show associated with a specific id."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Returns the data of a TV show", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = TvShowOutputDTO.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Invalid id provided", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "You don't have permission to access this resource", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "TV show not found", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "An unexpected error occurred", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    })
            }
    )
    @GetMapping("/{id}")
    ResponseEntity<TvShowOutputDTO> findById(
            @PathVariable(name = "id") @Positive Integer id
    ) throws EntityNotFoundException;

    @Operation(
            summary = "Register a TV show",
            description = "Registers a new TV show in the database using the provided data."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Returns the registered data of the TV show", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = TvShowOutputDTO.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Invalid parameter provided", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "You don't have permission to access this resource", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "An unexpected error occurred", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    })
            }
    )
    @PostMapping
    ResponseEntity<TvShowOutputDTO> save(
            @RequestBody @Valid TvShowInputDTO tvShowInput
    ) throws InvalidParameterException;

    @Operation(
            summary = "Update a TV show by id",
            description = "Updates any part of the data of a specified TV show, except its own id or the number of seasons."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Returns the data of the updated TV show", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = TvShowOutputDTO.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Invalid id provided", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "You don't have permission to access this resource", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "TV show not found", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "An unexpected error occurred", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    })
            }
    )
    @PutMapping("/{id}")
    ResponseEntity<TvShowOutputDTO> update(
            @PathVariable(name = "id") @Positive Integer id,
            @RequestBody @Valid TvShowInputDTO tvShowInput
    ) throws EntityNotFoundException, InvalidParameterException;

    @Operation(
            summary = "Remove a TV show by id",
            description = "Removes the TV show associated with a specified id from the database."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "The TV show was successfully removed from the database"),
                    @ApiResponse(responseCode = "400", description = "Invalid id provided", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "You don't have permission to access this resource", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "TV show not found", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "An unexpected error occurred", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
            }
    )
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(
            @PathVariable(name = "id") @Positive Integer id
    ) throws EntityNotFoundException;
}
