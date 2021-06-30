package ReentrantLock.CustomerandBoss;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * 写一个固定容量同步容器，拥有put和get方法，以及getcount方法
 * 能够支持2个生产者线程以及10消费者线程的阻塞调用
 *
 * 使用wait以及notifyAll实现
 *
 */

public class Mycontainer1<T> {
    final private LinkedList<T> lists = new LinkedList<>();
    final private int MAX = 10;//最多10个元素
    private int count = 0;

    public synchronized void put(T t){
        while(lists.size() == MAX){
            try{
                this.wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        lists.add(t);
        ++count;
        System.out.println("lists.size="+lists.size());
        this.notifyAll();//通知消费者线程进行消费
    }

    public synchronized T get(){
        T t=null;
        while (lists.size()==0){
            try{
                this.wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        t=lists.removeFirst();
        count--;
        System.out.print("lists.size="+lists.size()+"   ");
        this.notifyAll();//通知生产者进行生产（其实这里就是没有区分生产者线程和消费者线程，都会被叫醒，可能会导致一部分性能被浪费了）
        return t;
    }

    public static void main(String[] args) {
        Mycontainer1<String> c = new Mycontainer1<>();

        //启动消费者线程
        for (int i=0;i<10;i++){
            new Thread(()->{
                for(int j=0;j<5;j++){
                    System.out.println(c.get());
                }
            }).start();
        }
        try{
            TimeUnit.SECONDS.sleep(2);
        }catch (InterruptedException e){
            e.printStackTrace();
        }


        //启动生产者线程
        for(int i=0;i<2;i++){
            new Thread(()->{
                for (int j=0;j<25;j++)c.put(Thread.currentThread().getName()+" "+j);
            }).start();
        }


    }






















}
