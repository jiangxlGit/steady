package com.example.demo;

import org.junit.Test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author jiangxinlin
 * @version 2018-06-21
 */
public class AlternateTest {

    public volatile Integer count = 0;
    public volatile boolean flag = false;

    class Num {
        public volatile Integer num = 0;
        public volatile boolean flag = false;
    }

    class ThreadA implements Runnable {

        Num num;

        public ThreadA (Num num) {
            this.num = num;
        }

        AlternateTest alternateTest;
        public ThreadA (AlternateTest alternateTest) {
            this.alternateTest = alternateTest;
        }

        @Override
        public void run() {
            System.out.println("------" + Thread.currentThread().getName() + "------");
            while (true) {
                synchronized (alternateTest) {
                    if (!flag) {
                        count++;
                        flag = true;
                        System.out.println(Thread.currentThread().getName() + ": " + count);
                        alternateTest.notifyAll();
                    } else {
                        try {
                            alternateTest.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    class ThreadB implements Runnable {

        Num num;

        public ThreadB (Num num) {
            this.num = num;
        }

        AlternateTest alternateTest;
        public ThreadB (AlternateTest alternateTest) {
            this.alternateTest = alternateTest;
        }

        @Override
        public void run() {
            System.out.println("------" + Thread.currentThread().getName() + "------");
            while (true) {
                synchronized (alternateTest) {
                    if (flag) {
                        count++;
                        flag = false;
                        System.out.println(Thread.currentThread().getName() + ": " + count);
                        alternateTest.notifyAll();
                    } else {
                        try {
                            alternateTest.wait();
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

        Num num = new Num();
        AlternateTest alternateTest = new AlternateTest();

        Thread thread1 = new Thread(new ThreadA(alternateTest), "A");
        Thread thread2 = new Thread(new ThreadB(alternateTest), "B");
        Thread thread3 = new Thread(new ThreadB(alternateTest), "C");
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
