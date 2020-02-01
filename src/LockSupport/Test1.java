package LockSupport;

//使用LockSupport

import java.util.concurrent.locks.LockSupport;

public class Test1 {
    public static void main(String[] args) {
        Thread thread=new Thread(()->
        {
            LockSupport.park();
            System.out.println(Thread.currentThread().getName()+"被唤醒");
        });
        thread.start();
        try
        {
            Thread.sleep(2000);
        }
        catch (InterruptedException e)
        {e.printStackTrace();}
        LockSupport.unpark(thread);
    }

}
