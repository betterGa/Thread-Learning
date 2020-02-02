package ThreadPoolExecutor.ExecutorFrame;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//会根据需要创建新线程
//使用缓冲线程池
//大小无界，适用于执行很多的短期异步任务的小程序，或者负载较轻的服务器。
public class CacheThreadPoolTest {
    public static void main(String[] args) {
        ExecutorService executorService= Executors.newCachedThreadPool();
        for(int i=0;i<5;i++)
        {
            try
            {Thread.sleep(500);}
            catch (InterruptedException e)
            {e.printStackTrace();}
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    for(int j=0;j<10;j++)
                    {System.out.println(Thread.currentThread().getName()+"、"+j);}
                }
            });
        }
    executorService.shutdown();
    }
}
