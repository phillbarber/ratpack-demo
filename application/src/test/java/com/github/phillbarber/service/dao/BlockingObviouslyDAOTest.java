package com.github.phillbarber.service.dao;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BlockingObviouslyDAOTest extends DAOTest{


    @Test
    public void shouldReturnRowFromDB(){
        assertThat(new BlockingObviouslyDAO(getSession()).getValueFromDB().toBlocking().first()).isEqualTo("Amazing Value");
    }

}

