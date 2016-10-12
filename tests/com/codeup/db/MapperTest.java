/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.db;

import org.junit.Test;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

public class MapperTest {
    @Test
    public void it_does_not_bind_parameters_if_empty_array_is_given()
        throws SQLException {
        PreparedStatement statement = mock(PreparedStatement.class);

        Mapper.map(statement, new Object[]{});

        verify(statement, never()).setObject(anyInt(), anyObject());
    }

    @Test
    public void it_does_not_bind_parameters_if_empty_map_is_given()
        throws SQLException {
        PreparedStatement statement = mock(PreparedStatement.class);

        Mapper.map(statement, Collections.emptyMap());

        verify(statement, never()).setObject(anyInt(), anyObject());
    }

    @Test
    public void it_binds_a_single_parameter_in_array() throws SQLException {
        PreparedStatement statement = mock(PreparedStatement.class);

        Mapper.map(statement, new Object[]{"luis"});

        verify(statement, times(1)).setObject(1, "luis");
    }

    @Test
    public void it_binds_a_single_parameter_in_map() throws SQLException {
        PreparedStatement statement = mock(PreparedStatement.class);
        Map<Integer, Object> parameters = new LinkedHashMap<>();
        parameters.put(1, "luis");

        Mapper.map(statement, parameters);

        verify(statement, times(1)).setObject(1, "luis");
    }

    @Test
    public void it_binds_several_parameters_in_array() throws SQLException {
        PreparedStatement statement = mock(PreparedStatement.class);

        Mapper.map(statement, new Object[]{"luis", "montealegreluis@gmail.com", 15});

        verify(statement).setObject(1, "luis");
        verify(statement).setObject(2, "montealegreluis@gmail.com");
        verify(statement).setObject(3, 15);
    }

    @Test
    public void it_binds_several_parameters_in_map() throws SQLException {
        PreparedStatement statement = mock(PreparedStatement.class);

        Map<Integer, Object> parameters = new LinkedHashMap<>();
        parameters.put(1, "luis");
        parameters.put(2, "montealegreluis@gmail.com");
        parameters.put(3, 15);

        Mapper.map(statement, parameters);

        verify(statement).setObject(1, "luis");
        verify(statement).setObject(2, "montealegreluis@gmail.com");
        verify(statement).setObject(3, 15);
    }
}
