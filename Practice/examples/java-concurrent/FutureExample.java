import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<Integer> future = executor.submit(() -> {
            Thread.sleep(2000);
            return 42;
        });

        executor.shutdown();

        try {
            // Check if the task is done
            boolean isDone = future.isDone();
            System.out.println("Task done: " + isDone);

            // Wait for the task to complete and get the result
            int result = future.get();
            System.out.println("Task result: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}