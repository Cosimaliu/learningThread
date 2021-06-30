package ReentrantLock;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁：读的时候（所有线程一起读），写的时候（"排它锁"，只执行写程序）
 * 只有在读的时候才是排它锁的形式，效率上得到很大的提升
 *
 */

public class T09_TestReadWriteLock {
    static Lock lock = new ReentrantLock();
    private static int value;

    static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    static Lock readLock = readWriteLock.readLock();
    static Lock writeLock = readWriteLock.writeLock();

    public static void read(Lock lock){
        try{
            lock.lock();
            Thread.sleep(1000);
            System.out.println("read over! this.value="+value);
        }catch(InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public static  void write(Lock lock,int v){
        try{
            lock.lock();
            Thread.sleep(500);
            value = v;
            System.out.println("write over! new value="+value);
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }


    public static void main(String[] args) {

//        Runnable readR = ()->read(lock);
        Runnable readR = ()->read(readLock);

//        Runnable writeR = ()->write(lock,new Random().nextInt());
        Runnable writeR = ()->write(writeLock,new Random().nextInt());

        for(int i=0;i<18;i++){
            new Thread(readR).start();
            if(i==2||i==3){
                new Thread(writeR).start();
            }
        }
    }
}
