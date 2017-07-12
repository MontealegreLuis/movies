/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.infrastructure.jdbc;

import com.codeup.auth.domain.identity.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class UsersMapperTest {
    @Test
    public void it_maps_correctly_a_user() {
        UsersMapper mapper = new UsersMapper();
        List<Object> values = new ArrayList<>();
        values.addAll(Arrays.asList(7L, "luis", "changeme"));

        User user = mapper.mapRow(values);

        assertEquals(7L, user.id());
        assertEquals("luis", user.username().value());
        assertTrue("changeme".equals(user.password().toString()));
    }
}
