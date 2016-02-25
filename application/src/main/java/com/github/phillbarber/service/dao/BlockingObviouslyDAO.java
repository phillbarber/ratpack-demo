package com.github.phillbarber.service.dao;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SimpleStatement;
import rx.Observable;

public class BlockingObviouslyDAO implements DAO {

    private Session session;

    public BlockingObviouslyDAO(Session session) {
        this.session = session;
    }

    public Observable<String> getValueFromDB() {
        try {
            ResultSet rows = session.executeAsync(new SimpleStatement("select dummy from dummy")).get();
            String result = rows.one().getString("dummy");
            return Observable.just(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}


