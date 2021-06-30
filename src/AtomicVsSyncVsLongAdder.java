import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

public class AtomicVsSyncVsLongAdder {
    static long count2=0L;
    static AtomicLong count1 =  new AtomicLong( 0L);
    static LongAdder count3 = new LongAdder();


    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[1000];
        for(int i=0;i<threads.length;i++){
                threads[i]=
                        new Thread(()->{
                    for (int k=0;k<100000;k++) count1.incrementAndGet();
                });
        }
        long start = System.currentTimeMillis();//毫秒

        for(Thread t : threads) t.start();

        for(Thread t : threads) t.join();

        long end = System.currentTimeMillis();

        System.out.println("Atomic"+count1.get()+" time "+(end-start));
        //===================================================================

        Object lock = new Object();

        for(int i=0;i<threads.length;i++){
            threads[i]=new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int k=0;k<100000;k++){
                        synchronized (lock){
                            count2++;
                        }
                    }
                }
            });
        }
        start = System.currentTimeMillis();//毫秒

        for(Thread t : threads) t.start();

        for(Thread t : threads) t.join();

        end = System.currentTimeMillis();

        System.out.println("sync"+count1.get()+" time "+(end-start));
        //===================================================================
        for(int i=0;i<threads.length;i++){
            threads[i] = new Thread(()->{
               for(int k=0;k<100000;k++)count3.increment();
            });
        }
        start = System.currentTimeMillis();//毫秒

        for(Thread t : threads) t.start();

        for(Thread t : threads) t.join();

        end = System.currentTimeMillis();

        System.out.println("LongAdder"+count1.get()+" time "+(end-start));
    }



}
