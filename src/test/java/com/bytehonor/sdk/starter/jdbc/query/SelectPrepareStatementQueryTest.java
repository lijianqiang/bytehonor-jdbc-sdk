package com.bytehonor.sdk.starter.jdbc.query;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.define.spring.query.QueryCondition;
import com.bytehonor.sdk.starter.jdbc.Student;
import com.bytehonor.sdk.starter.jdbc.condition.SqlAdapter;
import com.bytehonor.sdk.starter.jdbc.sql.PrepareStatement;
import com.bytehonor.sdk.starter.jdbc.sql.SelectPrepareStatement;

public class SelectPrepareStatementQueryTest {

    private static final Logger LOG = LoggerFactory.getLogger(SelectPrepareStatementQueryTest.class);

    @Test
    public void test() {
        Set<Integer> set = new HashSet<Integer>();
        set.add(1);
        set.add(2);
        set.add(3);
        QueryCondition condition = QueryCondition.and();
        condition.integers("age", set);
        condition.gt("createAt", System.currentTimeMillis());
        condition.like("nickname", "boy");
        condition.descBy("age");
        PrepareStatement statement = new SelectPrepareStatement(Student.class, SqlAdapter.convert(condition));
        String sql = statement.sql();
        Object[] args = statement.args();

        LOG.info("test sql:{}", sql);
        statement.check();

        String target = "SELECT id, nickname, age, update_at, create_at FROM tbl_student WHERE age IN (1,2,3) AND create_at > ? AND nickname LIKE ? ORDER BY age DESC LIMIT 0,20";
        assertTrue("test", target.equals(sql) && args.length == 2);
    }

    @Test
    public void testNoCondition() {
        QueryCondition condition = QueryCondition.and();
        PrepareStatement statement = new SelectPrepareStatement(Student.class, SqlAdapter.convert(condition));
        String sql = statement.sql();
        Object[] args = statement.args();

        LOG.info("testNoCondition sql:{}", sql);
        statement.check();

        String target = "SELECT id, nickname, age, update_at, create_at FROM tbl_student LIMIT 0,20";
        assertTrue("testNoCondition", target.equals(sql) && args.length == 0);
    }

    @Test
    public void testEqEmpty() {
        QueryCondition condition = QueryCondition.and();
        condition.eq("nickname", "");
        PrepareStatement statement = new SelectPrepareStatement(Student.class, SqlAdapter.convert(condition));
        String sql = statement.sql();
        Object[] args = statement.args();

        LOG.info("testEqEmpty sql:{}", sql);
        statement.check();

        String target = "SELECT id, nickname, age, update_at, create_at FROM tbl_student WHERE nickname = ? LIMIT 0,20";
        assertTrue("testEqEmpty", target.equals(sql) && args.length == 1);
    }
}