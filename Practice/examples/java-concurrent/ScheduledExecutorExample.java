import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorExample {
  public static void main(String[] args) {
      ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

      scheduler.schedule(() -> {
          System.out.println("Delayed task executed after 1 second.");
      }, 1, TimeUnit.SECONDS);

      scheduler.scheduleAtFixedRate(() -> {
          System.out.println("Repeating task executed every 2 seconds.");
      }, 2, 2, TimeUnit.SECONDS);

      scheduler.scheduleWithFixedDelay(() -> {
          System.out.println("Task with fixed delay executed after previous task completes.");
      }, 0, 3, TimeUnit.SECONDS);

      // Keep the program running for a while to see the output
      try {
          Thread.sleep(15000); // Sleep for 15 seconds
      } catch (InterruptedException e) {
          e.printStackTrace();
      }

      // Shutdown the scheduler when done
      scheduler.shutdown();
  }
}