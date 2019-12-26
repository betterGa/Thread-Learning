package FollowCoreJava;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bank {
    private Condition suffientFunds;

private final double[] accounts;

/*
public Bank()
{suffientFunds=bankLock.newCondition();}


public Bank(int n,double initialBalance)
{
    accounts=new double[n];
    Arrays.fill(accounts,initialBalance);
}
*/
/**
public void transfer(int from,int to,double amount)
{
    if(accounts[from]<amount) return;
    System.out.println(Thread.currentThread());
    accounts[from]-=amount;
    System.out.printf("%10.2f from %d to %d",amount,from,to);
accounts[to]+=amount;
System.out.printf("Total Balance:%10.2f%n",getTotalBalance());
}
*/




/*
//用锁机制保护transfer方法：
    private Lock bankLock=new ReentrantLock();
    public void transfer(int from,int to,int amount)
    {
        bankLock.lock();
    try{
        System.out.print(Thread.currentThread());
        accounts[from]-=amount;
        System.out.printf("%10.2f from %d to %d",amount,from,to);
        accounts[to]+=amount;
        System.out.printf("Total Balance:%10.2f%n",getTotalBalance());}
    finally {
        bankLock.unlock();}
    }
*/

private Lock bankLock;
//条件对象（条件变量）的使用
public Bank(int n,double initialBalance)
{
    accounts=new double[n];
    Arrays.fill(accounts,initialBalance);
    bankLock=new ReentrantLock();
    suffientFunds=bankLock.newCondition();}

public void transfer(int from,int to,double amout) throws InterruptedException
{
    bankLock.lock();
    try {
        while (accounts[from]<amout)
            suffientFunds.await();

        System.out.print(Thread.currentThread());
        accounts[from]-=amout;
        System.out.printf("%10.2f from %d to %d",amout,from,to);
        accounts[to]+=amout;
        System.out.printf("Total Balance:%10.2f%n",getTotalBalance());
        suffientFunds.signalAll();
    }
    finally {
        bankLock.unlock();
    }
}


public double getTotalBalance() {
    bankLock.lock();
    try {
        double sum = 0;
        for (double a : accounts)
            sum += a;
            return sum;
    } finally {
        bankLock.unlock();
    }
}

/*

//因为要使用条件对象所以将此段代码改写如上
//同时上面的方法还使用了lock unlock锁了代码块

public double getTotalBalance()
{double sum=0;
for(double a:accounts)
{sum+=a;}
return sum;
}
*/



public int size()
{return accounts.length;}
}

