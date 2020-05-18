package br.com.aisdigital.movieschallenge.movies;

import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;

import static br.com.aisdigital.movieschallenge.movies.MovieRepositoryTest.getMovie;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MovieController.class)
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieRepository repository;

    @MockBean
    private ModelMapper modelMapper;

    @Test
    public void getMoviesShouldReturnOkStatus() throws Exception {
        // given
        String path = UriComponentsBuilder.fromUriString("/movies").build().getPath();

        Movie movie = getMovie();
        movie.setId(1);

        when(repository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(Collections.singletonList(movie)));

        // when
        mockMvc.perform(get(path)
                .contentType(MediaType.APPLICATION_JSON))

                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size", equalTo(1)));

        // TODO Assert order
    }

    @Test
    public void getMoviesByIdShouldReturnOkStatus() throws Exception {
        // given
        String path = UriComponentsBuilder.fromUriString("/movies/{id}").buildAndExpand(1).getPath();

        Movie movie = getMovie();
        movie.setId(1);

        when(repository.findById(1)).thenReturn(Optional.of(movie));

        when(modelMapper.map(any(Movie.class), eq(MovieDTO.class))).thenReturn(getDTO(movie));

        // when
        mockMvc.perform(get(path)
                .contentType(MediaType.APPLICATION_JSON))

                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.title", equalTo("Bloodshot")))
                .andExpect(jsonPath("$.overview", notNullValue()))
                .andExpect(jsonPath("$.releaseDate", notNullValue()));
    }

    private MovieDTO getDTO(final Movie movie) {
        return new MovieDTO(movie.getId(), movie.getTitle(), movie.getOverview(), movie.getReleaseDate());
    }

    @Test
    public void getMoviesByIdShouldReturnNotFoundStatus() throws Exception {
        // given
        String path = UriComponentsBuilder.fromUriString("/movies/{id}").buildAndExpand(1).getPath();

        // when
        mockMvc.perform(get(path)
                .contentType(MediaType.APPLICATION_JSON))

                // then
                .andExpect(status().isNotFound());
    }
}
