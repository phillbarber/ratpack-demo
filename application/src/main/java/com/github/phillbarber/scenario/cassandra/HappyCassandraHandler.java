package com.github.phillbarber.scenario.cassandra;

import com.github.phillbarber.service.DummyDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.rx.RxRatpack;
import rx.Observable;

public class HappyCassandraHandler implements Handler {


    private Logger logger = LoggerFactory.getLogger(HappyCassandraHandler.class);
    private DummyDAO dummyDAO;


    public HappyCassandraHandler(DummyDAO dummyDAO) {
        this.dummyDAO = dummyDAO;
    }

    @Override
    public void handle(Context context) throws Exception {

        logger.info("Request received");

        Observable<String> contentFromDownstreamSystem = dummyDAO.getRowFromDB();

        RxRatpack.promiseSingle(contentFromDownstreamSystem).then(
                response -> {
                    context.render("DB Returned: " + response);
                    logger.info("Response sent to client");
                });

    }
}
