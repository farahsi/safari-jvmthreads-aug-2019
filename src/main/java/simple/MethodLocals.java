package simple;

public class MethodLocals {
  public static void showNumbers(boolean again) {
    int i = 0;
    for (; i < 10; i++) {
      System.out.println("> " + i);
      if (again && i == 5) {
        showNumbers(false);
      }
    }
  }
  public static void main(String[] args) {
    showNumbers(true);
//    showNumbers();
  }
}
