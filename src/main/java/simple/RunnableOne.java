package simple;

class MyJob implements Runnable {
  int i = 0;
  @Override
  public void run() {
    for (; i < 1_000; i++) {
      System.out.println(Thread.currentThread().getName() +
          " i is " + i);
    }
    System.out.println(Thread.currentThread().getName() + " Finished.");
  }
}

public class RunnableOne {
  public static void main(String[] args) throws InterruptedException {
    MyJob mj = new MyJob();

    System.out.println("about to start my job");
    Thread t1 = new Thread(mj);
//    t1.setDaemon(true);
    t1.start();
    Thread t2 = new Thread(mj);
    t2.start();
    Thread.sleep(5);
    System.out.println("my job finished");
  }
}
