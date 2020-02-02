package ThreadPoolExecutor.ExecutorFrame;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//使用定时调度池
public class Test {
    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService= Executors.newScheduledThreadPool(5);
        for(int i=0;i<5;i++)
        {scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                for(int j=0;j<10;j++)
                {System.out.println(Thread.currentThread().getName()+"、"+j);}
            }
        },2,3,TimeUnit.SECONDS);}
    }
}
