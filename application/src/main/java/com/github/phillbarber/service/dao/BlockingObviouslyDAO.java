package com.github.phillbarber.service.dao;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SimpleStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

public class BlockingObviouslyDAO implements DAO {

    private Session session;

    public BlockingObviouslyDAO(Session session) {
        this.session = session;
    }

    public Observable<String> getValueFromDB() {
        try {
            String result = getValue();
            return Observable.just(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getValue() throws InterruptedException, java.util.concurrent.ExecutionException {
        ResultSet rows = session.executeAsync(new SimpleStatement("select dummy from dummy")).get();
        return rows.one().getString("dummy");
    }
}


