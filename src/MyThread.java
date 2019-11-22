import java.util.logging.Logger;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

//正确启动多线程
//版本一：继承Thread类，覆写run()方法。
/*
public class MyThread extends Thread {
    private String title;
    public MyThread(String title) {
        this.title = title; }

    @Override
    public void run()
    {
        for(int i=0;i<10;i++)
        {System.out.println(this.title+",i="+i); }
    }

    public static void main(String[] args) {
        MyThread myThread1=new MyThread("thread1");
        MyThread myThread2=new MyThread("thread2");
        MyThread myThread3=new MyThread("thread3");
        myThread1.start();
        myThread2.start();
        myThread3.start();
        LOGGER.info(""+myThread1.getState());
        LOGGER.info(""+myThread2.getState());
        LOGGER.info(""+myThread3.getState());
    }

}
*/

//版本二：MyThread实现Runnable接口，
// 多线程的启动永远是Thread类的start()方法，
// 而Runnable接口不像Thread类，有start()方法可以继承，
//Runnable接口中只有个run()方法。
//观察Thread类的构造方法，有个Thread(Runnable runnable);

public class MyThread implements Runnable
{private String title;
public MyThread(String title)
    {this.title=title;}

    @Override
    public void run() {
        for(int i=0;i<10;i++)
        {
            System.out.println(this.title+",i="+i);
        }
    }

    public static void main(String[] args) {
        Thread thread1=new Thread(new MyThread("thread1"));
        Thread thread2=new Thread(new MyThread("thread2"));
        Thread thread3=new Thread(new MyThread("thread3"));

        thread1.start();
        thread2.start();
        thread3.start();


    }


}