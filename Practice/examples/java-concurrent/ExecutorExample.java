import java.util.concurrent.Executor;

public class ExecutorExample {
  public static void main(String[] args) {
    Executor executor = new Invoker();
    executor.execute( () -> {
      System.out.println("Hello Executor!");
    });
  }

}

class Invoker implements Executor {
  @Override
  public void execute(Runnable r) {
      r.run();
  }
}