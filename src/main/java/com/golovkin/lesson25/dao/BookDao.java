package com.golovkin.lesson25.dao;

import com.golovkin.lesson25.model.Author;
import com.golovkin.lesson25.model.Book;
import com.golovkin.lesson25.model.Genre;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

@Component
public class BookDao extends AbstractDao<Book> {
    private static final String FIND_ALL_QUERY = "SELECT id, name, authorId, genreId, pageCount FROM Book;";
    private static final String INSERT_QUERY = "INSERT INTO Book(name, authorId, genreId, pageCount) VALUES(?, ?, ?, ?);";
    private static final String UPDATE_QUERY = "UPDATE Book SET name = ?, authorId = ?, genreId = ?, pageCount = ? WHERE id = ?;";
    private static final String FIND_BY_ID_QUERY = "SELECT id, name, authorId, genreId, pageCount FROM Book WHERE id = ?;";
    private static final String DELETE_QUERY = "DELETE FROM Book WHERE id = ?;";
    private Dao<Author> authorDao;
    private Dao<Genre> genreDao;

    public BookDao(JdbcTemplate jdbcTemplate, Dao<Author> authorDao, Dao<Genre> genreDao) {
        super(jdbcTemplate);
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @Override
    protected RowMapper<Book> getRowMapper() {
        return (rs, rowNum) -> {
            Book book = new Book();
            book.setName(rs.getString("name"));

            Optional<Author> author = authorDao.get(rs.getLong("authorId"));
            book.setAuthor(author.orElse(null));

            Optional<Genre> genre = genreDao.get(rs.getLong("genreId"));
            book.setGenre(genre.orElse(null));

            book.setPageCount(rs.getInt("pageCount"));
            book.setId(rs.getLong("id"));

            return book;
        };
    }

    @Override
    protected String getFindAllSqlQuery() {
        return FIND_ALL_QUERY;
    }

    @Override
    protected PreparedStatement getCreatePreparedStatement(Book book, Connection connection) throws SQLException {
        Genre genre = book.getGenre();
        createGenreIfNotExist(genre);

        Author author = book.getAuthor();
        createAuthorIfNotExist(author);

        String sql = INSERT_QUERY;

        PreparedStatement preparedStatement = connection
                .prepareStatement(sql, new String[]{"id"});
        preparedStatement.setString(1, book.getName());
        preparedStatement.setLong(2, author.getId());
        preparedStatement.setLong(3, genre.getId());
        preparedStatement.setInt(4, book.getPageCount());

        return preparedStatement;
    }

    private void createAuthorIfNotExist(Author author) {
        if (author.getId() == null) {
            authorDao.create(author);
        }
    }

    private void createGenreIfNotExist(Genre genre) {
        if (genre.getId() == null) {
            genreDao.create(genre);
        }
    }

    @Override
    protected PreparedStatement getUpdatePreparedStatement(Book book, Connection connection) throws SQLException {
        String sql = UPDATE_QUERY;

        Author author = book.getAuthor();
        createAuthorIfNotExist(author);

        Genre genre = book.getGenre();
        createGenreIfNotExist(genre);

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, book.getName());
        preparedStatement.setLong(2, author.getId());
        preparedStatement.setLong(3, genre.getId());
        preparedStatement.setInt(4, book.getPageCount());
        preparedStatement.setLong(5, book.getId());

        return preparedStatement;
    }

    @Override
    protected String getFindByIdSqlQuery() {
        return FIND_BY_ID_QUERY;
    }

    @Override
    protected String getDeleteByIdSqlQuery() {
        return DELETE_QUERY;
    }
}
