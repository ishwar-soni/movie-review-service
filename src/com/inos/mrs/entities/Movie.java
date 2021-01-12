package com.inos.mrs.entities;

import com.inos.mrs.utils.Genere;

import java.time.LocalDate;
import java.util.List;

public class Movie {
    private Integer id;
    private String name;
    private LocalDate releasedDate;
    private List<Genere> generes;

    public Movie(String name, LocalDate releasedDate, List<Genere> generes) {
        this.name = name;
        this.releasedDate = releasedDate;
        this.generes = generes;
    }

    public Movie(Movie movie) {
        this.id = movie.getId();
        this.name = movie.getName();
        this.releasedDate = movie.getReleasedDate();
        this.generes = movie.getGeneres();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getReleasedDate() {
        return releasedDate;
    }

    public void setReleasedDate(LocalDate releasedDate) {
        this.releasedDate = releasedDate;
    }

    public List<Genere> getGeneres() {
        return generes;
    }

    public void setGeneres(List<Genere> generes) {
        this.generes = generes;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", releasedDate=" + releasedDate +
                ", generes=" + generes +
                '}';
    }
}
