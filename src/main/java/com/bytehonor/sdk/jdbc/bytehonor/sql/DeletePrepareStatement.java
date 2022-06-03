package com.bytehonor.sdk.jdbc.bytehonor.sql;

import com.bytehonor.sdk.jdbc.bytehonor.query.QueryCondition;
import com.bytehonor.sdk.jdbc.bytehonor.query.SqlConditionGroup;
import com.bytehonor.sdk.jdbc.bytehonor.util.SqlInjectUtils;
import com.bytehonor.sdk.jdbc.bytehonor.util.SqlStringUtils;

public class DeletePrepareStatement extends MysqlPrepareStatement {

    public DeletePrepareStatement(Class<?> clazz, QueryCondition condition) {
        super(clazz, condition);
    }

    @Override
    public String sql() {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ").append(table.getTableName());

        if (condition.getGroup() == null) {
            throw new RuntimeException("delete sql condition group null");
        }

        sql.append(SqlStringUtils.toWhereSql(condition.getGroup()));
        return sql.toString();
    }

    @Override
    public Object[] args() {
        if (SqlConditionGroup.isArgsEmpty(condition.getGroup())) {
            throw new RuntimeException("delete sql condition group args isEmpty");
        }

        return condition.getGroup().args().toArray();
    }

    @Override
    public int[] types() {
        if (SqlConditionGroup.isArgsEmpty(condition.getGroup())) {
            return new int[0];
        }
        return SqlInjectUtils.listArray(condition.getGroup().types());
    }

}
