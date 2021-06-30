public class ThreadChangLiang {
    volatile String s1 = "volatile";
    volatile String s2 ="synchronized";

    void m1() throws InterruptedException {
        synchronized (s1){
            Thread.sleep(10000);
            System.out.println(s1);
        }
    }
    void m2() throws InterruptedException {
        synchronized (s2){
            //Thread.sleep(1000);
            System.out.println(s2+"=========="+s1);

        }
    }

    public static void main(String[] args) {
        ThreadChangLiang t = new ThreadChangLiang();
        new Thread(()->{
            try {
                t.m1();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(()->{
            try {
                t.s1="volatilechange";
                t.m2();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
