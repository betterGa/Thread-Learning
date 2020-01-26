package waitAndNotify;


import java.sql.Connection;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//使用Condition来实现有界队列
/*
有界队列是一种特殊的队列，
当队列为空时，队列的获取（删除）操作将会阻塞获取（删除）线程，直到队列中有新增元素；
当队列已满时，队列的插入操作将会阻塞插入线程，直到队列出现空位。
 */

public class BoundedQueue<T> {
    private Object[] items;
    //队列中当前元素个数
    private int count;
    private Lock lock=new ReentrantLock();
    private Condition empty=lock.newCondition();
    private Condition full=lock.newCondition();

    //有参构造传入size，Object数组长度
    public BoundedQueue(int size)
    {items=new Object[size];}

    //添加元素方法，
    // 如果当前队列已满，则添加线程进入等待状态，直到有空位被唤醒
    public void add(T t,int addIndex) throws InterruptedException {
        lock.lock();
        try
        {
            //当前队列已满，添加线程进入等待状态
            while (count==items.length)
            {full.await();}
        items[addIndex]=t;
            count++;
            empty.signal();
    }
    finally {
            lock.unlock();
        }
        }

        //删除元素方法
    //如果当前队列为空，则移除线程进入等待状态直到队列不为空时被唤醒
    public T remove(int removeIndex)throws InterruptedException
    {lock.lock();
        try {
            //当队列为空时，移除线程进入等待状态
            while (count==0)
            {
                empty.await();
            }
            Object x=items[removeIndex];
            count--;
            full.signal();
            return (T)x;
        }
        finally {
            lock.unlock();
        }
    }

}
