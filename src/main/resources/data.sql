INSERT INTO Author(id, firstName, lastName, phone) VALUES(1, 'Александр', 'Островский', '+79106934510');
INSERT INTO Author(id, firstName, lastName, phone) VALUES(2, 'Николай', 'Лесков', '+79206934510');

INSERT INTO Genre(id, name) VALUES(1, 'очерк');
INSERT INTO Genre(id, name) VALUES(2, 'драма');

INSERT INTO Book(id, genreId, authorId, name, pageCount) VALUES(1, 2, 1, 'Гроза', 50);
INSERT INTO Book(id, genreId, authorId, name, pageCount) VALUES(2, 2, 1, 'Бесприданница', 192);
INSERT INTO Book(id, genreId, authorId, name, pageCount) VALUES(3, 1, 2, 'Леди Макбет Мценского уезда', 288);