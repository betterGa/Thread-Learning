package Unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.time.Year;

/*
public class TestUnsafe {
    //获取Unsafe的实例
static final Unsafe unsafe= Unsafe.getUnsafe();

//记录变量 state 在类 TestUnsafe 中的偏移量 (2,2,2)
    static final long stateOffset;

    //变量
    private volatile long state=0;

    static
    {
        try
        {
            //获取 state 变量在类 TestUnsafe 中的偏移值
            stateOffset=unsafe.objectFieldOffset(TestUnsafe.class.getDeclaredField("state"));
        }
        catch (Exception e)
        {System.out.println(e.getLocalizedMessage());
        throw new Error(e);}
    }

    public static void main(String[] args) {
        //创建实例，并设置 state 值
        TestUnsafe test=new TestUnsafe();
        Boolean sucess=unsafe.compareAndSwapInt(test,stateOffset,0,1);
        System.out.println(sucess);
    }
}
*/

//用反射机制来实例化 Unsafe 类
public class TestUnsafe
{

    static final Unsafe unsafe;
    static final long stateOffset;
    private volatile long state=0;
    static
    {
        try
        {

            //利用反射获取 Unsafe 的成员变量 theUnsafe
            Field field=Unsafe.class.getDeclaredField("theUnsafe");

            //设置为可存取
            field.setAccessible(true);

            //获取该变量的值
            unsafe=(Unsafe) field.get(null);

            //获取 state 在 TestUnsafe 中的偏移量
            stateOffset=unsafe.objectFieldOffset(TestUnsafe.class.getDeclaredField("state"));
        } catch (Exception e)
        {System.out.println(e.getLocalizedMessage());
        throw new Error(e);
    }
}

    public static void main(String[] args) {
        TestUnsafe testUnsafe=new TestUnsafe();
        Boolean success=unsafe.compareAndSwapInt(testUnsafe,stateOffset,0,1);
        System.out.println(success);
    }
}