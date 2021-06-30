package ReentrantLock;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读：
 *Semaphore(信号灯)：灯亮执行，不亮（为0）不执行
 *
 * 即有一个线程在执行的时候就将灯灭掉一个（灯的数值减一），这要其它线程看不到灯就不能执行只能等待
 *
 */

public class T10_TestSemaphore {
    public static void main(String[] args) {
        //允许两个线程执行
        Semaphore s = new Semaphore(4);
        //允许一个线程执行
        //Semaphore s = new Semaphore(1);


        for (int i=0;i<10;i++){
            new Thread(()->{
                try {
                    s.acquire();//将亮着的信号灯减一
                    System.out.println(Thread.currentThread().getName()+" running...");
                    Thread.sleep(200);
                    System.out.println(Thread.currentThread().getName()+" continue running...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    s.release();//线程执行完自己的业务，将占用的信号灯点亮
                }
            }).start();
        }

    }

}
