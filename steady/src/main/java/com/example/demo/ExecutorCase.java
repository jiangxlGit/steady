package com.example.demo;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author jiangxinlin
 * @version 2018-07-10
 */
public class ExecutorCase {

    private static Executor executor = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            executor.execute(new task());
        }
    }

    static class task implements Runnable {

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
        }
    }

}
