package com.example.demo;

import org.junit.Test;

/**
 * @author jiangxinlin
 * @version 2018-06-20
 */
public class VolatileTest {
    private static volatile int n1 = 0;//volatile
    private static volatile int n2 = 0;

    @Test
    public void test1() {
        for (int i = 0; i < 2; i++) {
            Thread thread1 = new Thread(new ThreadA());
            thread1.start();
        }
        Thread thread2 = new Thread(new ThreadB());
        thread2.start();
        try {
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    class ThreadA implements Runnable {

        @Override
        public void run() {
            synchronized (ThreadA.class) {
                for (; n1 < Long.MAX_VALUE; ) {
                    ++n1;
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ++n2;
                }
                System.out.println("stoped");
            }
        }
    }

    class ThreadB implements Runnable {

        int a2 = 0;
        int a1 = 0;

        @Override
        public void run() {
            do {
                a1 = n1;
                a2 = n2;
                System.out.println(Thread.currentThread().getId() + "-----n1:" + a1 + "  n2:" + a2);
            } while (a1 >= a2);
            System.out.println("n2>n1");
        }
    }
}
