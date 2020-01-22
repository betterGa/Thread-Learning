package Lock_Learning;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
使用Cache组合一个非线程安全的HashMap作为缓存的实现，同时使用读写锁保证Cache的线程安全性。
 Cache使用读写锁提升读操作的并发性，也保证每次写操作对所有读写操作的可见性。
 */


//使用读写锁实现缓存
public class Cache {
    static Map<String,Object>  map=new HashMap<>();
    static ReentrantReadWriteLock rwl=new ReentrantReadWriteLock();
    static Lock readLock=rwl.readLock();
    static Lock writeLock=rwl.writeLock();


    /*
    在get方法中，需要获取读锁，使得并发访问该方法时不会被阻塞。
  */


    //线程安全的根据——一个key获取一个value
    public static final Object get(String key)
    {readLock.lock();
    try
    {return map.get(key);}
    finally {
        readLock.unlock();
    }
    }

    /*
    put和clear方法在更新HashMap时必须获取写锁，
    当获取写锁后，其他线程对于读锁和写锁的获取均被阻塞，
    而只有写锁被释放后，其他读写操作才能继续。
     */
    //线程安全的根据——key设置value,并返回旧的value
    public static final Object put(String key,Object value)
    {writeLock.lock();
    try
    {return map.put(key,value); }
    finally {
        writeLock.unlock();
    }
    }

    //线程安全 清空value
    public static final void clear()
    {writeLock.lock();
    try{
        map.clear();
    }
    finally {
        writeLock.unlock();
    }
    }

}
