package ThreadPoolExecutor.ExecutorFrame;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//使用Excutors框架创建FixedThreadPool,可重用固定线程数的线程池
//适用于为了满足资源管理的需求，而需要限制当前线程数量的应用场合，
//适用于负载比较重的服务器。

public class FixedThreadPoolTest {
    public static void main(String[] args) {
        ExecutorService executorService= Executors.newFixedThreadPool(5);
        for(int i=0;i<5;i++)
        {executorService.submit(new Runnable() {
            @Override
            public void run() {
                for(int j=0;j<10;j++)
                {System.out.println(Thread.currentThread().getName()+"、"+j);}
            }
        });}

    executorService.shutdown();}
}
