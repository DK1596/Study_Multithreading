package com.company.future_callable;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableAndFuture {
    public void runApp() {
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        Future<Integer> future = executorService.submit(() -> {
            Random random = new Random();

            Thread.sleep(2000);

            int randomValue = random.nextInt(10);
            System.out.println("Random value is " + randomValue);
            if (randomValue < 5) {
                throw new RuntimeException("Something bad happen");
            }

            return randomValue;
        });

        executorService.shutdown();

        try {
            System.out.println("Result is " + future.get());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            future.cancel(true);
            System.out.println(Thread.currentThread().getName() + " is interrupted");
        } catch (ExecutionException e) {
            throw launderThrowable(e);
        }

        System.out.println(Thread.currentThread().getName() + " finished");
    }

    protected static RuntimeException launderThrowable(Throwable t) {
        if (t instanceof RuntimeException) {
            return (RuntimeException) t;
        } else if (t instanceof Error) {
            throw (Error) t;
        } else {
            throw new IllegalArgumentException("Не является непроверяемым", t);
        }
    }
}
