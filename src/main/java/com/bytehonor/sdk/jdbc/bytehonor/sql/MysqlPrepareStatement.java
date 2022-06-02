package com.bytehonor.sdk.jdbc.bytehonor.sql;

import com.bytehonor.sdk.jdbc.bytehonor.meta.MetaTable;
import com.bytehonor.sdk.jdbc.bytehonor.model.ModelMapper;
import com.bytehonor.sdk.jdbc.bytehonor.query.QueryCondition;
import com.bytehonor.sdk.jdbc.bytehonor.util.MetaParseUtils;

public abstract class MysqlPrepareStatement implements PrepareStatement {

    protected final Class<?> clazz;

    protected final MetaTable table;

    protected final QueryCondition condition;

    public MysqlPrepareStatement(Class<?> clazz, QueryCondition condition) {
        this.clazz = clazz;
        this.table = MetaParseUtils.parse(clazz);
        this.condition = condition;
    }

    @Override
    public <T> void prepare(T model, ModelMapper<T> mapper) {

    }

    public Class<?> getClazz() {
        return clazz;
    }

    public MetaTable getTable() {
        return table;
    }

    public QueryCondition getCondition() {
        return condition;
    }

}
