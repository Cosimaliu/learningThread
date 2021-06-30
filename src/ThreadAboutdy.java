public class ThreadAboutdy {
    public synchronized void m1(){
        System.out.println(Thread.currentThread().getName()+"m1 start...");
        try {
                Thread.sleep(10000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+"m1 end...");
    }

    public void m2(){
        try {
            Thread.sleep(5000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+"m2");
    }

    public static void main(String[] args) {
        ThreadAboutdy t = new ThreadAboutdy();
        new Thread(t::m1,"t1").start();
        new Thread(t::m2,"t2").start();
    }

}
