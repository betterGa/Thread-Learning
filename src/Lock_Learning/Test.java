package Lock_Learning;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

//查看AQS的前驱和后继节点
public class Test {
    private static Lock lock=new ReentrantLock();
    public static void main(String[] args) {
        for(int i=0;i<5;i++)
        {Thread thread=new Thread(()->
            {lock.lock();
            try
            {Thread.sleep(10000);}
            catch(Exception e)
            {e.printStackTrace();}
            finally {
                lock.unlock();
            }
            });
        thread.start();
        }
    }
}
