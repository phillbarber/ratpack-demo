package com.github.phillbarber.service.dao;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NonBlockingColdDAOTest extends DAOTest{

    @Test
    public void shouldReturnRowFromDB() throws Exception {
        final NonBlockingColdDAO nonBlockingColdDAO = new NonBlockingColdDAO(getSession());
        assertThat(callServiceInRatpackThread(() -> nonBlockingColdDAO.getValueFromDB())).isEqualTo("Amazing Value");
    }


}

