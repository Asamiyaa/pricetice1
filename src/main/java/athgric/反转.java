package athgric;

/**
 * Created by yangwenjun on 2023/5/9 15:36
 *
 * 给你一个 32 位的有符号整数 x ，返回将 x 中的数字部分反转后的结果。
 *
 * 如果反转后整数超过 32 位的有符号整数的范围 [−231,  231 − 1] ，就返回 0。
 *
 * 假设环境不允许存储 64 位整数（有符号或无符号）。
 *
 */
public class 反转 {


  public static void main(String[] args) {
     doSome(-123232L);
  }

  private static void doSome(Long input) {
    /**
     * 只能转String?
     */
    String inputStr = String.valueOf(input);
    StringBuffer stringBuffer = new StringBuffer();

    if (input > 0) {
      for (int i = inputStr.length() - 1; i >= 0; i--) {
        stringBuffer.append(inputStr.charAt(i));
      }

    } else {
      for (int i = inputStr.length() -1; i >= 1; i--) {
        stringBuffer.append(inputStr.charAt(i));
      }
      stringBuffer = new StringBuffer("-").append(stringBuffer);
    }

    /**
     * long比较大小
     */
    String str = stringBuffer.toString();
    long ll = Long.parseLong(str);
    int end = 2 ^ 31 - 1;
    int begin = -2 ^ 31;
    if (ll > end && ll < begin) {
      System.out.println("0");
    }

    System.out.println(ll);

  }





  /**
   * 长度越界
   */
//  private static void doSome(Object input) {
//
//
//    if (String.valueOf(String.valueOf(input).charAt(0)).equals("-")) {
//
//    } else {
//
//    }
//
//  2^31
//
//
//
//  }

}
