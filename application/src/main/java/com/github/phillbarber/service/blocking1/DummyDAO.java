package com.github.phillbarber.service.blocking1;

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

    public Observable<String> getRowFromDB() throws Exception{
        Observable<ResultSet> resultSetObservable =
                Observable.just(session.executeAsync(new SimpleStatement("select dummy from dummy")).get());
        return resultSetObservable.map(resultSet -> resultSet.one().getString("dummy"));
    }
}


