package ThreadPoolExecutor;


import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Test {
    @org.junit.Test
   public void test1() {
        //手工创建一个线程池
        //public ThreadPoolExecutor(int corePoolSize,
        //                              int maximumPoolSize,
        //                              long keepAliveTime,
        //                              TimeUnit unit,
        //                              BlockingQueue<Runnable> workQueue) {
        //        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
        //             Executors.defaultThreadFactory(), defaultHandler);

    ThreadPoolExecutor threadPoolExecutor= new ThreadPoolExecutor(3,
                5,
                2000,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
    }

@org.junit.Test

    public void test2()
{

}

}
