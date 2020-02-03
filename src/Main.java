import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.lang.Thread;
/*
public class Main
{
    public static void main(String[] args) {
Vector<String> v=new Vector<>();
v.add("hello");
v.add("hello");
v.add("B");
v.add("bit");
Enumeration<String> enumeration=v.elements();
while (enumeration.hasMoreElements())
{System.out.println(enumeration.nextElement());}




    }}
    */

//守护线程
/*
public class Main
{
    public static void main(String[] args) {
        Thread thread=new Thread(new Runnable() {
            @Override
            //是个无限循环
            public void run() {
                for(;;)
                {}
            }
        });
        thread.start();
        System.out.println("main thread is over");
    }
}

/**
运行结果为：
 main thread is over
但是JVM进程并没有退出
说明当父线程结束之后，子线程还是可以存在的，
 说明子线程的生命周期并不受父线程的影响。
 这也说明了在用户线程还存在的情况下，JVM进程不会终止。
 */



//将进程设置为守护进程
public class Main {


    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            //是个无限循环
            public void run() {
                for (; ; ) {
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
        System.out.println("main thread is over");
    }


//如果将thread设置为守护线程，那么可以Main执行完JVM进程就终止了。
/**
 * 在这个例子中，main函数是唯一的用户线程，thread线程是守护线程，
 * 当main线程执行结束后，JVM发现已经没有用户线程了，就会终止JVM进程。
 * 由于这里的守护线程执行的是一个死循环，
 * 可以说明JVM不等守护进程运行完毕就会结束JVM进程。
 */


@Test
public void test20200202()
{
    String start = "2000-01-01";
    String end = "3000-12-31";
    SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
               Date startDate;
         long startDateL;
         long endDateL;
         long oneDay = 24*60*60*1000;
     try  {
             startDate = myFormatter.parse(start);
             startDateL = startDate.getTime();
             endDateL = myFormatter.parse(end).getTime();
             while(startDateL < endDateL){
                 String s = myFormatter.format(startDate).replaceAll("-", "");
                 String[] str={s.substring(0, 4),s.substring(4)};
                 StringBuffer sb = new StringBuffer(str[1]);
                 if(str[0].toString().equals(sb.reverse().toString())){
                   System.out.println(myFormatter.format(startDate));
                 }
                 startDateL = startDateL + oneDay;
                 startDate.setTime(startDateL);
             }
          } catch (ParseException e) {
                         e.printStackTrace();
                    }
                }
               }

