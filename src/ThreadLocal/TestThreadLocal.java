package ThreadLocal;
//ThreadLocal不支持继承性
public class TestThreadLocal {
    //创建线程变量
   /*public static ThreadLocal<String> threadLocal1=new ThreadLocal<String>();*/
//改为可继承的：
    public static ThreadLocal<String> threadLocal1=new InheritableThreadLocal<>();
    public static void main(String[] args) {

        threadLocal1.set("hello world");
    Thread thread=new Thread(new Runnable() {
        @Override
        public void run() {
            //子线程输出线程变量的值
            System.out.println("thread:"+threadLocal1.get());
        }
    });
    thread.start();
    //主线程输出线程变量的值
        System.out.println("main:" +threadLocal1.get());
    }
}

/**运行结果为：
 * main:hello world
 * thread:null
 */

/*
同一个ThreadLocal变量在父线程中被设置值后，在子线程中是获取不到的。
因为在子线程thread里调用get方法时当时线程是thread线程，
而这里调用set方法设置线程变量的是mian线程，两个线程是不同的线程。
所以子线程访问时返回null。
那么有无方法可以让子线程访问到父线程呢？

有个继承自ThreadLocal的InheritableThreadLocal
让子线程可以访问在父线程中设置的本地变量.
 */