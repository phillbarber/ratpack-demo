package com.github.phillbarber.service.dao;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SimpleStatement;
import com.github.phillbarber.service.AsyncCassandraWrapper;
import ratpack.rx.RxRatpack;
import rx.Observable;

public class NonBlockingColdDAO implements DAO {

    private Session session;

    public NonBlockingColdDAO(Session session) {
        this.session = session;
    }

    public Observable<String> getValueFromDB() {
        SimpleStatement statement = new SimpleStatement("select dummy from dummy");
        Observable<ResultSet> resultSetObservable = AsyncCassandraWrapper.executeAsyncOnSubscribe(statement, RxRatpack.scheduler(), session);
        return resultSetObservable.map(resultSet -> resultSet.one().getString("dummy"));
    }
}
