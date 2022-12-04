package boot;

import java.util.function.Function;
import org.springframework.util.Assert;

/**
 * Created by yangwenjun on 2022/10/28 14:39
 */
public class TestMapping {

  /** 0.字段设置是否合理
   *  1.convert <source,desc>  对应的函数式
   *  2. dto 转换   beancopy
   *  3.
   */
  public static void main(String[] args) {

    ConvertImpl convert = new ConvertImpl();
    String abc = convert.convert("abc", (a) -> a.toUpperCase());
    System.out.println(abc);    //ABC




  }

}

/**
 *  抽取变量
 *      1.泛型
 *      2.function 抽取行为
 */
interface Convert<SOURCE,DESC>{

  DESC convert(SOURCE source, Function<SOURCE, DESC> function);
}


class ConvertImpl implements Convert<String,String> {

  @Override
  public String convert(String s, Function<String, String> function) {
    Assert.notNull(s,"not null");
    String ret = function.apply(s);
    Assert.notNull(ret,"not null");
    return ret;
  }
}