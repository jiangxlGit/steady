package com.example.demo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 消费者线程
 * @author jiangxinlin
 * @version 2018-06-12
 */
public class ThreadConsumer extends Thread {
    private Consumer consumer = null;

    public ThreadConsumer(Lock lock, Condition condition) {
        this.consumer = new Consumer(lock,condition);
    }

    @Override
    public void run() {
        //死循环，不断的消费
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            consumer.getValue();
        }
    }

    class Consumer {
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

    }

}
