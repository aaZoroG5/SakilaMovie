package com.pluralsight.models;

public class Film {

    //properties taken from sakila DB
    int filmID;
    String title;
    String description;
    int releaseYear;
    int length;

    //constructor
    public Film(int filmID, String title, String description, int releaseYear, int length) {
        this.filmID = filmID;
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.length = length;
    }

    //toString to print out film details
    @Override
    public String toString() {
        return "Film{" +
                "filmID=" + filmID +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", releaseYear=" + releaseYear +
                ", length=" + length +
                '}';
    }

}
