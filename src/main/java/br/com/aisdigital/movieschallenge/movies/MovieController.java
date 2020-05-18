package br.com.aisdigital.movieschallenge.movies;

import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/movies")
public class MovieController {

    private MovieRepository repository;
    private ModelMapper modelMapper;

    public MovieController(MovieRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<Page<MovieDTO>> getMovies() {
        Page<Movie> movies = this.repository.findAll(Pageable.unpaged());
        return ResponseEntity.ok(movies.map(it -> modelMapper.map(it, MovieDTO.class)));
    }

    @GetMapping("{id}")
    public ResponseEntity<MovieDTO> getMoviesById(@PathVariable("id") Integer id) {
        Movie movie = this.repository.findById(id).orElseThrow(() -> new MovieException("Movie not found"));
        return ResponseEntity.ok(this.modelMapper.map(movie, MovieDTO.class));
    }

    @ControllerAdvice
    static class RestExceptionHandler {

        @ExceptionHandler(MovieException.class)
        public final ResponseEntity<Object> handleMovieException(final MovieException ex) {
            log.error(ex.getMessage(), ex);
            return ResponseEntity.status(404).body(ex.getMessage());
        }
    }
}
