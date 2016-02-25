package com.github.phillbarber.service.dao;

import org.junit.Test;
import org.scassandra.http.client.ActivityClient;
import org.scassandra.http.client.PreparedStatementExecution;
import ratpack.exec.ExecResult;
import ratpack.test.exec.ExecHarness;

import static org.assertj.core.api.Assertions.assertThat;
import static ratpack.rx.RxRatpack.promiseSingle;
import static org.scassandra.matchers.Matchers.*;

public class NonBlockingColdDAOTest extends DAOTest{

    @Test
    public void shouldReturnRowFromDB() throws Exception {
        assertThat(callServiceInRatpackThread(new NonBlockingColdDAO(getSession()))).isEqualTo("Amazing Value");
//TODO add test to verify call is NOT made after calling method
//        PreparedStatementExecution expectedStatement = PreparedStatementExecution.builder()
//                .withPreparedStatementText("select * from dummy")
//                .build();
//        assertThat(getActivityClient().retrievePreparedStatementExecutions(), preparedStatementRecorded(expectedStatement));

    }
//
//    private ActivityClient getActivityClient() {
//        return null;
//    }

    private String callServiceInRatpackThread(NonBlockingColdDAO nonBlockingColdDAO) throws Exception {
        try (ExecHarness harness = ExecHarness.harness()) {
            ExecResult<String> yield = harness.yield(execution -> promiseSingle(nonBlockingColdDAO.getValueFromDB()));
            return yield.getValueOrThrow();
        }
    }
}

