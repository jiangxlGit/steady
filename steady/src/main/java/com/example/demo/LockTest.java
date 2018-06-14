package com.example.demo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * 功能描述: 死锁示例
 *
 * @param:
 * @return:
 * @auther: jiangxinlin
 * @date: 2018/6/14 15:33
 */
public class LockTest {
    public static String obj1 = "obj1";
    public static String obj2 = "obj2";

    private static Lock lock1 = new ReentrantLock();
    private static Lock lock2 = new ReentrantLock();

    public static void main(String[] args) {
        LockA la = new LockA();
        LockB lb = new LockB();
        new Thread(la).start();
        new Thread(lb).start();
    }

    static class LockA implements Runnable {
        public void run() {
            try {
                System.out.println(System.nanoTime() + " LockA 开始执行");
                while (true) {
                    lock1.tryLock(3, TimeUnit.SECONDS);
//                    synchronized (LockTest.obj1) {
                        System.out.println(System.nanoTime() + " LockA 锁住 obj1");
                        Thread.sleep(3000); // 此处等待是给B能锁住机会
                            lock2.tryLock(3, TimeUnit.SECONDS);
//                        synchronized (LockTest.obj2) {
                            System.out.println(System.nanoTime() + " LockA 锁住 obj2");
                            Thread.sleep(3 * 1000); // 为测试，占用了就不放
//                        }
//                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class LockB implements Runnable {
        public void run() {
            try {
                System.out.println(System.nanoTime() + " LockB 开始执行");
                while (true) {
                        lock2.tryLock(3, TimeUnit.SECONDS);
//                    synchronized (LockTest.obj2) {
                        System.out.println(System.nanoTime() + " LockB 锁住 obj2");
                        Thread.sleep(3000); // 此处等待是给A能锁住机会
                            lock1.tryLock(3, TimeUnit.SECONDS);
//                        synchronized (LockTest.obj1) {
                            System.out.println(System.nanoTime() + " LockB 锁住 obj1");
                            Thread.sleep(3 * 1000); // 为测试，占用了就不放
//                        }
//                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}