package ThreadPoolExecutor;

//向线程池提交任务。
//方法二：submit()方法,用于提交需要返回值的任务
//线程池会返回一个 future 类型的对象，通过这个 future 对象可以判断任务是否执行成功
//而使用 get(long timeout, TimeUnit unit) 方法则会阻塞当前线程一段时间后立即返回
//这时有可能任务没有执行完



import java.util.concurrent.*;

class CallableThread implements Callable<String>
{
    @Override
    public String call() throws Exception
    {for(int i=0;i<5;i++)
    {
        System.out.println(Thread.currentThread().getName()+"、"+i);
    }
    return Thread.currentThread().getName()+"任务执行完毕";
    }
}
public class Submit {

    public static void main(String[] args) {
        CallableThread callableThread=new CallableThread();
        ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor(3,5,2000,TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
        for(int i=0;i<5;i++)
        {
            Future<String> future=threadPoolExecutor.submit(callableThread);
       try
       {String string=future.get();
       System.out.println(string);}
        catch (Exception e)
        {e.printStackTrace();}
        }
    }
}
