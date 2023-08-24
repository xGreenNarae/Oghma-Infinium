import java.util.LinkedList;
import java.util.Queue;

public class SynchronizedWaitNotifyExample {
    private final Object lock = new Object();
    private Queue<Integer> buffer = new LinkedList<>();
    private static final int BUFFER_CAPACITY = 5;

    public void produce() throws InterruptedException {
        synchronized (lock) {
            while (buffer.size() == BUFFER_CAPACITY) {
                System.out.println("Buffer is full. Producer is waiting.");
                lock.wait();
            }

            int value = (int) (Math.random() * 100);
            buffer.offer(value);
            System.out.println("Produced: " + value);
            printBuffer();

            lock.notify();
        }
    }

    public void consume() throws InterruptedException {
        synchronized (lock) {
            while (buffer.isEmpty()) {
                System.out.println("Buffer is empty. Consumer is waiting.");
                lock.wait();
            }

            int value = buffer.poll();
            System.out.println("Consumed: " + value);
            printBuffer();

            lock.notify();
        }
    }

    private void printBuffer() {
        System.out.print("--------- Buffer content: ");
        for (int value : buffer) {
            System.out.print(value + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        SynchronizedWaitNotifyExample example = new SynchronizedWaitNotifyExample();

        Thread producerThread = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    example.produce();
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread consumerThread = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    example.consume();
                    Thread.sleep(800);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        producerThread.start();
        consumerThread.start();

        try {
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
