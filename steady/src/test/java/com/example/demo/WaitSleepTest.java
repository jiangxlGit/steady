package com.example.demo;

import org.junit.Test;

import java.util.concurrent.locks.Lock;

/**
 * @author jiangxinlin
 * @version 2018-06-20
 */
public class WaitSleepTest {

    private StringBuffer sb = new StringBuffer("");

    class WaitThread implements Runnable {

        @Override
        public void run() {
            while (true) {
                Thread thread = Thread.currentThread();
                System.out.println("------wait准备获取对象锁------"+thread.getId() + "-" + thread.getName());
                synchronized (sb) {
                    try {
                        System.out.println("------wait before------"+thread.getId() + "-" + thread.getName());
                        sb.wait();
                        System.out.println("------wait after------"+thread.getId() + "-" + thread.getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    class SleepThread implements Runnable {

        @Override
        public void run() {
            while (true) {
                Thread thread = Thread.currentThread();
                System.out.println("------sleep准备获取对象锁------"+thread.getId() + "-" + thread.getName());
                synchronized (sb) {
                    try {
                        System.out.println("------wait before------"+thread.getId() + "-" + thread.getName());
                        Thread.sleep(3000);
                        System.out.println("------wait after------"+thread.getId() + "-" + thread.getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Test
    public void test() {
        Thread thread1 = new Thread(new WaitThread());
        Thread thread2 = new Thread(new WaitThread());
        thread1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread2.start();
    }
}
