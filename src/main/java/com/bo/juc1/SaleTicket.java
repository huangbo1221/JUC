package com.bo.juc1;

import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * @ClassName SaleTicket
 * @Description 回归下多线程编程，主要是明白编程的思想
 * 线程就是一个单独的资源类，没有任何附属的操作！！！！！！！！
 * 不要再写一个自己的线程类，来实现Runnable接口的方式实现多线程。要会解耦！！！！
 * @Author huangbo1221
 * @Date 2021/9/17 8:47
 * @Version 1.0
 */
public class SaleTicket {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                try {
                    ticket.sale();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                try {
                    ticket.sale();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                try {
                    ticket.sale();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "C").start();

        // 不用sychronized控制，则输出如下：
        /**
         * A卖出了第1张票
         * B卖出了第2张票
         * C卖出了第3张票
         * A卖出了第4张票
         * B卖出了第5张票
         * C卖出了第6张票
         * B卖出了第7张票
         * A卖出了第7张票
         * C卖出了第8张票
         * B卖出了第10张票
         * A卖出了第9张票
         * C卖出了第11张票
         * A卖出了第12张票
         * B卖出了第13张票
         * C卖出了第14张票
         * B卖出了第15张票
         * A卖出了第16张票
         * C卖出了第17张票
         * A卖出了第18张票
         * B卖出了第19张票
         * C卖出了第20张票
         * B卖出了第21张票
         * A卖出了第22张票
         * C卖出了第23张票
         * B卖出了第24张票
         * A卖出了第25张票
         * C卖出了第26张票
         * B卖出了第27张票
         * A卖出了第28张票
         * C卖出了第29张票
         * B卖出了第30张票
         */
        // 可看到卖票重复

        System.out.println("====================================");
        // 加了synchronize以后输出如下：
        /**
         * A卖出了第1张票
         * A卖出了第2张票
         * A卖出了第3张票
         * A卖出了第4张票
         * A卖出了第5张票
         * A卖出了第6张票
         * A卖出了第7张票
         * A卖出了第8张票
         * A卖出了第9张票
         * A卖出了第10张票
         * A卖出了第11张票
         * A卖出了第12张票
         * A卖出了第13张票
         * A卖出了第14张票
         * A卖出了第15张票
         * A卖出了第16张票
         * A卖出了第17张票
         * A卖出了第18张票
         * A卖出了第19张票
         * A卖出了第20张票
         * A卖出了第21张票
         * A卖出了第22张票
         * A卖出了第23张票
         * A卖出了第24张票
         * C卖出了第25张票
         * C卖出了第26张票
         * B卖出了第27张票
         * B卖出了第28张票
         * B卖出了第29张票
         * C卖出了第30张票
         */
    }
}

class Ticket {
    private int tickets = 30;

    public synchronized void sale() throws InterruptedException {
        if (tickets > 0) {
            tickets--;
            System.out.println(Thread.currentThread().getName() + "卖出了第" + (30 - tickets) + "张票");
            Thread.sleep(200);
        }
    }
}
