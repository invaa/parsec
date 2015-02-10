package com.parsec.model;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * Movie entity.
 *
 * Created: 09.02.2015
 *
 * @author Oleksandr Zamkovyi
 * @since ???
 */

@Entity
public class Movie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String title;
    private String rating;

    @Size(min = 0, max = 9000)
    @Lob
    @Column(name = "description", length = 3000)
    private String description;
    private String runtime;
    private String budget;
    private String poster;
    private String releaseDate;
    private String parentalGuide;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<CastCrew> castAndCrew;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Genre> genres;

    public Movie() {
    }

    public Movie(String id) {
        this.id = id;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getParentalGuide() {
        return parentalGuide;
    }

    public void setParentalGuide(String parentalGuide) {
        this.parentalGuide = parentalGuide;
    }

    public List<CastCrew> getCastAndCrew() {
        return castAndCrew;
    }

    public void setCastAndCrew(List<CastCrew> castAndCrew) {
        this.castAndCrew = castAndCrew;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
