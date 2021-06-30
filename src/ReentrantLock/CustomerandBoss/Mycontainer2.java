package ReentrantLock.CustomerandBoss;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 写一个固定容量同步容器，拥有put和get方法，以及getcount方法
 * 能够支持2个生产者线程以及10消费者线程的阻塞调用
 * <p>
 * 使用wait以及notifyAll实现
 */

public class Mycontainer2<T> {
    final private LinkedList<T> lists = new LinkedList<>();
    final private int MAX = 10;//最多10个元素
    private int count = 0;


    private Lock lock = new ReentrantLock();
    //Condition的本意就是定义等待队列
    private Condition producer = lock.newCondition();//定义一个生产者阻塞队列
    private Condition consumer = lock.newCondition();//定义一个消费者阻塞队列

    public void put(T t) {
        try {
            lock.lock();
            while (lists.size() == MAX) {
                producer.await();
            }
            lists.add(t);
            ++count;
            System.out.println(Thread.currentThread().getName() + "   lists.size=" + lists.size());
            consumer.signalAll();//通知消费者线程进行消费()
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public T get() {
        T t = null;
        try {
            lock.lock();
            while (lists.size() == 0) {
                consumer.await();
            }
            t = lists.removeFirst();
            count--;
            System.out.print(Thread.currentThread().getName() + "   lists.size=" + lists.size() + "   ");
            producer.signalAll();//通知生产者进行生产
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return t;
    }

    public static void main(String[] args) {
        Mycontainer2<String> c = new Mycontainer2<>();

        //启动消费者线程
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    System.out.println(c.get());
                }
            }).start();
        }
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //启动生产者线程
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int j = 0; j < 25; j++) c.put(Thread.currentThread().getName() + " " + j);
            }).start();
        }


    }


}
