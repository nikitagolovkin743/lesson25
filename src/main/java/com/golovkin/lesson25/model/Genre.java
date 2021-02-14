package com.golovkin.lesson25.model;

public class Genre implements Identifiable {
    private Long id;
    private String name;

    public Genre() {
    }

    public Genre(String name) {
        this.name = name;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return String.format("%s", getName());
    }
}
