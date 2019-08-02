package simple;

public class Stopper {
  private static boolean stop = false;

  public static void main(String[] args) throws InterruptedException {
    new Thread(() -> {
      System.out.println(Thread.currentThread().getName() + " Starting");
      while (! stop)
        System.out.print(".")
            ;
      System.out.println(Thread.currentThread().getName() + " Stopping");
    }).start();

    System.out.println("Stopper thread started...");
    Thread.sleep(1_000);
    stop = true;
    System.out.println("Stop set to true, main exiting..");
  }
}
