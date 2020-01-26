package waitAndNotify;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test {
    public static void main(String[] args) {
    Lock lock=new ReentrantLock();
    Condition condition=lock.newCondition();
    for(int i=0;i<10;i++)
    {
        Thread thread=new Thread(()->
        {
            lock.lock();
            try{
                condition.await();
            }
            catch (InterruptedException e)
            {e.printStackTrace();}
            finally {
                lock.unlock();
            }
        });
        thread.start();
    }

    }}
