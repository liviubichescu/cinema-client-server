package ro.ubb.socketCinema.common.domain;

import java.util.Objects;

public class Movie extends BaseEntity<Long> {
    private String title;
    private String director;
    private String producer;
    private int relesedYear;
    private double buget;

    public Movie() {
    }

    public Movie(Long id, String title, String director, String producer, int relesedYear, double buget) {
        super(id);
        this.title = title;
        this.director = director;
        this.producer = producer;
        this.relesedYear = relesedYear;
        this.buget = buget;
    }

    public String getTitle() {
        return title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getProducer() {
        return producer;
    }

    public int getRelesedYear() {
        return relesedYear;
    }

    public double getBuget() {
        return buget;
    }

    public String getBookToString() {

        return "id= " + super.getId() +
                ", title= " + title +
                ", director= " + director +
                ", producer= " + producer +
                ", relesedyear= " + relesedYear +
                ", buget= " + buget +
                ";";
    }

    @Override
    public String toString() {
        return getBookToString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return relesedYear == movie.relesedYear &&
                Double.compare(movie.buget, buget) == 0 &&
                Objects.equals(title, movie.title) &&
                Objects.equals(director, movie.director) &&
                Objects.equals(producer, movie.producer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), title, director, producer, relesedYear, buget);
    }
}
