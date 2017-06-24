/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.infrastructure.jdbc;

import com.codeup.auth.domain.identity.User;
import com.codeup.db.ConfigurableDataSource;
import com.codeup.db.tests.MySQLSetup;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class JdbcUsersTest {
    private JdbcUsers users;

    @Before
    public void loadFixtures() throws Exception {
        MysqlDataSource source = ConfigurableDataSource.using(MySQLSetup.configuration());
        MySQLSetup.truncate(source, "users");
        MySQLSetup.loadDataSet(source, "tests/resources/users.xml");

        users = new JdbcUsers(source.getConnection());
    }

    @Test
    public void it_finds_a_user_by_its_username() {
        assertNotNull(users.identifiedBy("admin"));
    }

    @Test
    public void it_does_not_find_an_unknown_user() {
        assertNull(users.identifiedBy("unknown"));
    }

    @Test
    public void it_registers_a_new_user() {
        User user = User.signUp("luis", "password");

        users.add(user);

        assertNotNull(users.identifiedBy("luis"));
    }
}