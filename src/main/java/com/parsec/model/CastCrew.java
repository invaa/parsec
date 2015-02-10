package com.parsec.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Cast entity.
 *
 * Created: 09.02.2015
 *
 * @author Oleksandr Zamkovyi
 * @since ???
 */

@Entity
public class CastCrew implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String name;

    public CastCrew(String name, String role, String header, Movie movie) {
        this.name = name;
        this.role = role;
        this.header = header;
        this.movie = movie;
    }

    private String role;
    private String header;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = Movie.class)
    private transient Movie movie;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    @Override
    public String toString() {
        return  "name=" + name +
                ", role=" + role;
    }

    public CastCrew() {
    }
}
