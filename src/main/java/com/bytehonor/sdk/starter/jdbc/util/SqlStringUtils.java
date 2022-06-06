package com.bytehonor.sdk.starter.jdbc.util;

import com.bytehonor.sdk.starter.jdbc.query.SqlConditionGroup;
import com.bytehonor.sdk.starter.jdbc.query.SqlOrder;
import com.bytehonor.sdk.starter.jdbc.query.SqlPage;

public class SqlStringUtils {

    public static String toWhereSql(SqlConditionGroup group) {
        if (group == null) {
            return "";
        }
        return group.toSql();
    }

    public static String toOrderSql(SqlOrder order) {
        if (order == null) {
            return "";
        }
        return order.toSql();
    }

    public static String toLimitSql(SqlPage page) {
        if (page == null) {
            return "";
        }
        return page.toSql();
    }

}