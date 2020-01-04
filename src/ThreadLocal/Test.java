package ThreadLocal;
//ThreadLocal的简单使用

/**
 * ThreadLocal用于提供线程局部变量，
 * 在多线程环境可以保证各个线程里的变量独立于其他线程里的变量。
 *也就是说，ThreadLocal可以为每个线程创建一个【单纯的变量副本】
 * 相当于线程的private static类型变量
 *
 * ThreadLocal的作用和同步机制有些相反：
 * 同步机制是为了保证多线程环境下数据的一致性
 * 而ThreadLocal是为了保证多线程环境下的独立性
 */

//ThreadLocal的简单使用
public class Test
{
    private static String commStr;
    private static ThreadLocal<String> threadStr=new ThreadLocal<String>();

    public static void main(String[] args) {
        commStr="main";
        threadStr.set("main");
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                commStr="thrad";
                threadStr.set("thread");
            }
        });
        thread.start();
        try
        {
            thread.join();
        }
        catch (InterruptedException e)
        {e.printStackTrace();}
    System.out.println(commStr);
    System.out.println(threadStr.get());
    }
}