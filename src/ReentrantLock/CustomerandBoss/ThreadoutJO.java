package ReentrantLock.CustomerandBoss;

import java.util.LinkedList;

/**
 * 两个线程：一个输出基数，一个输出偶数
 */
public class ThreadoutJO {
    //最大容量
    public static final int MAX_SIZE = 0;

    //共享媒介
    public static Object obj = new Object();

    public static void main(String[] args) {


        new Thread(()->{
            synchronized (obj){
                int i=0;
                while(i<=100){
                        if(i%2==0){
                            System.out.println("线程:"+Thread.currentThread().getName()+",输出偶数："+i);
                            obj.notify();
                            try {
                                obj.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    i++;
                    }
            }

        }).start();
        new Thread(()->{
            synchronized (obj){
                int i=0;
                while(i<=100){
                        if(i%2==1){
                            System.out.println("线程:"+Thread.currentThread().getName()+",输出奇数："+i);
                            obj.notify();
                            try {
                                obj.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    i++;
                    }
                }
        }).start();

    }
}
