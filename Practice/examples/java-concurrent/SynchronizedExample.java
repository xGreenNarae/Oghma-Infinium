public class SynchronizedExample {
    private int count = 0;
    private final Object lock = new Object();
    public static void main(String[] args) {
        SynchronizedExample example = new SynchronizedExample();

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    example.increment();
                }
            }).start();
        }

        try {
            Thread.sleep(2000); // Allow threads to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final count: " + example.getCount());
    }

    public synchronized void increment() {
        count++;
    }

    public int getCount() {
      // synchronized block example
      synchronized (lock) {
          return count;
      }
    }

}
