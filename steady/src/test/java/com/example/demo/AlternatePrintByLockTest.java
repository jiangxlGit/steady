package com.example.demo;

import org.junit.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 奇偶交替打印
 * @author jiangxinlin
 * @version 2018-06-22
 */
public class AlternatePrintByLockTest {

    public volatile Integer num = 0;
    public volatile boolean flag = false;

    public Lock lock = new ReentrantLock();
    public Condition condition = lock.newCondition();


    // 奇数打印
    class ThreadA implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    lock.lock();
                    // 当flag为false就打印奇数并通知偶数打印线程且flag赋值为true，否则等待
                    if (!flag) {
                        num++;
                        System.out.println("奇数：" + num);
                        flag = true;
                        condition.signalAll(); // 必须为signalAll，否则会出现假死
                    } else {
                        condition.await();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    class ThreadB implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    lock.lock();
                    // 当flag为true就打印偶数并通知奇数数打印线程且flag赋值为false，否则等待
                    if (flag) {
                        num++;
                        System.out.println("偶数：" + num);
                        flag = false;
                        condition.signalAll();
                    } else {
                        condition.await();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    @Test
    public void test() {

        Thread thread1 = new Thread(new ThreadA(), "A");
        Thread thread2 = new Thread(new ThreadB(), "B");
        Thread thread3 = new Thread(new ThreadB(), "C");
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
