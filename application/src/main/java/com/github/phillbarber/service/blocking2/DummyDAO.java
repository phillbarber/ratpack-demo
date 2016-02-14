package com.github.phillbarber.service.blocking2;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.ResultSetFuture;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SimpleStatement;
import rx.Observable;

import java.util.concurrent.ExecutionException;

public class DummyDAO {

    private Session session;

    public DummyDAO(Session session) {
        this.session = session;
    }

    public Observable<String> getRowFromDB() throws Exception{
        ResultSetFuture resultSetFuture = session.executeAsync(new SimpleStatement("select dummy from dummy"));
        Observable<ResultSetFuture> resultSetObservable = Observable.just(resultSetFuture);
        return resultSetObservable.map(resultSetFuture1 -> {
            try {
                return resultSetFuture1.get().one().getString("dummy");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}

