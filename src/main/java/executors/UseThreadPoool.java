package executors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class MyJob implements Callable<String> {
  private static int count = 0;
  private String name = "Callable " + count++;

  public String call() throws Exception {
    System.out.println("Starting job " + name);
    Thread.sleep((int)(Math.random() * 1000) + 1000);
    return "Job " + name + " result";
  }
}
public class UseThreadPoool {
  public static void main(String[] args) throws Throwable {
    ExecutorService es = Executors.newFixedThreadPool(2);

    List<Future<String>> handles = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      Future<String> handle = es.submit(new MyJob());
      handles.add(handle);
    }
    System.out.println("All jobs submitted");
    while (handles.size() > 0) {
      Iterator<Future<String>> ifs = handles.iterator();
      while (ifs.hasNext()) {
        Future<String> h = ifs.next();
        if (h.isDone()) {
          System.out.println("Job completed: " + h.get());
          ifs.remove();
        }
      }
    }
    System.out.println("All jobs finished...");
  }

}
