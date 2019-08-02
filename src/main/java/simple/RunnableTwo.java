package simple;

public class RunnableTwo {
  public static void main(String[] args) throws InterruptedException {
    int [] data = {0, 0};
    Runnable job = () -> {
      for (int i = 0; i < 1_000_000; i++) {
        data[0]++;
        data[1]++;
      }
      System.out.println("Finished");
    };
    Thread t1 = new Thread(job);
    t1.start();
    Thread t2 = new Thread(job);
    t2.start();
    t1.join();
    t2.join();
    System.out.println("Finished, values are " +
        data[0] + " and " + data[1]);
  }
}
