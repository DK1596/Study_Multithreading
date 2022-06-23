package com.company.semaphore;

import java.util.concurrent.Semaphore;

public class ConnectionPool {
    private static ConnectionPool connectionPool = new ConnectionPool();
    private int connectionCount;

    private Semaphore semaphore = new Semaphore(5);

    private ConnectionPool() {
    }

    public static ConnectionPool getConnection() {
        return connectionPool;
    }

    public void work() {
        try {
            semaphore.acquire();
            doWork();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }

    public void doWork() {
        synchronized (this) {
            connectionCount++;
            System.out.println(Thread.currentThread().getName() + " increment count of connection: " + connectionCount);
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (this) {
            connectionCount--;
            System.out.println(Thread.currentThread().getName() + " decrement count of connection: " + connectionCount);
        }
    }
}
