package com.kqianwei.JUC.demo1;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用lock锁演示死锁的情况
 *      synchronized作为锁的情况下，只有运行完成或者抛出异常的时候才会释放锁,在高并发的情况下其他需要该资源的线程兄弟都会
 *      被堵塞在这，后边的线程都会积压在这里造成系统的卡顿，一般都会用lock.tryLock来进行试试，如果抢的到我就用如果我抢不到
 *      那我就写一个递归方法在转一圈回来在抢，直到抢到为止
 *
 *
 *
 * @author WZhaokang
 * @date 2020/11/25 19:37
 */
public class DeadLockDemo2 {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();

        new Thread(()->{
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName()+"\t" + "......come in");
                //暂停几秒钟线程
                try{TimeUnit.SECONDS.sleep(1);}catch (InterruptedException e){e.printStackTrace();}
                System.out.println(Thread.currentThread().getName()+"\t" + "......leave out");
            }finally {
                lock.unlock();
            }
        },"A").start();

        new Thread(()->{
            try {
                //等3秒等不到就撤
                if (lock.tryLock(3L,TimeUnit.SECONDS)){
    //            if (lock.tryLock()){
                    //如果被占用，可以直接走不等待，也可以进行等待
                    try {
                        System.out.println(Thread.currentThread().getName()+"\t" + "......come in");

                    }finally {
                        lock.unlock();
                    }
                }else{
                    System.out.println(Thread.currentThread().getName()+"\t" + "抢不到，后续重试");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"B").start();
    }
}
