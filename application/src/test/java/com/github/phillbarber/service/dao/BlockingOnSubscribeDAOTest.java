package com.github.phillbarber.service.dao;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BlockingOnSubscribeDAOTest extends DAOTest {


    @Test
    public void shouldReturnRowFromDB(){
        assertThat(new BlockingOnSubscribeDAO(getSession()).getValueFromDB().toBlocking().first()).isEqualTo("Amazing Value");
    }


}
