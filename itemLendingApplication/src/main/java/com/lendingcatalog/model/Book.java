package com.lendingcatalog.model;

import com.lendingcatalog.util.Logger;

import java.time.LocalDate;
import java.util.UUID;

public class Book implements CatalogItem {
    private String id;
    private String title;
    private String author;

    private LocalDate publishDate;

    public Book(String title, String author, String publishDate) {

        this.title = title;
        this.author = author;
        this.publishDate = LocalDate.parse(publishDate);
    }


    @Override
    public boolean matchesName(String name) {

        return title.toLowerCase().contains(name.toLowerCase());
    }

    @Override
    public boolean matchesCreator(String creator) {
        return author.toLowerCase().contains(creator.toLowerCase());
    }

    @Override
    public boolean matchesYear(int searchYear) {
        return publishDate != null && publishDate.getYear() == searchYear;
    }

    @Override
    public void registerItem() {
        id = UUID.randomUUID().toString();
        Logger.log("Book created: " + this.toString(), "book.log");
    }
    
    @Override
    public String toString() {
        return "* " + title + System.lineSeparator()
                + " - Written by: " + author + System.lineSeparator()
                + " - Published: " + publishDate + System.lineSeparator()
                + " - Id: " + id;
    }

}

