package com.bytehonor.sdk.jdbc.bytehonor.sql;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.jdbc.bytehonor.Student;
import com.bytehonor.sdk.jdbc.bytehonor.query.QueryCondition;

public class SelectPrepareStatementTest {

    private static final Logger LOG = LoggerFactory.getLogger(SelectPrepareStatementTest.class);

    @Test
    public void test() {
        Set<Integer> set = new HashSet<Integer>();
        set.add(1);
        set.add(2);
        set.add(3);
        QueryCondition condition = QueryCondition.create();
        condition.inInt("age", set);
        condition.gt("create_at", System.currentTimeMillis());
        condition.like("nickname", "boy");
        condition.descBy("age");
        PrepareStatement statement = new SelectPrepareStatement(Student.class, condition);
        String sql = statement.sql();
        Object[] args = statement.args();

        LOG.info("sql:{}", sql);
        for (Object arg : args) {
            LOG.info("arg:{}", arg);
        }

        String target = "SELECT id, nickname, age, update_at, create_at FROM tbl_student WHERE age IN (1,2,3) AND create_at > ? AND nickname LIKE ? ORDER BY age DESC LIMIT 0,20";
        assertTrue("test", target.equals(sql) && args.length == 2);
    }

    @Test
    public void testNoCondition() {
        QueryCondition condition = QueryCondition.create();
        PrepareStatement statement = new SelectPrepareStatement(Student.class, condition);
        String sql = statement.sql();
        Object[] args = statement.args();

        LOG.info("testNoCondition sql:{}", sql);
        for (Object arg : args) {
            LOG.info("testNoCondition arg:{}", arg);
        }

        String target = "SELECT id, nickname, age, update_at, create_at FROM tbl_student LIMIT 0,20";
        assertTrue("test", target.equals(sql));
    }

    @Test
    public void testNoConditionNoPage() {
        QueryCondition condition = QueryCondition.create();
        condition.setPage(null);
        PrepareStatement statement = new SelectPrepareStatement(Student.class, condition);
        String sql = statement.sql();
        boolean hasError = false;
        try {
            statement.args();
        } catch (Exception e) {
            hasError = true;
            LOG.error("error {}", e.getMessage());
        }

        LOG.info("testNoConditionNoPage sql:{}", sql);

        String target = "SELECT id, nickname, age, update_at, create_at FROM tbl_student";
        assertTrue("test", target.equals(sql) && hasError);
    }
}
