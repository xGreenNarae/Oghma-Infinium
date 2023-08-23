import java.util.concurrent.Semaphore;

public class SemaphoreExample {
    public static void main(String[] args) {
        int numberOfPermits = 2;
        Semaphore semaphore = new Semaphore(numberOfPermits);

        for (int i = 0; i < 5; i++) {
            final int threadNumber = i;
            new Thread(() -> {
                try {
                    System.out.println("Thread " + threadNumber + " is trying to acquire a permit.");
                    semaphore.acquire();
                    System.out.println("Thread " + threadNumber + " has acquired a permit and is using a resource.");
                    // Simulate using the resource
                    Thread.sleep(2000);
                    System.out.println("Thread " + threadNumber + " is releasing the permit.");
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
