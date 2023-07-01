package boot.spring;

/**
 * 解析 string , 使其成为java认识的，操作符
 */
public class ASpel {


  public static void main(String[] args) {
    parse("1+2");
  }

  private static void parse(String s) {
    //拆分器
    //解析器
    String[] v = s.split("\\+");
    System.out.println(Integer.valueOf(v[0]) + Integer.valueOf(v[1]));

  }
}
