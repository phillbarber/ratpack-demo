package com.github.phillbarber.service.dao;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NonBlockingHotDAOTest extends DAOTest{


    @Test
    public void shouldReturnRowFromDB() throws Exception {
        final NonBlockingHotDAO nonBlockingColdDAO = new NonBlockingHotDAO(getSession());
        assertThat(callServiceInRatpackThread(() -> nonBlockingColdDAO.getValueFromDB())).isEqualTo("Amazing Value");
    }
}
