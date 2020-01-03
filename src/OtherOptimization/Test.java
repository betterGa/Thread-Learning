package OtherOptimization;

//锁的粗化
//锁粗化就是将多次连接在一起的加锁、解锁操作合并为一次
//将多个连续的锁扩展为一个范围更大的锁
public class Test {
    private static StringBuffer sb=new StringBuffer();

    public static void main(String[] args) {
        sb.append("a");
        sb.append("b");
        sb.append("c");
    }
}
//这里每次调用StringBuilder的append方法都需要加锁和解锁，
//如果虚拟机检测到有一系列连串的对同一个对象加锁和解锁操作
//就会将其合成一次范围更大的加锁和解锁操作
//即在第一次append方法时进行加锁，最后一次append方法结束后进行解锁


/**
 *
 */

