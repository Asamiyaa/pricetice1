package boot.spring;

/**
 * 1.一个方法前后拦截越多，那么他的能力增强越多   beanPostProcess
 */
public class ACore {


  public static void main(String[] args) {
    pp1();
  }

  private static void pp1() {
    System.out.println("before");
    pp2();
    System.out.println("after");
  }

  private static void pp2() {
    System.out.println("pp");
  }

}
