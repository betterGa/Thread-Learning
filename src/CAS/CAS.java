package CAS;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

//解决ABA问题
public class CAS {
    public static void main(String[] args) {
        //AtomicReference “可以原子更新的对象引用”
        AtomicReference<Integer> atomicReference=new AtomicReference<>(100);
        new Thread(()->
        {
            //ti线程第一次修改为101
            System.out.println(Thread.currentThread().getName()+
                    ":"+atomicReference.compareAndSet(100,101)+
                    //get方法是获得当前值
                    " "+atomicReference.get());

            //t1线程第二次改回100
            System.out.println(Thread.currentThread().getName()+":"+
            atomicReference.compareAndSet(101,100)+" "+
                    atomicReference.get());},"t1").start();
            new Thread(()->
        {
            try {
                //t2线程先休眠1s，保证t1线程执行完
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //t2线程CAS操作，只要是100就修改为2019，
            //但是不知道中间修改过一次
            System.out.println(Thread.currentThread().getName() + ":"
                    + atomicReference.compareAndSet(100, 2019) +
                    " " + atomicReference.get());
        },"t2").start();
            //由运行结果可知，修改成功了。
        /**
         * ABA问题使用版本号/时间戳解决。
         * AtomicStampedReferenced
         */

//初始值，初始版本号
        AtomicStampedReference<Integer> atomicStampedReference
                =new AtomicStampedReference<>(100,1);
new Thread(
        ()->
        {
            //获得最开始的版本号1
            int stamp=atomicStampedReference.getStamp();
            try
            {
                Thread.sleep(2000);
            }
            catch (InterruptedException e)
            {e.printStackTrace();}

            //t3线程修改值为101，版本号+1，现在版本号为2
            System.out.println(Thread.currentThread().getName()
            +": "+atomicStampedReference.compareAndSet(100,101,atomicStampedReference.getStamp(),
                    atomicStampedReference.getStamp()+1)+" "+
                    atomicStampedReference.getStamp());
//t3线程修改值改为100，版本号+1,现在版本号为3
            System.out.println(Thread.currentThread().getName()
            +": "+atomicStampedReference.compareAndSet(101,100,atomicStampedReference.getStamp(),
                    atomicStampedReference.getStamp()+1)+" "+
            atomicStampedReference.getStamp());},"t3").start();

new Thread(()->
{
    int stamp=atomicStampedReference.getStamp();
    try
    {Thread.sleep(4000);}
catch (InterruptedException e)
{e.printStackTrace();}
//不会修改成功，因为版本号不一致
    System.out.println(Thread.currentThread().getName()+
":"+atomicStampedReference.compareAndSet(100,2019,stamp,stamp+1)
    +" "+atomicStampedReference.getStamp());},"t4").start();
    }
}







