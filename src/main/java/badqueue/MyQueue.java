package badqueue; // Seriously this is a bad queue!! Use the API queue

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MyQueue<T> {
  public static final int SIZE = 10;
  private T[] data = (T[]) new Object[SIZE];
  private int count = 0;

  public void put(T t) throws InterruptedException {
    synchronized (this) {
      while (count == SIZE)
        this.wait(); // releases lock (must be transactionally correct!)
      // does not restart executing until RECOVERS the lock...

      data[count++] = t; // needs "atomicity" or mutual exclusion
      this.notify();
    }
  }

  public T take() throws InterruptedException {
    synchronized (this) {
      while (count == 0)
        this.wait();
      this.notify(); // Confusing, but SAFE position!!!
      T item = data[0];
      // Error was that I had passed "item" instead of "data" doh!?!?
      // item isn't even an array, but the signature of System.arraycopy takes Object, not "array"
      // (which is odd and news to me!)
      System.arraycopy(data, 1, data, 0, --count);
      return item;
    }
  }


  // "producer-consumer" model is now (mostly) called Actors

  public static void main(String[] args) {
    MyQueue<int[]> mq = new MyQueue<>();
//    BlockingQueue<int[]> mq = new ArrayBlockingQueue<>(10);
    Runnable prod = () -> {
      try {
        for (int i = 0; i < 10_000; i++) {
          int[] data = new int[2];
          data[0] = i;
          if (i < 500) Thread.sleep(10);
          data[1] = i;
          if (i == 5_000) data[0] = -1;
          mq.put(data);
          data = null;
        }
        System.out.println("Producer completed");
      } catch (InterruptedException ie) {
        System.out.println("That can't happen!");
      }
    };

    Runnable cons = () -> {
      try {
        for (int i = 0; i < 10_000; i++) {
          int[] data = mq.take();
          if (i > 9_500) Thread.sleep(10);
          if (data[0] != i || data[0] != data[1]) {
            System.out.println("ERROR at position " + i);
          }
        }
        System.out.println("Consumer completed...");
      } catch (InterruptedException ie) {
        System.out.println("That can't happen");
      }
    };

    new Thread(prod).start();
    new Thread(cons).start();
    System.out.println("Kicked off, main exiting...");
  }
}
