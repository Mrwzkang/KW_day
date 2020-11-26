package com.kqianwei.JUC.demo1;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * CAS 无锁编程
 *  采用CAS实现线程间的通信
 * @author WZhaokang
 * @date 2020/11/26 0:47
 */
//例子：如何实现CAS完成无锁编程，三个线程打印0-1000
/*public class CASDemo3 {
    //首先演示一个错误的编写方式
    private int number = 0;

    public static void main(String[] args) {
        for (int i = 0; i < 3; i++){
            new Thread(()->{
                for (int j = 0; j < 1000; j++){
                    System.out.println(Thread.currentThread().getName()+":"+j);
                }
            },i+"").start();
        }
    }
}*/

//简单的改装使用同步方法进行修改,使用互斥锁/悲观锁
/*public class CASDemo3 {
    private int number = 0;

    public static void main(String[] args) {

        for (int i = 0; i < 3; i++){
            new Thread(()->{
                synchronized (CASDemo3.class) {
                    for (int j = 0; j < 1000; j++) {
                        System.out.println(Thread.currentThread().getName() + ":" + j);
                    }
                }
            },i+"").start();
        }
    }
}*/

//CAS无锁实现
public class CASDemo3 {
    //它的底层通过CAS实现同步计数器
    static AtomicInteger number = new AtomicInteger(0);

    public static void main(String[] args) {

        for (int i = 0; i < 3; i++){
            new Thread(()->{
                while (number.get() < 1000) {
                    System.out.println(Thread.currentThread().getName() + ":" + number.incrementAndGet());
                }
            },i+"").start();
        }
    }
}
