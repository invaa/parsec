package com.parsec.repository;

import com.parsec.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for movie entities.
 *
 * Created: 09.02.2015
 *
 * @author Oleksandr Zamkovyi
 * @since ???
 */
@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {
    List<Movie> findByTitleContaining(String title);
}
