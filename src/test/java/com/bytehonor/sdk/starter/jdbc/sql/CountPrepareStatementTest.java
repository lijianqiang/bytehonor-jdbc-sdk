package com.bytehonor.sdk.starter.jdbc.sql;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.starter.jdbc.Student;
import com.bytehonor.sdk.starter.jdbc.query.QueryCondition;

public class CountPrepareStatementTest {

    private static final Logger LOG = LoggerFactory.getLogger(SelectPrepareStatementTest.class);

    @Test
    public void test() {
        Set<Integer> set = new HashSet<Integer>();
        set.add(1);
        set.add(2);
        set.add(3);
        QueryCondition condition = QueryCondition.create();
        condition.inInt("age", set);
        condition.gt("createAt", System.currentTimeMillis());
        condition.like("nickname", "boy");
        condition.descBy("age");
        PrepareStatement statement = new CountPrepareStatement(Student.class, condition);
        String sql = statement.sql();
        Object[] args = statement.args();

        LOG.info("sql:{}", sql);
        statement.check();

        String target = "SELECT COUNT(id) FROM tbl_student WHERE age IN (1,2,3) AND create_at > ? AND nickname LIKE ?";
        assertTrue("test", target.equals(sql) && args.length == 2);
    }

    @Test
    public void testNoCondition() {
        QueryCondition condition = QueryCondition.create();
        PrepareStatement statement = new CountPrepareStatement(Student.class, condition);
        String sql = statement.sql();
        Object[] args = statement.args();

        LOG.info("testNoCondition:{}", sql);
        statement.check();

        assertTrue("testNoCondition", "SELECT COUNT(id) FROM tbl_student".equals(sql) && args.length == 0);
    }
}