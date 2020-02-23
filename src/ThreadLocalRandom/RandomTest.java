package ThreadLocalRandom;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomTest {
    public static void main(String[] args) {
        /**
        //创建一个默认种子的随机数生成器
        Random random=new Random();
        //生成 10 个 在 0~5 之间的随机数
        for(int i=0;i<10;i++)
        System.out.println(random.nextInt(5));
    }
}*/


      ThreadLocalRandom random=ThreadLocalRandom.current();

      for(int i=0;i<10;i++)
      {
          System.out.println(((ThreadLocalRandom) random).nextInt(5));
      }
    }}
