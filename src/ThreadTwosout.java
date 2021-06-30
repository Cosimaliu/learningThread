import java.util.concurrent.atomic.AtomicInteger;

//两个线程分别循环输出100以内的奇数和偶数
public class ThreadTwosout {
    static AtomicInteger cxsNum = new AtomicInteger(0);
    static volatile boolean flag = true;

    public static void main(String[] args) {
        //偶数
        Thread t1 = new Thread(()->{
           for(;100>cxsNum.get();){
               //System.out.println("进入t1====="+cxsNum.get());
                if (!flag&&(cxsNum.get() == 0 || cxsNum.incrementAndGet() % 2 == 0)){
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    System.out.println("t1====="+cxsNum.get());
                        flag=true;
                }
           }
        });
        //奇数
        Thread t2 = new Thread(()->{
            for(;100>cxsNum.get();){
                //System.out.println("进入t2====="+cxsNum.get());
                if (flag&&(cxsNum.incrementAndGet() % 2 != 0)){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("t2====="+cxsNum.get());
                    flag=false;
                }
            }
        });
        System.out.println("初始值："+cxsNum.get());
        t1.start();
        t2.start();
    }
}
