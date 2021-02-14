package com.golovkin.lesson25.model;

public class Author implements Identifiable {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;

    public Author() {
    }

    public Author(String firstName, String lastName, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName == null) {
            throw new IllegalArgumentException("Значение не может быть null");
        }

        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName == null) {
            throw new IllegalArgumentException("Значение не может быть null");
        }

        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if (phone == null) {
            throw new IllegalArgumentException("Значение не может быть null");
        }

        this.phone = phone;
    }

    @Override
    public String toString() {
        return String.format("%s %s", getLastName(), getFirstName());
    }
}
