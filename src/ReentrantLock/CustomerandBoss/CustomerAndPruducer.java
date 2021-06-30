package ReentrantLock.CustomerandBoss;


import java.util.LinkedList;

/**
 * 2021年6月16日09:59:35
 *
 * 生产者与消费者问题是多线程同步的一个经典问题。生产者和消费者同时使用一块缓冲区，
 * 生产者生产商品放入缓冲区，消费者从缓冲区中取出商品。我们需要保证的是，当缓冲区满时，
 * 生产者不可生产商品；当缓冲区为空时，消费者不可取出商品。
 */

//生产者消费者问题
public class CustomerAndPruducer {

    //最大容量
    public static final int MAX_SIZE = 5;

    //存储媒介
    public static LinkedList list = new LinkedList();


    public static void main(String[] args) {
        CustomerAndPruducer proAndCon = new CustomerAndPruducer();
        Producer pro = new Producer();
        Customer Con = new Customer();
        for(int i=0;i<10;i++){
            Thread proThread = new Thread(pro);
            proThread.start();
            Thread conThread = new Thread(Con);
            conThread.start();
        }

    }


    static class Producer implements Runnable{

        @Override
        public void run() {
            synchronized (list){
                //仓库容量已经达到最大值
                while(list.size() == MAX_SIZE){
                    System.out.println("仓库已满，生产者"+Thread.currentThread().getName()+"不可生产。");
                    try{
                        list.wait();
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
                list.add(1);
                System.out.println("生产者"+Thread.currentThread().getName()+"生产，仓库已使用容量为："+list.size());
                list.notify();
            }
        }
    }

    static class Customer implements Runnable{

        @Override
        public void run() {
            synchronized (list){
                //仓库容量已经达到最大值
                while(list.size() == 0){
                    System.out.println("仓库为空，消费者"+Thread.currentThread().getName()+"不可消费。");
                    try{
                        list.wait();
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
                list.removeFirst();
                System.out.println("消费者"+Thread.currentThread().getName()+"消费，仓库库存为："+list.size());
                list.notify();
            }
        }
    }



}
