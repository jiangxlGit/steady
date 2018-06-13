package com.example.demo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 消费者
 *
 * @author jiangxinlin
 * @version 2018-06-12
 */
public class Consumer {
    private Lock lock;
    private Condition condition;

    public Consumer(Lock lock, Condition condition) {
        super();
        this.lock = lock;
        this.condition = condition;
    }

    public void getValue() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getId() + "》》》进入消费者");
            while (StringObject.list != null && StringObject.list.size() == 0) {
                //没值，不进行消费
                condition.await();
            }
            String value = StringObject.list.get(StringObject.list.size() - 1);
            System.out.println(Thread.currentThread().getId() + "---消费的值是：" + value);
            StringObject.list.remove(value);
            condition.signalAll();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // 默认是非公平锁，传入true则为公平锁
        Lock lock = new ReentrantLock(true);
        Condition newCondition = lock.newCondition();
        Producer product = new Producer(lock, newCondition);
        Consumer consumer = new Consumer(lock, newCondition);
        for (int i = 0; i < 3; i++) {
            ThreadProducer pThread = new ThreadProducer(product);
            ThreadConsumer cThread = new ThreadConsumer(consumer);
            pThread.start();
            cThread.start();
        }

    }

}
