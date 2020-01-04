package DeadLock;

//同步的本质在于：一个线程等待另外一个线程执行完毕执行完成后才可以继续执行。
//但是如果现在相关的几个线程彼此之间都在等待着，那么将造成死锁。



class Pen
{
    private String pen="笔";
    public String getPen()
    {return pen;}

}

class Book
{
    private String book="本子";
    public String getBook()
    {return book;}
}

public class DeadLock {
    private static Pen pen=new Pen();
    private static Book book=new Book();

    public static void main(String[] args) {
        new DeadLock().deadLock();
    }
    public void deadLock()
    {Thread thread1=new Thread(new Runnable()
    //笔线程
    {
        @Override
        public void run() {
           synchronized (pen)
           {System.out.println(Thread.currentThread()+"我有笔，我就不给你。");
           synchronized (book)
           {System.out.println(Thread.currentThread()+"把你的本子给我。");}


           }
        }
    },"Pen");

    Thread thread2=new Thread(new Runnable()
    //本子线程
    {
        @Override
        public void run() {
            synchronized (book)
            {System.out.println(Thread.currentThread()+"我有本子，我就不给你。");
            synchronized (pen)
            {System.out.println(Thread.currentThread()+"把你的笔给我。");}
            }
        }
    },"Book");
    thread1.start();
    thread2.start();
    }
}


/**
 * 这个就是典型的
 * 【线程A已经持有了资源2，它同时还想申请资源1；
 *   线程B已经持有了资源1，它同时还想申请资源2
 *   所以线程A和B就因为相互等待对方已经持有的资源，而进入了死锁状态。】
 *
 */