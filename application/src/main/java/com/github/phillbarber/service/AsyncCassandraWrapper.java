package com.github.phillbarber.service;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.ResultSetFuture;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.Scheduler;
import rx.internal.producers.SingleDelayedProducer;

import java.util.concurrent.Executor;

public class AsyncCassandraWrapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncCassandraWrapper.class);


    public static Observable<ResultSet> executeAsyncOnSubscribe(Statement statement, Scheduler scheduler, Session session) {
        return Observable.create(subscriber -> {
            try {
                ResultSetFuture resultSetFuture = session.executeAsync(statement);

                SingleDelayedProducer<ResultSet> producer = new SingleDelayedProducer<>(subscriber);
                subscriber.setProducer(producer);

                resultSetFuture.addListener(() -> {
                    try {

                        ResultSet result = resultSetFuture.get();
                        producer.setValue(result);
                    } catch (Exception e) {
                        LOGGER.warn("Error writing to Cassandra", e);
                        subscriber.onError(e);
                    }
                }, createExecutor(scheduler));

            } catch (Exception exception) {
                LOGGER.warn("Error writing to Cassandra", exception);
                subscriber.onError(new RuntimeException(exception));
            }
        });
    }

    private static Executor createExecutor(Scheduler scheduler) {
        return command -> {
            Scheduler.Worker worker = scheduler.createWorker();
            worker.schedule(() -> {
                try {
                    command.run();
                } catch (Exception e) {
                    worker.unsubscribe();
                }
            });
        };
    }

}
