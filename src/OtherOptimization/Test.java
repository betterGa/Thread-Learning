package OtherOptimization;

//锁的粗化
//锁粗化就是将多次连接在一起的加锁、解锁操作合并为一次
//将多个连续的锁扩展为一个范围更大的锁
/**
public class Test {
    private static StringBuffer sb=new StringBuffer();

    public static void main(String[] args) {
        sb.append("a");
        sb.append("b");
        sb.append("c");
    }
}*/
//这里每次调用StringBuilder的append方法都需要加锁和解锁，
//如果虚拟机检测到有一系列连串的对同一个对象加锁和解锁操作
//就会将其合成一次范围更大的加锁和解锁操作
//即在第一次append方法时进行加锁，最后一次append方法结束后进行解锁

/**
*
 */

//锁消除
//锁消除即删除不必要的加锁操作。
//根据代码逃逸技术,如果判断到一段代码中，堆上的数据不会逃逸出当前线程
//那么可以认为这段代码是线程安全的，不必要加锁。
public class Test
{
    public static void main(String[] args) {
        StringBuffer sb=new StringBuffer();
        sb.append("a").append("b").append("c");
    }
}

//虽然StringBuffer的append是一个同步方法，
// 但是这段程序中的StringBuffer属于一个局部变量
//并且不会从该方法中逃逸出去，所以其实这过程是线程安全的，可以将锁清除

