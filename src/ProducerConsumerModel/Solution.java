package ProducerConsumerModel;

import javax.jws.Oneway;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Goods
    {//商品名称
        private String name;
        //当前商品数量
        private int count;
        //商品最大数量
        private int maxCount;

        public Goods(int maxCount)
        {this.maxCount=maxCount;}

    private Lock lock=new ReentrantLock();
        private Condition consumer=lock.newCondition();
        private Condition producer=lock.newCondition();

        //生产方法
        public void setGoods(String name)
        {lock.lock();
        try {
            while (count==maxCount)
            {
                System.out.println(Thread.currentThread().getName()+
                        "商品数量已达最大，等待消费者消费");
                producer.await();
            }
            Thread.sleep(200);
            //生产商品
            this.name=name;
            count++;
            System.out.println(Thread.currentThread().getName()+"生产"+toString());

            //唤醒消费者线程
            consumer.signalAll();}
            catch(InterruptedException e)
            {e.printStackTrace();}
            finally {
            lock.unlock();
        }
        }

        //消费方法
        public void getGoods()
        {lock.lock();
        try
        {
            //商品数为0时阻塞消费者线程
            while (count==0)
            {
            System.out.println(Thread.currentThread().getName()+
                    "商品已被消费完，等待生产者生产");
            consumer.await();}

            //消费产品
                count--;
                System.out.println(Thread.currentThread().getName()+"消费"+toString());

                //唤醒生产者线程
                producer.signalAll();
            }
            catch (InterruptedException e)
            {e.printStackTrace(); }

finally {
            lock.unlock();
        }
        }

@Override
        public String toString()
{return "Goods{"+"name="+name+'\''+",count="+count+'}';}}

class Producer implements Runnable
{private Goods goods;
public Producer(Goods goods)
{this.goods=goods;}
@Override
    public void run()
{while (true)
{this.goods.setGoods("限量版发行");}
}}

class Consumer implements Runnable
{
private Goods goods;
public Consumer(Goods goods)
{this.goods=goods;
}
@Override
    public void run()
{while (true)
{this.goods.getGoods();}
}}


public class Solution {
    public static void main(String[] args) {
        List<Thread> list=new ArrayList<>();
        Goods goods=new Goods(10);
        Producer producer=new Producer(goods);
        Consumer consumer=new Consumer(goods);

        //创建10个消费者线程
        for(int i=0;i<10;i++)
        {Thread  thread=new Thread(consumer,"消费者"+i);
        list.add(thread);
    }

    //创建5个生产者线程
        for(int i=0;i<5;i++)
        {Thread thread=new Thread(producer,"生产者"+i);
        list.add(thread); }
for(Thread th:list)
{th.start();}}}