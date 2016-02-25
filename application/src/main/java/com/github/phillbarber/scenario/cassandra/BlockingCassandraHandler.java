package com.github.phillbarber.scenario.cassandra;

import com.github.phillbarber.service.BlockingCassandraDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.rx.RxRatpack;
import rx.Observable;

public class BlockingCassandraHandler implements Handler {


    private Logger logger = LoggerFactory.getLogger(BlockingCassandraHandler.class);
    private BlockingCassandraDAO blockingCassandraDAO;


    public BlockingCassandraHandler(BlockingCassandraDAO blockingCassandraDAO) {
        this.blockingCassandraDAO = blockingCassandraDAO;
    }

    @Override
    public void handle(Context context) throws Exception {

        logger.info("Request received");

        Observable<String> contentFromDownstreamSystem = blockingCassandraDAO.getValueFromDB();

        RxRatpack.promiseSingle(contentFromDownstreamSystem).then(
                response -> {
                    context.render("DB Returned: " + response);
                    logger.info("Response sent to client");
                });
    }
}
