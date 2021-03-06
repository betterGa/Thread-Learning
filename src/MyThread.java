import sun.security.krb5.internal.TGSRep;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
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

//Callable接口的使用
//有些线程需要返回结果，Callable接口中有方法
//public V call(){}   实现这个接口需要实现这个方法
//FutureTask类实现了RunnableFuture,RunnableFuture接口继承了Future接口，在Future接口中有get()方法。
// FutureTask类有实现这个get()方法，直接使用即可
//FutureTask类的构造方法参数可传入Callable接口类型的
/*
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
*/

/*
public class MyThread implements Runnable
{
    @Override
    public void run() {
        for(int i=0;i<10;i++)
        {System.out.println("当前线程"+Thread.currentThread().getName()+",  i="+i);
        }
    }

    public static void main(String[] args) {
        MyThread myThread=new MyThread();
        new Thread(myThread).start();
        new Thread(myThread).start();
        new Thread(myThread,"xiaojia").start();
    }
}
*/

/*
public class MyThread implements Runnable
{


    @Override
    public void run() {
        System.out.println("当前线程"+Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        MyThread myThread=new MyThread();
        myThread.run();//通过对象调用run方法。
        new Thread(myThread).start();

    }
}
*/

//处理休眠操作  sleep()

//线程休眠：让线程暂缓执行一下，等到了预计时间继续执行。
//线程休眠会交出CPU,让CPU去执行其他任务   (不会被分配CPU执行时间)
//不过无须等待其他线程显式唤醒（notify,notifyAll） 一定时间后会自动被系统唤醒
//但是sleep方法不会释放锁🔒。
//如果当前线程持有某个对象的锁🔒，它sleep了，其他线程仍无法访问这个对象。


/*
public class MyThread implements Runnable
{
public void ah()
{System.out.println("ah");}

    public static void main(String[] args) {
        MyThread myThread=new MyThread();
        new Thread(myThread).start();
        new Thread(myThread).start();
        new Thread(myThread).start();

        System.out.println("我就是想看看是不是主线程先执行完才去的子线程");
        System.out.println("俺也一样");
        System.out.println("+1");
        new MyThread().ah();


    }

    @Override
    public void run() {
        for(int i=0;i<5;i++)
        {try
        {System.out.println("当前线程："+Thread.currentThread().getName()+",i="+i);
            Thread.sleep(5000);}
        catch (InterruptedException e)
        {
            e.printStackTrace();

        }
        System.out.println("我是sleep以后的那个线程："+Thread.currentThread().getName()+" ,i="+i+Thread.currentThread().getState());
        }
    }
}



//运行结果：即使调大sleep时间，也总是三个三个输出。
//比如Thread-0先运行，调run()方法，先要休眠1s
//这时线程交出CPU,比如CPU让Thread-2运行，
//1s后，无需notify方法唤醒，系统会自动唤醒Thread-0，
//这时Thread-0恢复执行，输出"......"
//Thread的运行过程同理
//虽然结果总是三个三个输出，像是同时休眠，只是因为当前线程调用run()时，就立刻进入休眠sleep(),把时间片给了CPU
// 运行其他线程
// 而其他线程一调用run(),也是交出CPU，立刻休眠
//感觉像是同时休眠
// 但其实是并发执行的。

//观察yield()方法：线程让步


/*
public class MyThread implements Runnable
{
    @Override
    public void run() {
        for(int i=0;i<100;i++)
        {
            //线程让步，会让当前线程交出CPU权限，让CPU去执行其他线程，同样不释放锁🔒。
            //只能让具有相同优先级的线程具有获取CPU执行时间的机会。
            //System.out.println();
            System.out.println("yield之前"+Thread.currentThread().getName()+", i="+i);
            Thread.yield();
            //System.out.println();
        System.out.println("yield之后"+Thread.currentThread().getName()+", i="+i);
        }
    }

    public static void main(String[] args) {
        MyThread myThread=new MyThread();
        new Thread(myThread).start();
        new Thread(myThread).start();
        new Thread(myThread).start();
    }

}

*/
//join方法的使用
//join(millis)方法：等待线程死亡。
//在A线程中调用B.join()表示A线程会先暂停运行，等待B线程运行完毕以后，才接着运行。
//join()会调用join()方法表示永远等待，直到B死亡。
//join(millis)表示线程A等待线程B运行millis毫秒，millis毫秒后，A、B并发执行。

/*
public class MyThread implements Runnable {


    @Override
    public void run() {
        try {
            System.out.println("主线" +
                    "" +
                    "程睡眠前的时间");
            Test.printTime();
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName()+Thread.currentThread().getState());
            System.out.println("睡眠结束的时间");
            Test.printTime();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }}
    public static void main(String[] args) throws InterruptedException {
        MyThread myThread=new MyThread();
        Thread thread=new Thread(myThread,"子线程A");
        thread.start();
        System.out.println("主线程里的执行"+Thread.currentThread().getName());
        thread.join();
        System.out.println("代码结束"); }}
    class Test
    {
        public static void printTime()
        {
            Date date=new Date();
            DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm;ss");
            String time=dateFormat.format(date);
            System.out.println(time);
        }
    }
*/

//线程停止
//方法一：设置标志位使线程退出
/*
public class MyThread implements Runnable
{
   private boolean flag=true;
    @Override
    public void run() {
        int i=1;
        while (flag)
        {
            try
            {Thread.sleep(1000);
            System.out.println("第"+i+"次执行，线程名称为:"+Thread.currentThread().getName());
            i++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }}

public void setFlag(boolean flag)
        {this.flag=flag;}

    public static void main(String[] args) throws InterruptedException {
        MyThread myThread=new MyThread();
        Thread thread1=new Thread(myThread,"子线程A");
        thread1.start();
        Thread.sleep(2000);
        myThread.setFlag(false);
        System.out.println("代码结束");
    }
    }
*/

//方法二：使用stop方法使线程退出
//不安全，已被弃用
/*
public class MyThread implements Runnable
{
    private boolean flag=true;
    @Override
    public void run() {
        int i=1;
        while (flag)
        {
            try
            {Thread.sleep(1000);
                System.out.println("第"+i+"次执行，线程名称为:"+Thread.currentThread().getName());
                i++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }}

    public void setFlag(boolean flag)
    {this.flag=flag;}

    public static void main(String[] args) throws InterruptedException {
        MyThread myThread=new MyThread();
        Thread thread1=new Thread(myThread,"子线程A");
        thread1.start();
        Thread.sleep(3000);
        thread1.stop();
        System.out.println("代码结束");
    }
}
*/
//方法三：使用Thread类的interrupted方法可以中断线程
/*
public class MyThread implements Runnable
{
    private boolean flag=true;
    @Override
    public void run() {
    int i=1;
    while (flag)
    { try
        { Thread.sleep(1000);
            //判断是否被中断
            boolean bool=Thread.currentThread().isInterrupted();
            if(bool)
            {System.out.println("非阻塞情况下执行该操作，线程状态"+bool);
            break;}
            System.out.println("第"+i+"次执行，线程名称为"+Thread.currentThread().getName());
            i++;
        } catch (InterruptedException e) {
           System.out.println("退出阻塞状态了");
                                  boolean bool=Thread.currentThread().isInterrupted();
           System.out.println(bool);
           return;
        }
    }
    }

    public static void main(String[] args) throws InterruptedException {
        MyThread myThread=new MyThread();
        Thread thread1=new Thread(myThread,"子线程A");
        thread1.start();
        Thread.sleep(3000);
        thread1.interrupt();
        System.out.println("代码结束");
    }
}
*/

//设置优先级
/*public class MyThread implements Runnable
{ @Override
    public void run() {
    for(int i=0;i<5;i++)
    {
        System.out.println("当前线程"+Thread.currentThread().getName()+", i="+i);
    }}
    public static void main(String[] args) {
        System.out.println("当前线程是"+Thread.currentThread().getName()+"优先级为"+Thread.currentThread().getPriority());
        MyThread myThread=new MyThread();
        Thread t1=new Thread(myThread,"1");
        Thread t2=new Thread(myThread,"2");
        Thread t3=new Thread(myThread,"3");
        //1
        t1.setPriority(Thread.MIN_PRIORITY);
        //1
        t2.setPriority(Thread.MIN_PRIORITY);
        //10
        t3.setPriority(Thread.MIN_PRIORITY);
        t1.start();
        t2.start();
        t3.start();
    }}
    */

/*
//观察线程继承性
//“默认情况下，一个线程继承它的父线程的优先级”
class A implements Runnable {
    public void run() {
        System.out.println("A的优先级为: " + Thread.currentThread().getPriority());
        Thread thread = new Thread(new B());
        thread.start();
    }
}
    class B implements Runnable
    {
        //B线程是在A中start的，因此B线程的优先级默认继承了A线程的优先级
        @Override
        public void run() {
            System.out.println("B的优先级为"+Thread.currentThread().getPriority()); }
    }

    public class MyThread
    {
        public static void main(String[] args)
        { Thread thread=new Thread(new A());
            //10
            thread.setPriority(Thread.MIN_PRIORITY);
            thread.start();
        }
    }
*/


/*
//观察守护线程

class A implements Runnable {
    private int i;

    @Override
    public void run() {
        try {
            while (true) {
                i++;
                System.out.println("线程名称" + Thread.currentThread().getName() + ", i=" + i +
                        ", 是否守护线程" + Thread.currentThread().isDaemon());
                Thread.sleep(1000);
            }

        } catch (InterruptedException e) {
            System.out.println("线程名称" + Thread.currentThread().getName() + "中断线程了");
        }
    }
}
 public class MyThread
 {
     public static void main(String[] args) throws InterruptedException {
         Thread thread1=new Thread(new A(),"子线程A");
         //设置          线程A为守护线程,
         //必须在start方法之前
         thread1.setDaemon(true);
         thread1.start();
         Thread thread2=new Thread(new A(),"子线程B");
         thread2.start();
         Thread.sleep(3000);
         //中断非守护线程
         thread2.interrupt();

         //中断守护线程,已验证。
         //thread1.interrupt();
         Thread.sleep(10000);
         //System.out.println("主线程优先级为"+Thread.currentThread().getName()+" "+
           //      Thread.currentThread().getPriority());
         System.out.println("代码结束");
     }
 }
 */


//观察多个线程同时卖票
/*
public class MyThread implements Runnable
{
    //一共有10张票
    private int ticket=10;
    @Override
    public void run() {
        //还有票
 while (this.ticket>0)
 {
     try
     {
         Thread.sleep(5000);
     } catch (InterruptedException e) {
         e.printStackTrace();
     }
     System.out.println(Thread.currentThread().getName()+"还有"+this.ticket--+"张票");
 }
    }

    public static void main(String[] args) {
        MyThread myThread=new MyThread();
        new Thread(myThread,"黄牛A").start();
        new Thread(myThread,"黄牛B").start();
        new Thread(myThread,"黄牛C").start();
    }
}
*/

//同步处理
//synchronized处理同步问题
//synchronized有两种模式：同步代码块、同步方法

//（1）使用同步代码块时，必须设定当前要锁定的对象，一般是this
/*
public class MyThread implements Runnable {
    //一共10张票
    private int ticket = 10;

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            //在同一时刻，只允许一个线程进入代码块处理
            synchronized (this) {
                //还有票
                if (this.ticket > 0) {
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + ",还有" + this.ticket-- + "张票");
                }
            }
        }
    }

    public static void main(String[] args) {
MyThread myThread=new MyThread();
Thread thread1=new Thread(myThread,"线程A");
Thread thread2=new Thread(myThread,"线程B");
Thread thread3=new Thread(myThread,"线程C");

thread1.setPriority(Thread.MIN_PRIORITY);
thread3.setPriority(Thread.MAX_PRIORITY);
thread2.setPriority(Thread.MAX_PRIORITY);
thread1.start();
thread2.start();
thread3.start();
    }
}
*/

/*
//同步方法
public class MyThread implements Runnable
{

private int ticket=20;
public void print()
{System.out.println(this.ticket);}
    @Override
    public void run() {
        for(int i=0;i<20;i++)
        {
            this.sale();
            this.print();
        }}

        public synchronized void sale()
        {
            //还有票
            if(this.ticket>0)
            {
                try{
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
System.out.println(Thread.currentThread().getName()+",还有"+ticket--+"张票");
            }
        }

    public static void main(String[] args) {
        MyThread myThread=new MyThread();
        Thread thread1=new Thread(myThread,"黄牛A");
        Thread thread2=new Thread(myThread,"黄牛B");
        Thread thread3=new Thread(myThread,"黄牛C");
        thread1.setPriority(Thread.MIN_PRIORITY);
        thread2.setPriority(Thread.MAX_PRIORITY);
        thread3.setPriority(Thread.MAX_PRIORITY);
        thread1.start();
        thread2.start();
        thread3.start();
        myThread.print();
    }
    }
*/

/**

//观察synchronized锁多对象
class Sync
{public synchronized void test()
{
    System.out.println("test方法开始，当前线程为" + Thread.currentThread().getName());

    try {
        Thread.sleep(1000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    System.out.println("test方法结束，当前线程为" + Thread.currentThread().getName());
}}

public class MyThread extends Thread
{
    public void run()
{
    Sync sync=new Sync();
    sync.test();
}
    public static void main(String[] args) {
        for(int i=0;i<3;i++)
        {
            Thread thread=new MyThread();
            thread.start();
        }
    }
}
*/

//用synchronized锁住一个对象后，别的线程如果也想拿到这个对象的锁，就必须等待这个线程执行完成，释放锁，
//才能再次给对象加锁，才能达到线程同步的目的。
//即使是两个不同的代码段，都要锁同一个对象。
//那么这两个代码段也不能在多线程环境下同时运行。
//（"JVM为每个对象和类都关联了锁🔒，代表任何时候只允许一个线程拥有的特权"）

//现在想锁住一段代码
//有两种思路
//第一种:锁同一个对象
/**
 class Sync
{
    public void test() {
        synchronized (this) {
            System.out.println("test方法开始，当前线程为" + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("test方法结束，当前线程为" + Thread.currentThread().getName());
        }
    }
    }
    public class MyThread extends Thread {
        private Sync sync;

        public MyThread(Sync sync) {
            this.sync = sync;
        }

        @Override
        public void run() {
            this.sync.test();
        }


        public static void main(String[] args) {
            Sync sync = new Sync();
            for (int i = 0; i < 3; i++) {
                Thread thread = new MyThread(sync);
                thread.start();
            }
        }
    }
//应该是因为 sleep时不释放锁，别的线程不能访问该方法，sleep完了以后，也没别的线程正在进行
//那就继续是这个线程咯，所以先"开始"，紧接着就是该线程的结束
*/

/**
//第二种：全局锁。锁这个类对应的Class对象
class Sync
{
    public void test()
    {
        synchronized (Sync.class)
        {
            System.out.println("test方法开始，当前线程为"+Thread.currentThread().getName());
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {e.printStackTrace();}
            System.out.println("test方法结束，当前线程为"+Thread.currentThread().getName());
        }
    }
}
public class MyThread extends Thread
{
    @Override
    public void run()
    {
        Sync sync=new Sync();
        sync.test();
    }

    public static void main(String[] args) {
        for(int i=0;i<3;i++)
        {Thread thread=new MyThread();
        thread.start();}
    }
}
*/

/*
//观察对象锁机制
public class MyThread
{
    private static Object object=new Object();
    public static void main(String[] args) {
        synchronized (object)
        {System.out.println("hello world");}
    }
}
*/

//用javap反编译得到的结果：
/**
"C:\Program Files\Java\jdk1.8.0_131\bin\javap.exe" -c MyThread
        Compiled from "MyThread.java"





 public class MyThread {
 public MyThread();
 Code:
 0: aload_0
 1: invokespecial #1                  // Method java/lang/Object."<init>":()V
 4: return





 public static void main(java.lang.String[]);
    Code:
            0: getstatic     #2                  // Field object:Ljava/lang/Object;
            3: dup
       4: astore_1
       5: monitorenter
       6: getstatic     #3                  // Field java/lang/System.out:Ljava/io/PrintStream;
            9: ldc           #4                  // String hello world
            11: invokevirtual #5                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
            14: aload_1
      15: monitorexit
      16: goto          24
              19: astore_2
      20: aload_1
      21: monitorexit
      22: aload_2
      23: athrow
      24: return
    Exception table:
    from    to  target type
           6    16    19   any
          19    22    19   any

    static {};
    Code:
            0: new           #6                  // class java/lang/Object
            3: dup
       4: invokespecial #1                  // Method java/lang/Object."<init>":()V
            7: putstatic     #2                  // Field object:Ljava/lang/Object;
            10: return
}

    Process finished with exit code 0
*/

//观察Synronized修饰的同步方法

    /*
public class MyThread
{
    public synchronized void foo()
    {System.out.println("hello world");}

    public static void main(String[] args) {
        new MyThread().foo();
    }
}


    //使用javap反编译得到：
/**
 "C:\Program Files\Java\jdk1.8.0_131\bin\javap.exe" -c MyThread
 Compiled from "MyThread.java"
 public class MyThread {
 public MyThread();
 Code:
 0: aload_0
 1: invokespecial #1                  // Method java/lang/Object."<init>":()V
 4: return



 public synchronized void foo();
 Code:
 0: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
 3: ldc           #3                  // String hello world
 5: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
 8: return

 public static void main(java.lang.String[]);
 Code:
 0: new           #5                  // class MyThread
 3: dup
 4: invokespecial #6                  // Method "<init>":()V
 7: invokevirtual #7                  // Method foo:()V
 10: return
 }

 Process finished with exit code 0




 */

//JDK1.5提供的Lock锁🔒
    /*
public class MyThread implements Runnable
{
    private int ticket=500;
    private Lock ticketLock=new ReentrantLock();
    @Override
    public void run()
    {
        for(int i=0;i<500;i++)
        {
            ticketLock.lock();
            try {
                if (ticket > 0) {
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        System.out.println(Thread.currentThread().getName() + ",还有" + this.ticket-- + "张票");
                    }}} finally {
                        ticketLock.unlock();
                    }
                }
            }
    public static void main(String[] args) {
        MyThread mt=new MyThread();
        Thread t1=new Thread(mt,"黄牛1");
        Thread t2=new Thread(mt,"黄牛2");
        Thread t3=new Thread(mt,"黄牛3");
        t1.setPriority(Thread.MIN_PRIORITY);
        t2.setPriority(Thread.MAX_PRIORITY);
        t3.setPriority(Thread.MAX_PRIORITY);
    t1.start();
    t2.start();
    t3.start();
    }
}
*/

    //《Java并发编程之美》
//第1章 并发编程线程基础
//1.2 线程创建与运行
//方法一 继承Thread类
    /*
public class MyThread
{
    public static class ThreadTest extends Thread
    {
        @Override
        public void run()
        {
            System.out.println(Thread.currentThread()+"I am a child thread.");
        }

        public static void main(String[] args) {
            //创建线程实例，NEW 尚未启动。
            ThreadTest threadTest=new ThreadTest();
            //调用start方法才启动了线程。
            // 调用start方法并不是就立马开始执行了，
            //只是线程进入就绪状态，拿到了除了CPU以外的其他资源
            //等待获取CPU资源后才真正处于运行状态。
            threadTest.start();
        }
    }
}
*/
    //方法二：实现Runnable接口
/*
public class MyThread implements Runnable
{public int i;
    public void setI(int i) {
        this.i = i;
    }

    @Override
                public void run() {
        System.out.println(i);
        //System.out.println("this:"+this+"name"+Thread.currentThread().getName()+"I am a child thread.");}
    }
    public static void main(String[] args) {
        MyThread myThread=new MyThread();
        myThread.setI(666);
        new Thread(myThread).start();
//new Thread(myThread).start();
    }
    }
*/

    //继承Thread类的好处：获取当前线程用this就可以，不需要Thread.currentThread
//区分一下this 和 currentThread
    //这篇博客不错：
    //https://blog.csdn.net/single_wolf_wolf/article/details/83242430
    /*
 class CountOperate extends Thread {
    public CountOperate(){
System.out.println("CountOperate-----begin");
System.out.println("Thread.currentThread().getName() = " + Thread.currentThread().getName());
System.out.println("Thread.currentThread().isAlive() = " + Thread.currentThread().isAlive());
System.out.println("this.getName() = " + this.getName());
System.out.println("this.isAlive() = " + this.isAlive());
System.out.println("CountOperate-----end"); }
    @Override
    public void run(){
        System.out.println("run-----begin");
        System.out.println("Thread.currentThread().getName() = " + Thread.currentThread().getName());
        System.out.println("Thread.currentThread().isAlive() = " + Thread.currentThread().isAlive());
        System.out.println("this.getName() = " + this.getName());
        System.out.println("this.isAlive() = " + this.isAlive());
        System.out.println("run-----end");
    }
}
public class MyThread {
    public static void main(String[] args) {
        CountOperate c = new CountOperate();
        Thread t1 = new Thread(c);
        System.out.println("main begin t1 isAlive = " + t1.isAlive());
        t1.setName("A");
        t1.start();
        System.out.println("main end t1 isAlive = " + t1.isAlive());
    }
}
*/
    //实现Runnable接口和继承Thread类都有一个缺点：任务没有返回值。
//还有一种方法：使用FutureTask 任务类
    /*
class MyTask implements Callable<String>
{

    @Override
    public String call() throws Exception {
        return "Hello";}
}
public class MyThread
{
    public static void main(String[] args) throws InterruptedException {

        //创建异步任务
        FutureTask<String> futureTask=new FutureTask<>(new MyTask());
        //启动线程
        new Thread(futureTask).start();
        try
        {
            //等待任务执行完毕，并返回结果
            String result=futureTask.get();
            System.out.println(result);
        }
        catch (ExecutionException e)
        {e.printStackTrace();}
    }}
*/




