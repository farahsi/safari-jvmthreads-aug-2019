package compfut;

import java.util.concurrent.CompletableFuture;

public class TryPromise {

  public static void delay() {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
  public static void main(String[] args) throws InterruptedException {
    CompletableFuture<Void> cfv =
        CompletableFuture.supplyAsync(() -> "Hello")
        .thenApplyAsync(x -> x.toUpperCase())
        .thenApplyAsync(x -> {delay(); return x;})
        .thenApplyAsync(x -> "the text is " + x + " of length " + x.length())
        .thenAccept(x -> System.out.println("the result is x"));
    System.out.println("Pipeline created...");
//    Thread.sleep(2_500);

    cfv.join();
    System.out.println("All finished exiting...");
  }
}
