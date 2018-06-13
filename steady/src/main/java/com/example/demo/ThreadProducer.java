package com.example.demo;

/**
 * @author jiangxinlin
 * @version 2018-06-12
 */
public class ThreadProducer extends Thread {
    private Producer producer;

    public ThreadProducer(Producer producer) {
        this.producer = producer;
    }

    @Override
    public void run() {
        //死循环，不断的生产
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            producer.setValue();
        }
    }

}
