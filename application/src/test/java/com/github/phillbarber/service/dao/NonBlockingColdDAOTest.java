package com.github.phillbarber.service.dao;

import org.junit.Test;
import ratpack.exec.ExecResult;
import ratpack.test.exec.ExecHarness;

import static org.assertj.core.api.Assertions.assertThat;
import static ratpack.rx.RxRatpack.promiseSingle;

public class NonBlockingColdDAOTest extends DAOTest{

    @Test
    public void shouldReturnRowFromDB() throws Exception {
        assertThat(callServiceInRatpackThread(new NonBlockingColdDAO(getSession()))).isEqualTo("Amazing Value");
    }

    private String callServiceInRatpackThread(NonBlockingColdDAO nonBlockingColdDAO) throws Exception {
        try (ExecHarness harness = ExecHarness.harness()) {
            ExecResult<String> yield = harness.yield(execution -> promiseSingle(nonBlockingColdDAO.getValueFromDB()));
            return yield.getValueOrThrow();
        }
    }
}

