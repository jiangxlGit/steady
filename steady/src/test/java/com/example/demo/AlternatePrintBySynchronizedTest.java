package com.example.demo;

import org.junit.Test;

/**
 * @author jiangxinlin
 * @version 2018-06-21
 */
public class AlternatePrintBySynchronizedTest {

    public volatile Integer count = 0;
    public volatile boolean flag = false;

    class ThreadA implements Runnable {

        AlternatePrintBySynchronizedTest test;
        public ThreadA (AlternatePrintBySynchronizedTest alternateTest) {
            this.test = alternateTest;
        }

        @Override
        public void run() {
            System.out.println("------" + Thread.currentThread().getName() + "------");
            while (true) {
                synchronized (test) {
                    if (!flag) {
                        count++;
                        flag = true;
                        System.out.println(Thread.currentThread().getName() + ": " + count);
                        test.notifyAll();
                    } else {
                        try {
                            test.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    class ThreadB implements Runnable {

        AlternatePrintBySynchronizedTest test;
        public ThreadB (AlternatePrintBySynchronizedTest alternateTest) {
            this.test = alternateTest;
        }

        @Override
        public void run() {
            System.out.println("------" + Thread.currentThread().getName() + "------");
            while (true) {
                synchronized (test) {
                    if (flag) {
                        count++;
                        flag = false;
                        System.out.println(Thread.currentThread().getName() + ": " + count);
                        test.notifyAll();
                    } else {
                        try {
                            test.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }


    @Test
    public void test() {

        AlternatePrintBySynchronizedTest test = new AlternatePrintBySynchronizedTest();

        Thread thread1 = new Thread(new ThreadA(test), "A");
        Thread thread2 = new Thread(new ThreadB(test), "B");
        Thread thread3 = new Thread(new ThreadB(test), "C");
        thread1.start();
        thread2.start();
        thread3.start();
        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
