package Lock_Learning;
import com.sun.xml.internal.bind.annotation.OverrideAnnotationOf;
import org.junit.Test;

import java.sql.Connection;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

//自己实现一个简易互斥锁
public class Mutex implements Lock {

    private Sync sync=new Sync();

    //模拟可重入锁ReentrantLock类中的
    // abstract static class Sync extends AbstractQueuedSynchronizer
    static class Sync extends AbstractQueuedSynchronizer {
        @Override
        //tryAcquire方法：尝试以独占模式获取。
        // 该方法应该查询对象的状态是否允许以独占模式获取，如果是，则获取它。
        //该方法总是由执行获取的线程调用。 如果此方法报告失败，则获取方法可能将线程排队（如果尚未排队），直到被其他线程释放为止。 这可以用于实现方法Lock.tryLock() 。
        //默认实现会抛出UnsupportedOperationException 。
        /*
        AQS类中是这么写的:
           protected boolean tryAcquire(int arg) {
        throw new UnsupportedOperationException();/抛出不支持请求异常
    }

它的子类肯定会覆写这个方法的，比如ReentrantLock类中是这样覆写的:
    protected final boolean tryAcquire(int acquires) {
            return nonfairTryAcquire(acquires);
        }
    }
         */

        //我这样覆写
        protected boolean tryAcquire(int arg)
        { if(arg!=1)
            {throw new RuntimeException("信号量不为1!");}
         if(compareAndSetState(0,1))
        {
            //当前线程成功获取锁
            setExclusiveOwnerThread(Thread.currentThread());
            //获取锁成功则返回true
            return true;
        }
        //未获取锁成功则返回false
return false;
    }
@Override
        protected boolean tryRelease(int arg)
{
    if(getState()==0)
    {throw new IllegalMonitorStateException();}
    setExclusiveOwnerThread(null);
setState(0);
return true;
}

@Override
        protected boolean isHeldExclusively()
{return getState()==1;}
Condition newCondition()
{return  new ConditionObject();}}

//Lock接口实现方法
@Override
        public void lock()
{
sync.acquire(1);
}

@Override
    public void lockInterruptibly() throws InterruptedException
{sync.acquireInterruptibly(1);}

@Override
    public boolean tryLock()
{return sync.tryAcquire(1);}

@Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException
{
    return false;
}
@Override
    public void unlock()
{
    sync.release(1);
}

@Override
    public Condition newCondition()
{return sync.newCondition();}
    private static Mutex mutex=new Mutex();
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
                Thread thread = new Thread(() ->
                {
                    mutex.lock();
                    try {
                        Thread.sleep(3000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        mutex.unlock();
                    }
                });
                thread.start();
            }
        }
    }

//多线程调试可参考这篇
// https://blog.csdn.net/fuzzytalker/article/details/50925218



