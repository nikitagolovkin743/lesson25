package com.golovkin.lesson25.model;

public class Book implements Identifiable {
    private Long id;
    private Genre genre;
    private Author author;
    private String name;
    private Integer pageCount;

    public Book() {
    }

    public Book(String name, Genre genre, Author author, Integer pageCount) {
        this.genre = genre;
        this.author = author;
        this.name = name;
        this.pageCount = pageCount;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Значение не может быть null");
        }

        this.name = name;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    @Override
    public String toString() {
        return String.format("%s", getName());
    }
}
