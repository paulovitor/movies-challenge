package br.com.aisdigital.movieschallenge.movies;

import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class MovieRepositoryTest {

    @Autowired
    private MovieRepository repository;
    private Integer id;

    @BeforeEach
    public void setUp() {
        Movie saved = repository.save(getMovie());
        this.id = saved.getId();
    }

    // TODO Find All Pageable and order

    @Test
    public void findByIdShouldReturnMovie() {
        // when
        Optional<Movie> result = repository.findById(this.id);

        // then
        assertTrue(result.isPresent());
    }

    @Test
    public void findByIdShouldReturnEmptyOptional() {
        // when
        Optional<Movie> result = repository.findById(this.id + 1);

        // then
        assertFalse(result.isPresent());
    }

    static Movie getMovie() {
        return new Movie(null, "Bloodshot", "After he and his wife are murdered, marine Ray Garrison is resurrected by a team of scientists", LocalDate.of(2020, 3, 13));
    }
}