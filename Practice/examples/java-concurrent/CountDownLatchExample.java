import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {
  public static void main(String[] args) throws InterruptedException {
      int numberOfThreads = 3;
      CountDownLatch latch = new CountDownLatch(numberOfThreads);

      for (int i = 0; i < numberOfThreads; i++) {
          final int threadNumber = i;
          new Thread(() -> {
              System.out.println("Thread " + threadNumber + " is doing some work.");
              // Simulate some work
              try {
                  Thread.sleep(2000);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
              System.out.println("Thread " + threadNumber + " has completed.");
              latch.countDown(); // Decrement the count
          }).start();
      }

      // Wait until the count reaches zero
      latch.await();

      System.out.println("All threads have completed. Main thread continues.");
  }
}
