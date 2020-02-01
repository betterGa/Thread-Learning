package ProducerConsumerModel;

//wait()是Object类中的方法，将当前线程置入“预执行队列”中
//并且在wait()方法所在的代码处停止执行，直到接到通知或被中断为止
//wait()方法只能在同步方法或同步块中调用。
//如果调用wait()时，没有持有适当的锁，会抛出异常。
//wait()方法执行后，当前线程释放锁，线程与其他线程竞争重新获取锁。

public class Owait {
    public static void main(String[] args)throws InterruptedException
    {
        Object object=new Object();
        synchronized (object)
        {
            System.out.println("等待中...");
            object.wait();
            System.out.println("等待已过");
        }
        System.out.println("main 方法结束");
    }
}
