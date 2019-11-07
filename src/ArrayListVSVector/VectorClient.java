package ArrayListVSVector;

import java.util.List;
import java.util.Vector;

//用于测试Vector是否线程安全
public class VectorClient {
    public static void main(String[] args)throws Exception
    {
        List<String> list=new Vector<>();
        ListTask listThread=new ListTask(list);
        for(int i=0;i<100;i++)
        //生成100个线程并随即启用
        {
            /*
            public Thread(Runnable target,String name)
            分配一个新的Thread对象。
            此构造具有相同的效果Thread (null, target, name) 。

target - 启动此线程时调用其run方法的对象。 如果null ，则调用此线程的run方法。
name - 新线程的名称
             */


            /*
       String.valueOf(i)返回的是整型数据i的字符串形式
             */
            Thread thread=new Thread(listThread,String.valueOf(i));
            thread.start();
        }
/*
public static void sleep(long millis)
                  throws InterruptedException
                  使当前正在执行的线程以指定的毫秒数暂停（暂时停止执行），
                  具体取决于系统定时器和调度程序的精度和准确性。
                   线程不会丢失任何显示器的所有权。

millis - 以毫秒为单位的睡眠时间长度
异常
IllegalArgumentException - 如果 millis值为负数
InterruptedException - 如果任何线程中断当前线程。 当抛出此异常时，当前线程的中断状态将被清除。
 */

//使当前执行的线程暂停
        Thread.sleep(2000);
        System.out.println("list总长度为"+listThread.getList().size());
//输出list中的值
        for(int i=0;i<listThread.getList().size();i++)
        {System.out.print(listThread.getList().get(i)+" ");}
    }
}
