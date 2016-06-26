package com.github.phillbarber.scenario.cassandra;


import com.github.phillbarber.service.dao.RatpackCassandraDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ratpack.exec.Promise;
import ratpack.handling.Context;
import ratpack.handling.Handler;

public class RatpackCassandraHandler implements Handler {


    private Logger logger = LoggerFactory.getLogger(RatpackCassandraHandler.class);
    private final RatpackCassandraDAO ratpackCassandraDAO;

    public RatpackCassandraHandler(RatpackCassandraDAO ratpackCassandraDAO) {
        this.ratpackCassandraDAO = ratpackCassandraDAO;
    }


    @Override
    public void handle(Context ctx) throws Exception {

        logger.info("Request received");

        Promise<String> valueFromDB = ratpackCassandraDAO.getValueFromDB();

        valueFromDB.then(
                response -> {
                    ctx.render("DB Returned: " + response);
                    logger.info("Response sent to client");
                });
    }
}
