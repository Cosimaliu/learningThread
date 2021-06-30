package ReentrantLock;

import java.util.concurrent.TimeUnit;

/**
 * 在同一个类对象中被synchronized锁定的方法是可以重入的。（同一个线程调用，如果是不
 * 同的线程进行争用，则会有这个等待的过程（如下例））
 * 举例：分别调用super类的两个synchronized方法，如果不是可重入的则会发生死锁现象。
 * author:lsc 2021年6月9日07:54:09
 */

public class T01_ReentrantLock1 {
    synchronized void m1(){
        for (int i=0;i<10;i++){
            try{
                TimeUnit.SECONDS.sleep(1);
                System.out.println(i);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    synchronized void m2(){
        System.out.println("m2...");
    }

    synchronized void m3(){
        for (int j=0;j<10;j++){
            try{
                TimeUnit.SECONDS.sleep(1);
                System.out.println(j);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            m2();
        }
    }

    public static void main(String[] args) {

        //两线程争用
//        T01_ReentrantLock1 rl = new T01_ReentrantLock1();
//        new Thread(rl::m1).start();
//        try {
//            TimeUnit.SECONDS.sleep(1);
//        }catch (InterruptedException e){
//            e.printStackTrace();
//        }
//        new Thread(rl::m2).start();

        //单线程可重入
        T01_ReentrantLock1 rl= new T01_ReentrantLock1();
        new Thread(rl::m3).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
