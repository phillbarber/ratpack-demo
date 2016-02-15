package com.github.phillbarber.service.nonblocking1;

import com.datastax.driver.core.ResultSet;
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
        Observable<ResultSet> resultSetObservable = Observable.from(resultSetFuture);
        resultSetObservable.toBlocking();
        return resultSetObservable.map(resultSet -> resultSet.one().getString("dummy"));
    }
}
