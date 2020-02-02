package ThreadPoolExecutor;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//向线程池提交任务。
//方法一：execute() 方法,用于提交不需要返回值的任务，所以无法判断任务是否被线程池执行成功
class RunnabledThread implements Runnable
{
    @Override
    public void run() {
        for(int i=0;i<5;i++)
        {
            System.out.println(Thread.currentThread().getName()+"、"+i);

        }
    }
}
public class Execute {
    public static void main(String[] args) {
        RunnabledThread runnabledThread=new RunnabledThread();
        ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor(3,50,2000,TimeUnit.MILLISECONDS,new LinkedBlockingQueue<>());
        for(int i=0;i<5;i++)
        {
            threadPoolExecutor.execute(runnabledThread);
        }
    }
}
