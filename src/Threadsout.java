public class Threadsout {
    static class MyThread extends Thread{
        @Override
        public void run() {
            System.out.println("Hello myThread");
        }
    }
    static class MyThread2 implements Runnable{
        @Override
        public void run() {
            System.out.println("this is Mythread2");
        }
    }
    public static void main(String[] args) {
        new MyThread().start();
        new Thread(new MyThread2()).start();
        new Thread(()->{
            System.out.println("Hello lambda");
        }).start();
        }
    }
