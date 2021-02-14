package com.golovkin.lesson25.dao;

import com.golovkin.lesson25.model.Identifiable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<T extends Identifiable> implements Dao<T> {
    private JdbcTemplate jdbcTemplate;

    public AbstractDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<T> findAll() {
        return jdbcTemplate.query(getFindAllSqlQuery(), getRowMapper());
    }

    protected abstract String getFindAllSqlQuery();

    @Override
    public Long create(T entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> getCreatePreparedStatement(entity, connection), keyHolder);

        var generatedKey = (long) keyHolder.getKey();
        entity.setId(generatedKey);

        return generatedKey;
    }

    protected abstract PreparedStatement getCreatePreparedStatement(T entity, Connection connection) throws SQLException;

    @Override
    public Optional<T> get(Long id) {
        var entity = jdbcTemplate.queryForObject(getFindByIdSqlQuery(), getRowMapper(), id);

        return Optional.ofNullable(entity);
    }

    protected abstract String getFindByIdSqlQuery();

    @Override
    public void update(T entity, Long id) {
        jdbcTemplate.update(connection -> getUpdatePreparedStatement(entity, connection));
    }

    protected abstract PreparedStatement getUpdatePreparedStatement(T entity, Connection connection) throws SQLException;

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(getDeleteByIdSqlQuery(), id);
    }

    protected abstract String getDeleteByIdSqlQuery();

    protected abstract RowMapper<T> getRowMapper();
}
