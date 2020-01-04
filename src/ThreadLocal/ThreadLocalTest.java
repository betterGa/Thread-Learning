package ThreadLocal;

/**
 *本例开启了两个线程，在每个线程内部都设置了本地变量的值，
 * 然后调用print函数打印当前本地变量的值，
 * 如果打印后调用了本地变量的remove方法，则会删除本地内存中的该变量。
 *  */

public class ThreadLocalTest {

    static ThreadLocal<String> localVariable=new ThreadLocal<>();

    public static void main(String[] args) {
        //创建线程threadOne
        Thread threadOne=new Thread(new Runnable() {
            @Override
            public void run() {
                //设置线程One中本地变量localVariable的值
                localVariable.set("threadone local variable");
            print("threadone");
            System.out.println("threadone remove after"+":"+localVariable.get());
            }
        });

        Thread threadTwo=new Thread(new Runnable() {
            @Override
            public void run() {
                localVariable.set("threadTwo local variable");
            print("threadTwo");
            System.out.println("threadTwo remove after"+":"+localVariable.get());
            }
        });
        threadOne.start();
        threadTwo.start();
    }
    static void print(String str)
    {
        System.out.println(str+":"+localVariable.get());
        //清除当前线程本地内存中的localVariable变量
        localVariable.remove();
    }
}
