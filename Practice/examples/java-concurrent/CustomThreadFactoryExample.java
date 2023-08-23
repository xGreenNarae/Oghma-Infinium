import java.util.concurrent.ThreadFactory;

public class CustomThreadFactoryExample {
    public static void main(String[] args) {
        ThreadFactory factory = new CustomThreadFactory("MyThreadGroup");

        for (int i = 0; i < 5; i++) {
            Runnable task = () -> System.out.println("Thread running: " + Thread.currentThread().getName());
            Thread thread = factory.newThread(task);
            thread.start();
        }
    }
}

class CustomThreadFactory implements ThreadFactory {
    private int threadId;
    private String name;

    public CustomThreadFactory(String name) {
      this.threadId = 1;
      this.name = name;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, name + "-Thread_" + threadId);
        //     thread.setDaemon(false); // Not a daemon thread
        //     thread.setPriority(Thread.NORM_PRIORITY); // Normal priority
        //     thread.setName(threadGroupName + "-Thread-" + thread.threadId());
        System.out.println("created new thread with id : " + threadId +
            " and name : " + t.getName());
        threadId++;
        return t;
    }
}