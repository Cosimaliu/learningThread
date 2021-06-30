package ReentrantLock.CustomerandBoss;

import javax.swing.*;
import java.util.Scanner;

/**
 *.1.其中包子类成为了老板与顾客的锁对象，通过调用bun.wait()、bun.notify()方法达到让两个线程等待与唤醒的目的。所以老板与顾客类需要Buns成员变量用于接收锁对象。
 * 2.包子类传递了老板与顾客之间需要传递的消息。
 * 3.因为访问了公共资源Buns，为保证线程安全，在线程执行期间，需要用同步代码块synchronized{}把线程语句包裹起来。
 *
 */

public class BunLife {
    public static void main(String[] args) {
        Buns bun =new Buns();
        new Thread(new Boss(bun)).start();
        new Thread(new Customer(bun)).start();

    }

    //包子实例
    public static class Buns{
        private String surface;//包子皮
        private String inner;//包子馅
        private int count;//包子个数
        private boolean isEmpty;//是否做好
        private boolean haveCustomer;//是否有顾客

        public boolean isHaveCustomer() {
            return haveCustomer;
        }

        public void setHaveCustomer(boolean haveCustomer) {
            this.haveCustomer = haveCustomer;
        }

        public String getSurface() {
            return surface;
        }

        public void setSurface(String surface) {
            this.surface = surface;
        }

        public String getInner() {
            return inner;
        }

        public void setInner(String inner) {
            this.inner = inner;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public boolean isEmpty() {
            return isEmpty;
        }

        public void setEmpty(boolean empty) {
            isEmpty = empty;
        }

        @Override
        public String toString() {
            return "Buns{" +
                    "surface='" + surface + '\'' +
                    ", inner='" + inner + '\'' +
                    ", count=" + count +
                    ", isEmpty=" + isEmpty +
                    ", haveCustomer=" + haveCustomer +
                    '}';
        }
    }


    //顾客
    public static class Customer implements Runnable{
        private Buns bun;//用于接受锁对象

        public Customer(Buns bun){
            this.bun=bun;
        }

        @Override
        public void run() {
            synchronized (bun){
                //顾客点包子
                System.out.println("选包子皮:");
                Scanner sc = new Scanner(System.in);
                bun.setSurface(sc.next());
                System.out.println("选包子馅:");
                bun.setInner(sc.next());
                System.out.println("数量:");
                bun.setCount(sc.nextInt());
                bun.setHaveCustomer(true);
                bun.setEmpty(true);
                //点好了，唤醒老板
                bun.notify();
                //开始等待
                while(bun.isEmpty){
                    try{
                        System.out.println("顾客开始玩手机.....");
                        bun.wait();
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }

                //被唤醒后吃包子
                for(int i=1;i<=bun.getCount();i++){
                    try{
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    System.out.println("吃了第"+i+"个"+bun.getInner()+"馅的包子，好吃");
                }
                System.out.println("吃完了，顾客离开");
                bun.setHaveCustomer(false);
                bun.notify();
            }
        }
    }


    //老板
    public static class Boss implements Runnable{
        private Buns bun;//用于接受锁对象

        public Boss(Buns bun){
            this.bun=bun;
        }

        @Override
        public void run() {
            synchronized (bun){
                //计时等待，让顾客线程先执行
                try{
                    Thread.sleep(50);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                while(!bun.isHaveCustomer()){
                    try{
                        System.out.println("没有顾客下单，老板玩手机.....");
                        bun.wait();
                        System.out.println("有顾客下单，老板做包子.....");
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
                //执行做包子
                for (int i=1;i<=bun.getCount();i++){
                    try{
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    System.out.println("使用"+bun.getSurface()+bun.getInner()+"馅做了第"+i+"个包子");
                }
                bun.setEmpty(false);
                //做好唤醒顾客
                bun.notify();
                //顾客走了
                    try{
                        bun.wait();
                        System.out.println("顾客走了，老板开始玩手机.....");
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
            }
        }
    }
}
