package Yield;

import org.omg.CORBA.PUBLIC_MEMBER;

public class YieldTest implements Runnable {
    YieldTest()
    {
        Thread t=new Thread(this);
        t.start();
    }

    public void run()
    {
        for(int i=0;i<5;i++)
        {
            if(i%5==0)
            {
                System.out.println(Thread.currentThread()+"yield cpu...");
             //   Thread.yield();
            }
            }
            System.out.println(Thread.currentThread()+"is over");
                    //+
                   // "优先级: "+Thread.currentThread().getPriority());
    }

    public static void main(String[] args) {
        new  YieldTest();
        new YieldTest();
        new YieldTest();
    }

}
