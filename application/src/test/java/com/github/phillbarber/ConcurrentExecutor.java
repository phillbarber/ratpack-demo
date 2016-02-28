package com.github.phillbarber;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ConcurrentExecutor {

    private List<Callable<Response>> tasks = new ArrayList<>();
    private int numberOfCalls;

    public ConcurrentExecutor(Callable<Response> task, int numberOfCalls) {
        IntStream.range(0, numberOfCalls).forEach(i -> tasks.add(task));
        this.numberOfCalls = numberOfCalls;
    }

    public List<Response> executeRequestsInParallel() throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(numberOfCalls);
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
