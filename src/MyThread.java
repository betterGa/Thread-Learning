import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
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

/*
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
*/

//版本三：对于Runnable接口的实现采用匿名内部类，
/*
public class MyThread
{
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
System.out.println("Hello");
            }
        }).start();
        }
    }
*/

//使用Runnable接口实现的多线程的程序可以更好的描述出程序共享的概念。
/*
public class MyThread extends Thread
{
    private int ticket=10;
    private String name;
    public MyThread(String name)
    {
        this.name=name;
    }
    public void run()
    { while(this.ticket>0)
    {System.out.println(name+"剩余票数："+this.ticket--);}
    }

    public static void main(String[] args) {
        new MyThread("thread1").start();
        new MyThread("thread2").start();;
        new MyThread("thread3").start();
    }
}
*/
//只是线程的执行顺序可能改变，但是各卖各的票，都是"10,9,8,7,......"

/*
public class MyThread implements Runnable
{
private int ticket=10;
    FutureTask


    @Override
    public void run() {
        while (ticket>0)
        {System.out.println("剩余票数："+ticket--);}
    }

    public static void main(String[] args) {
        MyThread myThread=new MyThread();
        new Thread(myThread).start();
        new Thread(myThread).start();

    }
}
*/

//版本四：Callable接口实现多线程的启动
//有些线程需要返回结果，Callable接口中有方法
//public V call(){}   实现这个接口需要实现这个方法
//FutureTask类实现了RunnableFuture,RunnableFuture接口继承了Future接口，在Future接口中有get()方法。
// FutureTask类有实现这个get()方法，直接使用即可
//FutureTask类的构造方法参数可传入Callable接口类型的

public class MyThread implements Callable<String>
{

        private int ticket=10;
        @Override
        public String call() throws Exception {
            while (ticket >= 0)
            { System.out.println("剩余票数："+ticket--);}
            return "售罄，欢迎下次光临";
        }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyThread myThread=new MyThread();
        FutureTask futureTask=new FutureTask(myThread);
Thread thread=new Thread(futureTask);
Thread thread1=new Thread(futureTask);
thread.start();                                                           
thread1.start();
       System.out.println(futureTask.get());
    }



}