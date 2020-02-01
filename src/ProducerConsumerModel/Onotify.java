package ProducerConsumerModel;

//notify方法使停止的线程继续执行
//notify方法也要在同步方法或同步块中调用，通知那些可能等待该对象的对象锁的其他线程
//对其通知 notify，并使它们重新获取该对象的对象锁。
//如果有多个线程等待，则有线程规划期随机挑选出一个呈 wait 状态的线程。

//在notify()方法后，当前线程不会马上释放该线程锁，要等到执行 notify 方法的线程将程序执行完
//也就是退出同步块之后，才会释放线程锁

//使用notify 方法唤醒线程

class MyThread implements Runnable{
    private boolean flag;
    private Object object;

public MyThread(boolean flag,Object object)
{
    super();
    this.flag=flag;
    this.object=object;
}

public void waitMethod()
{
    synchronized (object)
    {try {
        while (true)
        {System.out.println("wait 方法开始..."+Thread.currentThread().getName());
        object.wait();
        System.out.println("wait 方法结束..."+Thread.currentThread().getName());
        return; }
    }
    catch (Exception e)
    {e.printStackTrace();}
    }
}

public void notifyMethod()
{
    synchronized (object)
    {try
    {System.out.println("notify方法开始"+Thread.currentThread().getName());
    object.notify();
        System.out.println("notify方法结束"+Thread.currentThread().getName());
    }
    catch (Exception e)
    {e.printStackTrace();}
    }
}

@Override
    public void run()
{if(flag)
this.waitMethod();
else {this.notifyMethod();}
}}
public class Onotify {
    public static void main(String[] args) throws InterruptedException {
        Object object=new Object();
        MyThread waitThread=new MyThread(true,object);
        MyThread notifyThread=new MyThread(false,object);
        Thread thread1=new Thread(waitThread,"wait线程");
        Thread thread2=new Thread(notifyThread,"notify线程");
        thread1.start();
        Thread.sleep(100);
        thread2.start();
        System.out.println("main方法结束");

    }
}
