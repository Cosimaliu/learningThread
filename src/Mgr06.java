public class Mgr06 {
    /**
     * 线程安全的单例，懒汉式
     */
    private static volatile Mgr06 INSTANCE;//在超高并发的情况下，可能读到版初始化的实例

    public Mgr06() {
    }

    public static Mgr06 getINSTANCE() {
        if(INSTANCE == null){
            synchronized (Mgr06.class){
                if (INSTANCE == null){
                    try{
                        Thread.sleep(100);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    INSTANCE = new Mgr06();
                }
            }
        }
        return INSTANCE;
    }




    public static void main(String[] args) {
        for (int i=0;i<50;i++){
            new Thread(()->{
                System.out.println(Mgr06.getINSTANCE().hashCode());
            }).start();
        }
    }
}
