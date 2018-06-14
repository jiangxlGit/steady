package com.example.demo;

/**
 * @author jiangxinlin
 * @version 2018-06-12
 */
public class ThreadLocalTest implements Runnable {

    public static final ThreadLocal<Integer> THREAD_LOCAL = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 60;
        }
    };

    @Override
    public void run() {
        while (true) {
            Integer tick = THREAD_LOCAL.get();
            if (tick == 0) {
                break;
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "=========" + tick);
            THREAD_LOCAL.set(--tick);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadLocalTest threadLocalTest = new ThreadLocalTest();
        Thread t1 = new Thread(threadLocalTest);
        Thread t2 = new Thread(threadLocalTest);
        t1.start();
        t2.start();
    }
}
