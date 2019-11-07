package ArrayListVSVector;

    import java.util.*;
    //验证ArrayList是线程非安全的
//可以看到ArrayList类中的add方法：
/*
 public boolean add(E e) {
        ensureCapacityInternal(size + 1);  // Increments modCount!!
        elementData[size++] = e;
        return true;
    }*/
//如果现在两个线程A和B,size=10
//A先执行了elementData[size]=e;操作，时间片空出来，
//线程B执行elementData[size]=e操作，就会把A当时加的值覆盖掉。
//线程A,B再依次size++.size变成12，而两次赋值都在一个位置[10]
//将导致elementData[11]=null
    public class ListTask implements Runnable{

        private List<String> list;

        public ListTask(List list)
        {this.list=list;}

        @Override
        public void run() {
            try
            {
                //线程睡眠10ms
                Thread.sleep(10);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

//把当前线程名加入list中
            list.add(Thread.currentThread().getName());
        }

        public List<String> getList() {
            return list;
        }

        public void setList(List<String> list) {
            this.list = list;
        }}


