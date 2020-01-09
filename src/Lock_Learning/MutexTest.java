package Lock_Learning;


import org.junit.Test;

import static org.junit.Assert.*;


public class MutexTest {
   private static Mutex mutex=new Mutex();
   @Test
   public void test() {

      for (int i = 0; i < 10; i++) {
         Thread thread = new Thread(() ->
         {
            mutex.lock();
            try {
               Thread.sleep(3000);
            } catch (Exception e) {
               e.printStackTrace();
            } finally {
               mutex.unlock();
            }
         });
         thread.start();
      }
   }
}
