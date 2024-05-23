package ReentrantLock;

/**
 *要求用线程顺序打印A1B2C3....Z26
 */

public class T01_00_Questioon {

    public static Object obj = new Object();

    public static void main(String[] args) {

            new Thread(()->{
                synchronized (obj){
                    char a[] = new char[26];
                    for(int i=0;i<26;i++) {
                        a[i]=(char)('A'+i);
                        System.out.print(a[i]+" ");
                        obj.notify();
                        try {
                            obj.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(a[i]=='Z') {
                            System.out.println("");

                        }
                    }
                }
            },"t1").start();

            new Thread(()->{
                synchronized (obj){
                    for(int i=0;i<26;i++) {
                        System.out.print((i+1)+" ");
                        obj.notify();
                        try {
                            obj.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(i==25) {
                            System.out.println("");
                        }
                    }
                }
            },"t2").start();

        }
}
