package ReentrantLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Reentrantlock()是可以替代synchronized的
 *
 */

public class T03_ReentrantLock3 {
    Lock lock = new ReentrantLock();
    void m1(){
        try{
            lock.lock();//sychronized(this)
            for(int i=0;i<3;i++){
                TimeUnit.SECONDS.sleep(1);
                System.out.println(i);
            }
        }catch(InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();//解锁：lock必须手动解锁，如果没有解锁，那执行中出现异常时，会一直锁定。
        }
    }

    /**
     * 使用tryLock进行尝试锁定，不管锁定与否，方法都将继续执行
     * 可以根据tryLock的返回值来判断是否锁定
     * 也可以指定tryLock的时间，由于tryLock(time)抛出异常，所以要注意unLock的
     */
    void m2(){
        boolean locked = false;
        try{
            locked = lock.tryLock(5,TimeUnit.SECONDS);
            System.out.println("m2...."+locked);
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            if(locked) lock.unlock();
        }
    }

    public static void main(String[] args) {
        T03_ReentrantLock3 rl =new T03_ReentrantLock3();
        new Thread(rl::m1).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        new Thread(rl::m2).start();
    }

}
