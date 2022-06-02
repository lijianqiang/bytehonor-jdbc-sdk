package com.bytehonor.sdk.jdbc.bytehonor.jdbc;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.bytehonor.sdk.jdbc.bytehonor.model.ModelMapper;
import com.bytehonor.sdk.jdbc.bytehonor.query.QueryCondition;
import com.bytehonor.sdk.jdbc.bytehonor.sql.PrepareStatement;
import com.bytehonor.sdk.jdbc.bytehonor.sql.PrepareStatementBuilder;

public class JdbcProxyDao {

    private static final Logger LOG = LoggerFactory.getLogger(JdbcProxyDao.class);

    private final JdbcTemplate jdbcTemplate;

    public JdbcProxyDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public <T> List<T> query(Class<?> clazz, QueryCondition condition, RowMapper<T> mapper) {
        Objects.requireNonNull(clazz, "clazz");
        Objects.requireNonNull(condition, "condition");
        Objects.requireNonNull(mapper, "mapper");

        PrepareStatement statement = PrepareStatementBuilder.select(clazz, condition);
        String sql = statement.sql();
        Object[] args = statement.args();

        log(clazz, sql, args);

        return jdbcTemplate.query(sql, args, statement.types(), mapper);
    }

    public <T> T queryById(Class<T> clazz, Long id, RowMapper<T> mapper) {
        Objects.requireNonNull(clazz, "clazz");
        Objects.requireNonNull(id, "id");

        List<T> result = query(clazz, QueryCondition.id(id), mapper);
        return DataAccessUtils.uniqueResult(result);
    }

    public int delete(Class<?> clazz, QueryCondition condition) {
        Objects.requireNonNull(clazz, "clazz");
        Objects.requireNonNull(condition, "condition");

        PrepareStatement statement = PrepareStatementBuilder.delete(clazz, condition);
        String sql = statement.sql();
        Object[] args = statement.args();

        log(clazz, sql, args);

        return jdbcTemplate.update(statement.sql(), statement.args());
    }

    public int deleteById(Class<?> clazz, Long id) {
        Objects.requireNonNull(clazz, "clazz");
        Objects.requireNonNull(id, "id");

        PrepareStatement statement = PrepareStatementBuilder.deleteById(clazz, id);
        String sql = statement.sql();
        Object[] args = statement.args();

        log(clazz, sql, args);

        return jdbcTemplate.update(statement.sql(), statement.args());
    }

    public int count(Class<?> clazz, QueryCondition condition) {
        Objects.requireNonNull(clazz, "clazz");
        Objects.requireNonNull(condition, "condition");

        PrepareStatement statement = PrepareStatementBuilder.count(clazz, condition);
        String sql = statement.sql();
        Object[] args = statement.args();

        log(clazz, sql, args);

        return jdbcTemplate.queryForObject(sql, args, statement.types(), Integer.class);
    }

    private void log(Class<?> clazz, String sql, Object[] args) {
        if (LOG.isDebugEnabled() == false) {
            return;
        }
        LOG.debug("clazz:{}, sql:{}", clazz.getSimpleName(), sql);
        for (Object arg : args) {
            LOG.debug("arg:{}", arg);
        }
    }

    public <T> int update(T model, QueryCondition condition, ModelMapper<T> mapper) {
        Objects.requireNonNull(model, "model");
        Objects.requireNonNull(condition, "condition");
        Objects.requireNonNull(mapper, "mapper");

        final Class<? extends Object> clazz = model.getClass();
        PrepareStatement statement = PrepareStatementBuilder.update(clazz, condition);
        statement.prepare(model, mapper);

        String sql = statement.sql();
        Object[] args = statement.args();
        log(clazz, sql, args);

        return jdbcTemplate.update(sql, args);
    }
}
