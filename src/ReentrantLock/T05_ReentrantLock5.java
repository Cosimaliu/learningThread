package ReentrantLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock还可以指定为公平锁
 *
 *
 * 如果有指定为true的情况下，输出并不是交替的，
 * 原因是因为另外一个线程还没有进入等待队列
 * （或者是在unlock之后，2线程比1线程进入的更快，所以会出现连续输出的情况，
 * 针对这种情况的解决办法则是：建立线程之间的通信）
 *
 */

public class T05_ReentrantLock5 extends Thread{
    //参数为true表示为公平锁（先来后到，先进先出），则一个新来的线程想要获取锁时，要先检查等待队列中是否已经有线程在等待，
    // 如果有的话就要等待已经在等待的线程全部执行完了，才能进行锁的获取。

    //参数为false表示为不公平锁，则一个新来的线程想要获取锁时，可以直接和已经在等待的线程直接进行竞争，
    // 可能比已经在等待一段时间的线程更早的获取锁。
    private static ReentrantLock lock  = new ReentrantLock(true);

    public void run(){
        for(int i=0;i<100;i++){
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName()+"获得锁");
            }finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        T05_ReentrantLock5 rl = new T05_ReentrantLock5();
        Thread th1 = new Thread(rl);
        Thread th2 = new Thread(rl);
        th1.start();
        th2.start();
    }

}
