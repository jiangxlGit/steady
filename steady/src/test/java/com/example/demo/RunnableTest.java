package com.example.demo;

/**
 * @author jiangxinlin
 * @version 2018-06-12
 */
public class RunnableTest implements Runnable {

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
        RunnableTest runnableTest = new RunnableTest();
        Thread t1 = new Thread(runnableTest);
        Thread t2 = new Thread(runnableTest);
        t1.start();
        t2.start();
    }
}
