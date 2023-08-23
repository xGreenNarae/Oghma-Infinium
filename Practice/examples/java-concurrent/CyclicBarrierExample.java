import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierExample {
    public static void main(String[] args) {
        int numberOfThreads = 3;
        CyclicBarrier barrier = new CyclicBarrier(numberOfThreads, () -> {
            System.out.println("All threads have reached the barrier. Continuing...");
        });

        for (int i = 0; i < numberOfThreads; i++) {
            final int threadNumber = i;
            new Thread(() -> {
                System.out.println("Thread " + threadNumber + " is waiting.");
                // Simulate some work
                try {
                    Thread.sleep(2000);
                    System.out.println("Thread " + threadNumber + " has reached the barrier.");
                    barrier.await(); // Wait at the barrier
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}