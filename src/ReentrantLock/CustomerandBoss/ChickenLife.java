package ReentrantLock.CustomerandBoss;

import java.util.concurrent.TimeUnit;

/**
 * 线程睡眠的两种方法:
 * 一：Thread.sleep()，二：是Object实例化对象的wait().
 *
 * wait()如果没有参数的话，线程会一直处于沉睡状态，除非主动唤醒，
 *
 * 主动唤醒的两种形式：notify()或者notifyAll();
 *
 * 假设场景：有四个角色，分别是厨房，食客，鸡，以及存放鸡的器皿
 *
 * 厨房：负责将鸡烹饪好。
 * 食客：负责吃鸡
 * 器皿：盛放烹饪好的鸡
 * 鸡：被烹饪、被吃。
 *
 *
 */


public class ChickenLife {

    public static void main(String[] args) {
        //容器
        Container container = new Container();

        //厨房 并将容器 丢给厨房
        Kitchen kitchen = new Kitchen(container);

        //食客 和厨子共享一个容器
        Customer customer1 = new Customer(container);
        Customer customer2 = new Customer(container);

        //启动线程
        kitchen.start();
        customer1.start();
        //customer2.start();
    }

    //食物,鸡
    public static class Chicken{
        //鸡的名字和编号
        private String name;

        public Chicken(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    //食客，负责吃鸡
    static class Customer extends Thread{
        Container container;

        public Customer( Container container) {
            this.container = container;
        }
        @Override
        public void run(){
            try{
                String bh="";
                for (int i = 1;i<=50;i++){
                    bh=container.eat().getName();//吃鸡并返回鸡号
                    if("".equals(bh)){
                        System.out.println("食客"+Thread.currentThread().getName()+"还在等待....");
                    }else{
                        System.out.println("食客"+Thread.currentThread().getName()+"吃了一只鸡："+bh+"号");
                    }
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

    }


    //厨房，负责做食物 鸡
    static class Kitchen extends Thread{
        Container container;

        public Kitchen(Container container) {
            this.container = container;
        }

        @Override
        public void run(){
            for (int i=1;i<=50;i++){
                try{
                    System.out.println("厨房"+Thread.currentThread().getName()+"做了一只鸡:"+i+"号");
                    container.pop(new Chicken(i+""));
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }

    }


    //鸡容器

    static class Container{
        //最多放十只鸡
        private Chicken [] chickens = new Chicken[10];

        //鸡的数量
        volatile int size=0;

        //食客吃掉一只鸡
        public synchronized Chicken eat() throws InterruptedException {
            /**
             * 等待生产者生产，消费者等待
             * 如果鸡没有吃完，则消费者可以吃掉一只鸡
             */
            if(size==0){
                this.wait();
            }
            size--;
            Chicken chicken = chickens[size];
            
            return chicken;
        }

        //厨房做出一只鸡
        public synchronized void pop(Chicken chicken) throws InterruptedException {
            //如果记得数量等于器皿的存放数量
            //通知消费者消费 生产者等待
            if(size == chickens.length-1){
                this.wait();
            }
            //不等于器皿数量则添加一只鸡到指定位置
            chickens[size] = chicken;
            size++;
            //唤醒所有等待线程
            this.notifyAll();
        }


        public synchronized void m2() throws InterruptedException {
            size--;
        }
    }



}
