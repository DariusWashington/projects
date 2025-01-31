package com.lendingcatalog.model;

import com.lendingcatalog.util.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Movie implements CatalogItem {

    private String id;
    private String movieTitle;
    private String director;
    private LocalDate releaseDate;

    // Constructor with release date as a parameter
    public Movie(String movieTitle, String director, LocalDate releaseDate, String id) {
        this.movieTitle = movieTitle;
        this.director = director;
        this.releaseDate = releaseDate;
        this.id = id;
    }

    // Constructor without release date
    public Movie(String movieTitle, String director, String id) {
        this(movieTitle, director, null, id);  //calls the main constructor with releaseDate as null
    }

    @Override
    public boolean matchesName(String searchStr) {
        return movieTitle.toLowerCase().contains(searchStr.toLowerCase());
    }

    @Override
    public boolean matchesCreator(String searchStr) {
        return director.toLowerCase().contains(searchStr.toLowerCase());
    }

    @Override
    public boolean matchesYear(int searchYear) {
        return releaseDate != null && releaseDate.getYear() == searchYear;  //checks if releaseDate is not null
    }

    @Override
    public void registerItem() {
        if (id == null || id.isEmpty()) {
            id = UUID.randomUUID().toString();
        }
        Logger.log("Movie registered: " + this.toString(), "movie.log");
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String releaseDateStr = (releaseDate != null) ? releaseDate.format(formatter) : "N/A";  //handle null releaseDate

        return "*" + movieTitle + System.lineSeparator()
                + " - Directed by: " + director + System.lineSeparator()
                + " - Released: " + releaseDateStr + System.lineSeparator()
                + " - Id: " + id;
    }
}