package com.kqianwei.JUC.demo1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 要求：循环打印1A2B3C。。。。10
 * @author WZhaokang
 * @date 2020/11/25 18:23
 */
public class CyclePrintDemo {
    public static void main(String[] args) {
        Res1 res1 = new Res1();
        new Thread(()->{
            for (int i = 1; i <= 10; i++){
                res1.number();
            }
        },"A").start();
        new Thread(()->{
            for (int i = 1; i<=10; i++){
                res1.charter();
            }
        },"B").start();
    }
}
class Res1{
    private int number = 1;
    private int charter = 65;

    private boolean flag = true;

    Lock lock  = new ReentrantLock();
    Condition c1 = lock.newCondition();
    Condition c2 = lock.newCondition();

    public void number(){
        lock.lock();
        try {
            while (!flag){
                c1.await();
            }
            System.out.println(Thread.currentThread().getName()+":"+number++);
            flag = false;
            c2.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void charter(){
        lock.lock();
        try {
            while (flag){
                c2.await();
            }
            System.out.println(Thread.currentThread().getName()+":"+ (char)(charter++));
            flag = true;
            c1.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

}
