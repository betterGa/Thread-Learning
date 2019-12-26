package FollowCoreJava;
//跟着核心卷上的例子。


//在大多数实际的多线程应用中，两个或两个以上的线程需要共享对同一数据的存取。
//如果两个线程存取相同的对象，并且每一个线程都调用了一个修改该对象状态的方法
//将会发生神马？可以想象，线程彼此踩了彼此的脚。
//根据各线程访问数据的次序，可能会产生讹误的对象
//这样的情况称作 “竞争条件”

//接下来模拟一个有若干账户的银行
//有transfer方法，该方法从一个账户随机转移一定数量到另一个账户。

public class UnsynchBankTest {
public static final int NACCOUNTS=100;
public static final double INITIAL_BALANCE=1000;
public static final double MAX_AMOUNT=1000;
public static final int DELAY=10;

    public static void main(String[] args) {
        Bank bank=new Bank(NACCOUNTS,INITIAL_BALANCE);
        for(int i=0;i<NACCOUNTS;i++)
        {int fromAccount=i;
        Runnable r=()->{
            try {
                while (true)
                {int toAccount=(int)(bank.size()*Math.random());
int account=(int)(MAX_AMOUNT*Math.random());
                bank.transfer(fromAccount,toAccount,account );
                Thread.sleep((int)(DELAY*Math.random()));}
            }
            catch (InterruptedException e)
            {}
        };
        Thread t=new Thread(r);
        t.start();
        }
    }
}
//运行结果，Thread[Thread-85,5,main]
//    274.82 from 85 to 94Total Balance:  98834.76
//Thread[Thread-95,5,main]
//     93.79 from 95 to 96Total Balance:  98834.76
//Thread[Thread-76,5,main]
//    586.88 from 76 to 1Total Balance:  98834.76
//Thread[Thread-61,5,main]
//    287.03 from 61 to 9Total Balance:  98834.76

//发现总额有时候不是100000.为什么呢？
//以 "accounts[to]+=amount" 为例，(在Bank类的transfer方法中) 这个指令不是原子操作，执行过程是这样的：
//(1) 将accounts[to]加载到寄存器
//(2) 增加amount
//(3) 将结果写回accounts[to]
//假设第1个线程执行步骤1和2，然后，它被剥夺了运行权。
//假设第2个线程被唤醒并修改了accounts数组中的同一项，
//然后，线程1被唤醒，并完成第3步。
//那么，线程2的改写数据操作就被擦去了，线程1没有把线程2改的数据写回。

//有很多语句的反编译结果，是好几条字节码指令。
//执行它们的线程可以在任何一条指令上被中断
//而每个方法都是 执行过程中可能被打断。

//用锁机制保护transfer方法
