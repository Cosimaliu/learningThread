package ReentrantLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Reentrantlock()是可以替代synchronized的
 *
 */

public class T02_ReentrantLock2 {
    Lock lock = new ReentrantLock();
    void m1(){
        try{
            lock.lock();//sychronized(this)
            for(int i=0;i<10;i++){
                TimeUnit.SECONDS.sleep(1);
                System.out.println(i);
            }
        }catch(InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();//解锁：lock必须手动解锁，如果没有解锁，那执行中出现异常时，会一直锁定。
        }
    }

    void m2(){
        try{
            lock.lock();
            System.out.println("m2...");
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        T02_ReentrantLock2 rl =new T02_ReentrantLock2();
        new Thread(rl::m1).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        new Thread(rl::m2).start();
    }

}
