package com.example.demo;

/**
 * 消费者线程
 * @author jiangxinlin
 * @version 2018-06-12
 */
public class ThreadConsumer extends Thread {
    private Consumer consumer;

    public ThreadConsumer(Consumer consumer) {
        this.consumer = consumer;
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

}
