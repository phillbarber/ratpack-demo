package com.github.phillbarber.service.dao;

import com.datastax.driver.core.*;
import ratpack.exec.Promise;

/**
 * Created by pergola on 26/06/16.
 */
public class RatpackCassandraDAO {

    private final Session session;

    public RatpackCassandraDAO(Session session) {
        this.session = session;
    }


    public Promise<ResultSet> execute(Statement statement) {
        return Promise.of(upstream -> {
            ResultSetFuture resultSetFuture = session.executeAsync(statement);
            upstream.accept(resultSetFuture);
        });
    }

    public Promise<String> getValueFromDB() {

        return execute(new SimpleStatement("select dummy from dummy")).map(resultSet -> resultSet.one().getString
                ("dummy"));
    }
}
