package com.github.phillbarber;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Created by pergola on 25/02/16.
 */
public class ConcurrentExecutor {

    private List<Callable<Response>> tasks;

    public ConcurrentExecutor(List<Callable<Response>> tasks) {
        this.tasks = tasks;
    }

    public List<Response> executeRequestsInParallel() throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(this.tasks.size());
        List<Response> responses = executorService.invokeAll(tasks).parallelStream().map(responseFuture -> {
            try {
                return responseFuture.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
        executorService.shutdownNow();
        return responses;
    }

}
