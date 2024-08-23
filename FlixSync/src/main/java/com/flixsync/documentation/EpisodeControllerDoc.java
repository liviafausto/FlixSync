package com.flixsync.documentation;

import com.flixsync.config.ExceptionResponse;
import com.flixsync.exceptions.DuplicatedKeyException;
import com.flixsync.exceptions.EntityNotFoundException;
import com.flixsync.exceptions.InvalidParameterException;
import com.flixsync.model.dto.episode.EpisodeInputDTO;
import com.flixsync.model.dto.episode.EpisodeOutputDTO;
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

import java.util.List;

public interface EpisodeControllerDoc {
    @Operation(
            summary = "Find all episodes",
            description = "Finds all the episodes associated with the TV show's id."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Returns a page containing the specified amount of episodes"),
                    @ApiResponse(responseCode = "400", description = "Invalid parameter provided", content = {
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
    @GetMapping
    ResponseEntity<Page<EpisodeOutputDTO>> findAll(
            @PathVariable(name = "id") @Positive Integer tvShowId,
            @RequestParam(name="page", defaultValue = "0") @PositiveOrZero Integer page,
            @RequestParam(name="size", defaultValue = "10") @Positive Integer size
    ) throws EntityNotFoundException;

    @Operation(
            summary = "Find all episodes per season",
            description = "Finds all the episodes associated with the TV show's id and season."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Returns the episodes list of a TV show's specific season"),
                    @ApiResponse(responseCode = "400", description = "Invalid parameter provided", content = {
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
    @GetMapping("/s{season}")
    ResponseEntity<List<EpisodeOutputDTO>> findAllPerSeason(
            @PathVariable(name = "id") @Positive Integer tvShowId,
            @PathVariable(name = "season") @Positive Integer season
    ) throws EntityNotFoundException;

    @Operation(
            summary = "Find an episode by id",
            description = "Finds the episode associated with a specific id."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Returns the data of an episode", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = EpisodeOutputDTO.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Invalid parameter provided", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "You don't have permission to access this resource", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "TV show/Episode not found", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "An unexpected error occurred", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    })
            }
    )
    @GetMapping("/s{season}/e{episode}")
    ResponseEntity<EpisodeOutputDTO> findById(
            @PathVariable(name = "id") @Positive Integer tvShowId,
            @PathVariable(name = "season") @Positive Integer season,
            @PathVariable(name = "episode") @Positive Integer episodeNumber
    ) throws EntityNotFoundException;

    @Operation(
            summary = "Register an episode of a TV show",
            description = "Registers a new episode in the database for a specific TV show using the provided data."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Returns the registered data of the episode", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = EpisodeOutputDTO.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Invalid parameter provided", content = {
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
    @PostMapping
    ResponseEntity<EpisodeOutputDTO> save(
            @PathVariable(name = "id") @Positive Integer tvShowId,
            @RequestBody @Valid EpisodeInputDTO episodeInput
    ) throws EntityNotFoundException, InvalidParameterException, DuplicatedKeyException;

    @Operation(
            summary = "Update an episode by id",
            description = "Updates any part of the data of a specified episode, except its own id."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Returns the updated data of the episode", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = EpisodeOutputDTO.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Invalid parameter provided", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "You don't have permission to access this resource", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "TV show/Episode not found", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "An unexpected error occurred", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    })
            }
    )
    @PutMapping
    ResponseEntity<EpisodeOutputDTO> update(
            @PathVariable(name = "id") @Positive Integer tvShowId,
            @RequestBody @Valid EpisodeInputDTO episodeInput
    ) throws EntityNotFoundException, InvalidParameterException;

}
