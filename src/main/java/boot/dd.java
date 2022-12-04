package boot;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by yangwenjun on 2022/10/29 00:27
 */
public class dd {

  public static void main(String[] args) {
    String a = "abcdefdfdfdfdfdsfdfdfdfdfdfddfeeeeeeee";
    System.out.println(StringUtils.overlay(a, "****", 10, a.length()-10));
    System.out.println(a);
  }
}
