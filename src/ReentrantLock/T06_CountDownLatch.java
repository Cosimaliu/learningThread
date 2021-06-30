package ReentrantLock;

import java.util.concurrent.CountDownLatch;

/**
 *CountDownLatch类型锁中提供了一个线程的倒数计数功能
 * latch.await(相当于栅栏，当多少线程倒数完之后再执行某些特性的业务逻辑)
 */

public class T06_CountDownLatch extends Thread{

    public static void main(String[] args) {
        usingjoin();
        useringCountDownLatch();
    }

    private static void useringCountDownLatch(){
        Thread[] threads = new Thread[100];
        CountDownLatch latch = new CountDownLatch(threads.length);

        for (int i=0;i<threads.length;i++){
            int finalI = i;
            threads[i] = new Thread(()->{
                int result=0;
                for(int j=0;j<1000;j++) result +=j;
                System.out.println(Thread.currentThread().getName()+"=="+ finalI +"=="+"处理完毕。result="+result);
                    latch.countDown();//阈值减一(本身具有原子性,这个减减已经交给对应锁来处理了)
            });
        }
        for(int j=0;j<threads.length;j++){
            threads[j].start();
        }
        try {
            latch.await();//门栓插住,阈值没有到0则不打开进行接下来的业务处理
            System.out.println("CountDownLatch的100线程结束，进行相应业务处理....");
        }catch(InterruptedException e){
            e.printStackTrace();
        }

    }

    private static void usingjoin(){
        Thread[] threads = new Thread[100];
        for (int i=0;i<threads.length;i++){
            int finalI = i;
            threads[i]=new Thread(()->{
                int result = 0;
                for (int j=0;j<1000;j++) result += j;
                System.out.println(Thread.currentThread().getName()+"=="+ finalI +"=="+"处理完毕。result="+result);
            });
        }
        for(int j=0;j<threads.length;j++){
            threads[j].start();
        }
        for(int i=0;i<threads.length;i++){
            try {
                threads[i].join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println("join的100线程结束，进行相应业务处理....");
    }
}
