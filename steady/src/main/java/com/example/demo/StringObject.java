package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者与消费者操作的公共变量
 *
 * @author jiangxinlin
 * @version 2018-06-12
 */
public class StringObject {

    public static List<String> list = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        // 默认是非公平锁，传入true则为公平锁
        Lock lock = new ReentrantLock(true);
        Condition condition = lock.newCondition();
        for (int i = 0; i < 3; i++) {
            ThreadProducer pThread = new ThreadProducer(lock, condition);
            ThreadConsumer cThread = new ThreadConsumer(lock, condition);
            pThread.start();
            cThread.start();
        }

    }

}
