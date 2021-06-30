package ReentrantLock.CustomerandBoss;


import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 2021年6月16日09:59:35
 *
 * 生产者消费者（多个生产者多个消费者）
 */

//生产者消费者问题
public class CustomerAndPruducer2 {

    public static void main(String[] args) {
        Resource resource = new Resource();//多线程，共享一个变量，主要注意点就是对于这边变量的共享访问控制
        new Thread(new Producer(resource)).start();
        new Thread(new Producer(resource)).start();
        new Thread(new Customer(resource)).start();
        new Thread(new Customer(resource)).start();
    }


    static class Producer implements Runnable{
        private Resource resource;

        public Producer(Resource resource) {
            this.resource = resource;
        }

        @Override
        public void run() {
            resource.set(new Random().nextInt(1000) +"号商品");
        }
    }

    static class Customer implements Runnable{
        private Resource resource;

        public Customer(Resource resource) {
            this.resource = resource;
        }
        @Override
        public void run() {
            resource.out();
        }
    }


    static class Resource{
        private String name;
        private int count = 1;
        private boolean flag = false;
        public synchronized void set(String name){//多个生产线程的控制
            while(flag){//为false时候，进行生产，true时，睡眠等待
                try{
                    wait();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            this.name = name +"---"+count++;
            System.out.println(Thread.currentThread().getName() +"---生产者---"+this.name);
            flag = true;//生产者休眠
            this.notifyAll();
        }

        public synchronized void out(){
            while(!flag){
                try{
                    wait();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName()+"---------------消费者--------------"+this.name);
            flag=false;
            this.notifyAll();
        }
    }




}
