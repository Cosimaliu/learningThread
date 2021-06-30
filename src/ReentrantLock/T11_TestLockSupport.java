package ReentrantLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 *lockSupport支持使用park()方法和unpark()方法进行阻塞和放行
 */

public class T11_TestLockSupport {
    public static void main(String[] args) {
        Thread t =new Thread(()->{
            for(int i=0;i<10;i++){
                System.out.println(i);
                    if(i==5){
                        LockSupport.park();//可以暂停某个线程
                    }
                try {
                    TimeUnit.SECONDS.sleep(1);//睡一秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();

        try {
            TimeUnit.SECONDS.sleep(8);
            System.out.println("After 8 seconds");
            LockSupport.unpark(t);//可以指定暂停的线程继续执行，这个unpark也可以写在park之前，这样的话再线程park的时候就会失效
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
