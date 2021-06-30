package ReentrantLock;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 *线程监控:线程2在线程1执行到某个特定节点的时候执行相应的操作
 *
 * 实现方法：两种
 * 一方法：Object的wait()和notify()
 *
 * 二方法：使用CountDownLatch(1)
 *
 */

public class T13_Semaphore {

    public ArrayList list = new ArrayList();

    public void add(Object obj){
        list.add(obj);
    }

    private int size() {
        return list.size();
    }

    private static T13_Semaphore c = new T13_Semaphore();

    public static void main(String[] args) {


        final Object lock = new Object();
        
        new Thread(()->{
            synchronized(lock){
                System.out.println("t2 启动");
                if (c.size()!=5){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                System.out.println("t2 结束");
                lock.notify();//通知线程1继续执行
            }
        },"t2").start();
        try{
            TimeUnit.SECONDS.sleep(1);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+" 启动");
            synchronized (lock){
                for (int i=0;i<10;i++){
                    c.add(new Object());
                    System.out.println(Thread.currentThread().getName()+" add "+c.size());
                    if(c.size()==7){
                        System.out.println("t1去通知t2让它执行");
                        lock.notify();
                        try {
                            lock.wait();//释放锁，让t2得到执行
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            System.out.println(Thread.currentThread().getName()+" 结束");
        },"t1").start();

    }
}
