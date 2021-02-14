CREATE TABLE Author (
    id IDENTITY AUTO_INCREMENT PRIMARY KEY,
    firstName VARCHAR(255) NOT NULL,
    lastName VARCHAR(255) NOT NULL,
    phone VARCHAR(255) NOT NULL,
    UNIQUE (firstName, lastName, phone)
);

CREATE TABLE Genre (
    id IDENTITY AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE Book (
    id IDENTITY AUTO_INCREMENT PRIMARY KEY,
    genreId INTEGER NULL,
    authorId INTEGER NULL,
    name VARCHAR(255) NOT NULL,
    pageCount INTEGER NULL,
    FOREIGN KEY (genreId) REFERENCES Genre(id),
    FOREIGN KEY (authorId) REFERENCES Author(id),
    UNIQUE (authorId, name)
);