package ReentrantLock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 *CyclicBarrier类型锁功能类似于（满人，发车）
 *相当于赛马栅栏，人满发车
 */

public class T07_TestCyclicBarrier extends Thread{

    public static int i=0;
    public static volatile int k=0;

    public static CyclicBarrier barrier = new CyclicBarrier(20, new Runnable() {
        @Override
        public void run(){
            System.out.println("满人，发车");
        };
    });

//    第二种写法：
//    public static CyclicBarrier barrier = new CyclicBarrier(20,()-> System.out.println("满人"));

    public static void main(String[] args) {

        for (i=0;i<100;i++){
            new Thread(()->{
                try {
                    m2();
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
    synchronized static void m2(){//可以更清楚的看到栅栏满二十个线程之后再进行相应的业务操作
        System.out.println("当前等待线程数量:"+barrier.getNumberWaiting()+"    k的值："+k);
        k++;
    }
}
