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
    private static final String FIND_ALL_QUERY = "SELECT id, firstName, lastName, phone FROM Author;";
    private static final String INSERT_QUERY = "INSERT INTO Author(firstName, lastName, phone) VALUES(?, ?, ?);";
    private static final String UPDATE_QUERY = "UPDATE Author SET firstName = ?, lastName = ?, phone = ? WHERE id = ?;";
    private static final String FIND_BY_ID_QUERY = "SELECT id, firstName, lastName, phone FROM Author WHERE id = ?;";
    private static final String DELETE_QUERY = "DELETE FROM Author WHERE id = ?;";

    public AuthorDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected RowMapper<Author> getRowMapper() {
        return (rs, rowNum) -> {
            Author author = new Author(rs.getString("firstName"), rs.getString("lastName"), rs.getString("phone"));
            author.setId(rs.getLong("id"));
            return author;
        };
    }

    @Override
    protected String getFindAllSqlQuery() {
        return FIND_ALL_QUERY;
    }

    @Override
    protected PreparedStatement getCreatePreparedStatement(Author author, Connection connection) throws SQLException {
        String sql = INSERT_QUERY;

        PreparedStatement preparedStatement = connection
                .prepareStatement(sql, new String[]{"id"});
        preparedStatement.setString(1, author.getFirstName());
        preparedStatement.setString(2, author.getLastName());
        preparedStatement.setString(3, author.getPhone());

        return preparedStatement;
    }

    @Override
    protected PreparedStatement getUpdatePreparedStatement(Author author, Connection connection) throws SQLException {
        String sql = UPDATE_QUERY;

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, author.getFirstName());
        preparedStatement.setString(2, author.getLastName());
        preparedStatement.setString(3, author.getPhone());
        preparedStatement.setLong(4, author.getId());

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
