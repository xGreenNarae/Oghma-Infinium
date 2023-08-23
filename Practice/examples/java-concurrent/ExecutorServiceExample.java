import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceExample {
  

  public static void main(String[] args) {
    // Create a fixed-size thread pool with 2 threads
    ExecutorService executor = Executors.newFixedThreadPool(2);

    // Submit tasks for execution
    for (int i = 1; i <= 5; i++) {
        final int taskNumber = i;
        executor.submit(() -> {
            System.out.println("Task " + taskNumber + " executed by " + Thread.currentThread().getName());
        });
        // 또는
        executor.submit(new Task(i));
    }

    // Shutdown the executor when done
    executor.shutdown();

  }
}


class Task implements Runnable {
    private int taskNumber;

    public Task(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    @Override
    public void run() {
      System.out.println("Hello Task : " + taskNumber);
    }
}