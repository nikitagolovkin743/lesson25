package com.golovkin.lesson25.dao;

import com.golovkin.lesson25.model.Genre;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class GenreDao extends AbstractDao<Genre> {

    private static final String FIND_ALL_QUERY = "SELECT id, name FROM Genre;";
    private static final String INSERT_QUERY = "INSERT INTO Genre(name) VALUES(?);";
    private static final String UPDATE_QUERY = "UPDATE Genre SET name = ? WHERE id = ?;";
    private static final String FIND_BY_ID_QUERY = "SELECT id, name FROM Genre WHERE id = ?;";
    private static final String DELETE_QUERY = "DELETE FROM Genre WHERE id = ?;";

    public GenreDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected RowMapper<Genre> getRowMapper() {
        return (rs, rowNum) -> {
            Genre genre = new Genre(rs.getString("name"));
            genre.setId(rs.getLong("id"));
            return genre;
        };
    }

    @Override
    protected String getFindAllSqlQuery() {
        return FIND_ALL_QUERY;
    }

    @Override
    protected PreparedStatement getCreatePreparedStatement(Genre genre, Connection connection) throws SQLException {
        String sql = INSERT_QUERY;

        PreparedStatement preparedStatement = connection
                .prepareStatement(sql, new String[]{"id"});
        preparedStatement.setString(1, genre.getName());

        return preparedStatement;
    }

    @Override
    protected PreparedStatement getUpdatePreparedStatement(Genre genre, Connection connection) throws SQLException {
        String sql = UPDATE_QUERY;

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, genre.getName());
        preparedStatement.setLong(2, genre.getId());

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
