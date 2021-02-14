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
    public GenreDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected RowMapper<Genre> getRowMapper() {
        return (rs, rowNum) -> {
            var genre = new Genre(rs.getString("name"));
            genre.setId(rs.getLong("id"));
            return genre;
        };
    }

    @Override
    protected String getFindAllSqlQuery() {
        return "SELECT id, name FROM Genre;";
    }

    @Override
    protected PreparedStatement getCreatePreparedStatement(Genre genre, Connection connection) throws SQLException {
        var sql = "INSERT INTO Genre(name) VALUES(?);";

        PreparedStatement preparedStatement = connection
                .prepareStatement(sql, new String[]{"id"});
        preparedStatement.setString(1, genre.getName());

        return preparedStatement;
    }

    @Override
    protected PreparedStatement getUpdatePreparedStatement(Genre genre, Connection connection) throws SQLException {
        var sql = "UPDATE Genre SET name = ? WHERE id = ?;";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, genre.getName());
        preparedStatement.setLong(2, genre.getId());

        return preparedStatement;
    }

    @Override
    protected String getFindByIdSqlQuery() {
        return "SELECT id, name FROM Genre WHERE id = ?;";
    }

    @Override
    protected String getDeleteByIdSqlQuery() {
        return "DELETE FROM Genre WHERE id = ?;";
    }
}
