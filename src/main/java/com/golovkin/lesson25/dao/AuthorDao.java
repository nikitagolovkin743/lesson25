package com.golovkin.lesson25.dao;

import com.golovkin.lesson25.model.Author;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class AuthorDao extends AbstractDao<Author> {
    public AuthorDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected RowMapper<Author> getRowMapper() {
        return (rs, rowNum) -> {
            var author = new Author(rs.getString("firstName"), rs.getString("lastName"), rs.getString("phone"));
            author.setId(rs.getLong("id"));
            return author;
        };
    }

    @Override
    protected String getFindAllSqlQuery() {
        return "SELECT id, firstName, lastName, phone FROM Author;";
    }

    @Override
    protected PreparedStatement getCreatePreparedStatement(Author author, Connection connection) throws SQLException {
        var sql = "INSERT INTO Author(firstName, lastName, phone) VALUES(?, ?, ?);";

        PreparedStatement preparedStatement = connection
                .prepareStatement(sql, new String[]{"id"});
        preparedStatement.setString(1, author.getFirstName());
        preparedStatement.setString(2, author.getLastName());
        preparedStatement.setString(3, author.getPhone());

        return preparedStatement;
    }

    @Override
    protected PreparedStatement getUpdatePreparedStatement(Author author, Connection connection) throws SQLException {
        var sql = "UPDATE Author SET firstName = ?, lastName = ?, phone = ? WHERE id = ?;";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, author.getFirstName());
        preparedStatement.setString(2, author.getLastName());
        preparedStatement.setString(3, author.getPhone());
        preparedStatement.setLong(4, author.getId());

        return preparedStatement;
    }

    @Override
    protected String getFindByIdSqlQuery() {
        return "SELECT id, firstName, lastName, phone FROM Author WHERE id = ?;";
    }

    @Override
    protected String getDeleteByIdSqlQuery() {
        return "DELETE FROM Author WHERE id = ?;";
    }
}
