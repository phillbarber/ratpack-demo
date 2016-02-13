package com.github.phillbarber.service;

import com.datastax.driver.core.ResultSetFuture;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SimpleStatement;
import rx.Observable;

public class DummyDAO {

    private Session session;

    public DummyDAO(Session session) {
        this.session = session;
    }


    public Observable<String> getRowFromDB() {

        ResultSetFuture resultSetFuture = session.executeAsync(new SimpleStatement("select dummy from dummy"));

        return Observable.from(resultSetFuture).map(resultSet -> resultSet.one().getString("dummy"));


    }
}
