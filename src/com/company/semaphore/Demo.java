package com.company.semaphore;

public class Demo {
    private BoundedHashSet boundedHashSet = new BoundedHashSet(5);
    private Integer count = 0;

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> new Demo().produce());
        Thread thread2 = new Thread(() -> new Demo().consume());

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + " waits");

    }

    public void produce() {
        while (true) {
            try {
                boundedHashSet.add(count++);

                System.out.println(Thread.currentThread().getName() + " adds to SET: " + count);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void consume() {
        while (true) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " removes from SET: " + count);
            boundedHashSet.remove(count);
        }
    }
}
