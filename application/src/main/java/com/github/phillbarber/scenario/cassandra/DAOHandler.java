package com.github.phillbarber.scenario.cassandra;

import com.github.phillbarber.service.dao.DAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.rx.RxRatpack;
import rx.Observable;

public class DAOHandler implements Handler {


    private Logger logger = LoggerFactory.getLogger(DAOHandler.class);
    private DAO dao;


    public DAOHandler(DAO dao) {
        this.dao = dao;
    }

    @Override
    public void handle(Context context) throws Exception {

        logger.info("Request received");

        Observable<String> contentFromDownstreamSystem = dao.getValueFromDB();

        RxRatpack.promiseSingle(contentFromDownstreamSystem).then(
                response -> {
                    context.render("DB Returned: " + response);
                    logger.info("Response sent to client");
                });
    }
}
