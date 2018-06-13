package com.example.demo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者
 *
 * @author jiangxinlin
 * @version 2018-06-12
 */
public class Producer {
    // 锁
    private Lock lock;

    // Condition的作用是对锁进行更精确的控制
    private Condition condition;

    public Producer(Lock lock, Condition condition) {
        this.lock = lock;
        this.condition = condition;
    }

    public void setValue() {
        try {
            /*
                - 如果该锁定没有被另一个线程保持，则获取该锁定并立即返回，将锁定的保持计数设置为 1。
                - 如果当前线程已经保持该锁定，则将保持计数加 1，并且该方法立即返回。
                - 如果该锁定被另一个线程保持，则出于线程调度的目的，禁用当前线程，并且在获得锁定之前，
                该线程将一直处于休眠状态，此时锁定保持计数被设置为 1。
             */
            lock.lock();
            System.out.println(Thread.currentThread().getId() + "》》》进入生产者");

            // list大小超过10，不生产
            while (StringObject.list != null && StringObject.list.size() >= 2) {
                // 当前线程在接到信号或被中断之前一直处于等待状态
                condition.await();
            }

            String value = System.currentTimeMillis() + "" + System.nanoTime();
            System.out.println(Thread.currentThread().getId() + "---生产的值是：" + value);
            StringObject.list.add(value);
            // 唤醒在此Lock对象上等待的所有线程
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 把解锁操作括在finally字句之内是至关重要的，如果受保护的代码抛出异常，
            // 锁可以得到释放，这样可以避免死锁的发生
            lock.unlock();
        }


    }
}
