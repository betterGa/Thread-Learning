package DeadLock;
//死锁的例子
public class DeadLockTest2 {
    //创造两个资源
private static Object resourceA=new Object();
private static Object resourceB=new Object();
//创造两个线程
    public static void main(String[] args) {
        //创建线程A
        Thread threadA=new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (resourceA)
                {
                    System.out.println(Thread.currentThread()+"get resourceA");
                try
                {Thread.sleep(1000);}
                catch (InterruptedException e)
                {e.printStackTrace();}
                System.out.println(Thread.currentThread()+"waiting get sourceB");
                synchronized (resourceB)
                {System.out.println(Thread.currentThread()+"get resourceB");}
                }
            }
        });

//创建线程B
        /*Thread threadB=new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (resourceB)
                {System.out.println(Thread.currentThread()+"get resourceB");
                try
                {Thread.sleep(1000);}
                catch (InterruptedException e)
                {e.printStackTrace();}
                System.out.println(Thread.currentThread()+"waiting get resourceA");
                synchronized (resourceA)
                {System.out.println(Thread.currentThread()
                +"get resourceA");}
                }
            }
        });
threadA.start();
threadB.start();
}
}*/

/**
 *运行结果为:
 * Thread[Thread-0,5,main]get resourceA
 * Thread[Thread-1,5,main]get resourceB
 * Thread[Thread-1,5,main]waiting get resourceA
 * Thread[Thread-0,5,main]waiting get sourceB
 * 解释：
 * Thread-0是线程A，Thread-1是线程B，
 * 代码首先创建了两个资源，并创建了两个线程。
 * 从运行结果可知：线程调度器先调度了线程A，也就是把CPU资源分配给了线程A。
 * 线程A使用synchronized(resourceA) 方法获取了resourceA的监视器锁，
 * 然后调用sleep方法休眠1s，休眠1s是为了保证线程A在获取resourceB对应的锁前，
 * 能让线程B抢占到CPU，获取资源resourceB上的锁。
 * 线程A调用sleep方法后线程B会执行synchronized(resourceB)方法，
 * 这代表线程B获取到了resourceB对象的监视器锁资源，然后调用sleep方法休眠1s。
 * 好了，到了这里，线程A获得获取到了resourceA资源，而线程B获取到了resourceB资源。
 * 线程A休眠结束后，会企图获取resourceB资源，而这时resourceB资源被线程B所持有
 * 所以线程A会被阻塞而等待
 * 而同时，线程B休眠结束后，也会企图获取resourceA的资源，这时resourceA资源已经被线程A所持有，
 * 所以线程A与B就陷入了相互等待的状态，也就产生了死锁。
 *
 *
 *
 *
 *
 * 而DeadLock那个模块中，并没有sleep,所以，可能在笔线程获取pen资源后，会输出“我有笔，我就不给你”
 * 然后可能有两种情况：
 * 1.CPU这时给了本子线程，那么本子线程执行run，获取了book资源，会输出“我有本子，我就不给你”
 * 而后，本子线程想获取pen资源，受阻塞
 * 而到笔线程，笔线程这时想获得book资源，也就互相等待，产生了死锁。
 * 2.CPU这时仍归笔线程，笔线程获得book资源，所以一次输出的是“我有笔，我就不给你”和“把你的本子给我”
 *   然后CPU给了本子线程，
 *   （笔线程不知道哪时候就释放掉刚刚的pen资源和book资源了？？？
 *   哪时候呢？？？是不是执行完，也就释放掉了。
 *   网上说“执行完run（）后，该线程结束，系统自动释放该线程占用的资源，”）
 *   本子线程先获取到book资源，就输出“我有本子，我就不给你”
 *   然后CPU仍归本子线程，获取到pen资源，输出“把你的笔给我”
 *   第二种情况是不存在死锁的。
 *   运行结果确实是两种情况都有可能出现。
 */


//修改线程B,保证资源申请的有序性，可以避免死锁。
Thread threadB=new Thread(new Runnable() {
    @Override
    public void run() {
        synchronized (resourceA)
        {
        System.out.println(Thread.currentThread()+"get resourceB");
        try{
        Thread.sleep(1000); }
        catch (InterruptedException e)
        {e.printStackTrace();}
        System.out.println(Thread.currentThread()+"waiting get ResourceA");
        synchronized (resourceB)
        {System.out.println(Thread.currentThread()+"get resourceA");}
        }
        }
});
threadA.start();
threadB.start();
}}

/**
 * 改成这样的话，在线程B中获取资源的顺序和在线程A中获取资源的顺序保持一致。
 * 资源分配有序性是指：假如线程A和线程B都需要资源1，2，3，...,n时，
 * 对资源进行排序，线程A和B只有在获取了资源n-1时才能去获取资源n。
 *
 * 运行结果为：
 * Thread[Pen,5,main]我有笔，我就不给你。
 * Thread[Pen,5,main]把你的本子给我。
 * Thread[Book,5,main]我有本子，我就不给你。
 * Thread[Book,5,main]把你的笔给我。
 * 线程A和线程B同时执行到了synchronized(resourceA)
 * 只有一个线程可以获取到resourceA上的监视器锁，
 * 假如线程A获取到了，那么线程B就会被阻塞，那么线程B也就不会去获取资源B（什么资源都获取不到的。）
 * 线程A获取到资源A的监视器锁后，继续申请resourceB的监视器锁资源，
 * 这时线程A是可以获取到的，线程A获取到resourceB的锁资源并使用它之后，
 * 才会放弃对resourceB的持有，然后再释放对resourceA资源的持有，
 * 释放resourceA后线程B才会从阻塞状态变为激活状态，
 * 所以资源的有序性 破坏了 资源的请求并持有条件 和 环路等待 条件。
 * 从而避免了死锁。
 */