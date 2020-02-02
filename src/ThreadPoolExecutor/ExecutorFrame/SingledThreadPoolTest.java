package ThreadPoolExecutor.ExecutorFrame;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//使用单个worker线程。
//适用于需要保证顺序地执行各个任务。
public class SingledThreadPoolTest {
    public static void main(String[] args) {
        ExecutorService executorService= Executors.newSingleThreadExecutor();
        for(int i=0;i<5;i++)
        {executorService.submit(new Runnable() {
            @Override
            public void run() {
                for(int j=0;j<10;j++)
                {System.out.println(Thread.currentThread().getName()+"、"+j);}
            }
        });}
        executorService.shutdown();
    }
}
