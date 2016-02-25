package com.github.phillbarber.service.dao;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.ResultSetFuture;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SimpleStatement;
import rx.Observable;

public class BlockingOnSubscribeDAO implements DAO{

    private Session session;

    public BlockingOnSubscribeDAO(Session session) {
        this.session = session;
    }

    public Observable<String> getValueFromDB() {
        ResultSetFuture resultSetFuture = session.executeAsync(new SimpleStatement("select dummy from dummy"));
        Observable<ResultSet> resultSetObservable = Observable.from(resultSetFuture);
        return resultSetObservable.map(resultSet -> resultSet.one().getString("dummy"));
    }
}