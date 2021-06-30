package ReentrantLock;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 *Phasre虽然也是栅栏，但不是CyclicBarrier类型锁功能那种==循环栅栏==（CyclicBarrier的升级版，1.7出现）
 * 它是一个一个相互之间独立的栅栏
 *
 * 它不仅可以控制栅栏的数量，还可以控制栅栏上等待线程的数量。
 *
 * 编写一个简单的场景：结婚
 */

public class T08_TestPhaser{
    static Random r =new Random();
    static int min = r.nextInt(1000);

    static volatile int comebj = 0;

    static MarriagePhaser phaser= new MarriagePhaser();

    public static ArrayList lsit = new ArrayList();

    private static Person xn;
    private static Person xl;
    //第二种写法
    //Phaser p = new Phaser(7);

    static void milliSleep(int milli){
        try {
            TimeUnit.MICROSECONDS.sleep(milli);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
            phaser.bulkRegister(7);
            for (int i=0;i<5;i++){
                new Thread(new Person("p"+i)).start();
            }

        new Thread(xl =new Person("新郎")).start();
        new Thread(xn = new Person("新娘")).start();
    }


    static class Person extends T08_TestPhaser implements Runnable {
        String name;

        public Person(String name){this.name=name;}

        public void arrive(){
                if (!"新郎".equals(name)&&!"新娘".equals(name)) {
                    //milliSleep(min);
                    m2();
                    System.out.printf("%s====%s 到达现场!\n",comebj,name);
                    phaser.arriveAndAwaitAdvance();
                }else{
                    while (comebj!=5){
                        milliSleep(min);
                    }
                    milliSleep(min*10);
                    System.out.printf("%s 到达现场!\n", name);
                    phaser.arriveAndAwaitAdvance();
                }
        }

        public void eat(){
            milliSleep(min);
            System.out.printf("%s 吃完！\n",name);
            phaser.arriveAndAwaitAdvance();
        }

        private void leave() {
            if (!"新郎".equals(name)&&!"新娘".equals(name)){
                milliSleep(min);
                m1();
                System.out.printf("%s===%s 离开！\n",comebj,name);
                phaser.arriveAndAwaitAdvance();
            }else{
                while (comebj!=0){
                    milliSleep(min);
                }
                milliSleep(min*10);
                System.out.printf("%s 欢送宾客！\n",name);
                phaser.arriveAndAwaitAdvance();
            }
        }

        private void hug() {
            if ("新郎".equals(name)||"新娘".equals(name)){
                milliSleep(r.nextInt(1000));
                System.out.printf("%s 洞房！\n",name);
                phaser.arriveAndAwaitAdvance();//栅栏前等待
            }else{
                phaser.arriveAndDeregister();//Deregister结束注册:即线程结束了，不参与后续栅栏推倒后发生的业务处理（到达栅栏并结束线程）
            }
        }
        @Override
        public void run() {
                arrive();
                eat();
                leave();
                hug();
        }
    }

    static synchronized void m2(){
        comebj++;
    }
    static synchronized void m1(){
        comebj--;
    }


    static class MarriagePhaser extends Phaser{//每一个阶段栅栏被推倒的时候会执行相应的操作
        @Override
        protected boolean onAdvance(int phase,int registeredParties){//线程满足条件则自动调用 phase：第几个阶段，registeredParties：线程数
            switch (phase){
                case 0:
                    System.out.println("所有人到齐了！"+registeredParties);
                    System.out.println();
                    return false;
                case 1:
                    System.out.println("所有人吃完了！"+registeredParties);
                    System.out.println();
                    return false;
                case 2:
                    System.out.println("宾客离开了！"+registeredParties);
                    System.out.println();
                    return false;
                case 3:
                    System.out.println("婚礼结束！新郎新娘抱抱！"+registeredParties);
                    return true;
                default:
                    return true;
            }
        }
    }
}
