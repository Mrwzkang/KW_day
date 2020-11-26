package com.kqianwei.JUC.demo1;

import java.util.concurrent.TimeUnit;

/**
 * 手写一个死锁
 * @author WZhaokang
 * @date 2020/11/25 18:32
 */
public class DeadLockDemo {
    public static void main(String[] args) {
        Object o1 = new Object();
        Object o2 = new Object();
        new Thread(()->{
            synchronized (o1){
                System.out.println(Thread.currentThread().getName()+":"+ "获得到A的资源，接下来去请求B的资源");
                //暂停几秒钟线程
                try{TimeUnit.SECONDS.sleep(1);}catch (InterruptedException e){e.printStackTrace();}
                synchronized (o2){
                    System.out.println(Thread.currentThread().getName() + ":" +"获得B的资源");
                }
            }
        },"A").start();
        new Thread(()->{
            synchronized (o2){
                System.out.println(Thread.currentThread().getName()+":"+ "获得到B的资源，接下来去请求A的资源");
                //暂停几秒钟线程
                try{TimeUnit.SECONDS.sleep(1);}catch (InterruptedException e){e.printStackTrace();}
                synchronized (o1){
                    System.out.println(Thread.currentThread().getName() + ":" +"获得A的资源");
                }
            }
        },"B").start();
    }
}
