package com.company.semaphore;

import java.util.concurrent.Semaphore;

public class SemaphoreTest {
    static class Counter {
        private Integer value = 0;

        public void increment() {
            value++;
        }

        public Integer getValue() {
            return value;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Semaphore semaphore = new Semaphore(1);
        Counter counter = new Counter();

        Runnable runnable = () -> {
            try {
                while (true) {
                    System.out.printf("" +
                                    "Thread %s with counter: %d\n",
                            Thread.currentThread().getName(),
                            counter.getValue());
                    semaphore.acquire();

                    if (counter.getValue() < 5) {
                        counter.increment();
                        semaphore.release();
                    } else {
                        break;
                    }
                }

                semaphore.release();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        for (int i = 0; i < 3; i++) {
            new Thread(runnable).start();
        }

        Thread.sleep(1000);
        System.out.printf("" +
                        "Thread %s with counter: %d\n",
                Thread.currentThread().getName(),
                counter.getValue());
    }
}
