import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.persistence.criteria.CriteriaBuilder.In;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.Builder;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.fileupload.MultipartStream.IllegalBoundaryException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFShape;

/**
 * Created by yangwenjun on 2021/9/6 16:54
 */
public class 函数式编程 {

  private static final String aa = "aaa";
  public static final int aaa = 1;

  public static void main(String[] args) {

    //Function  <--> T/Object --> Abstract/模板
    Function<String, String> function = (input) -> input + "abc";

    System.out.println(function.apply("test"));

    //连接动作起来
    Function<String, String> andFunc = function.andThen((secInput) -> secInput + "sec");
    System.out.println(andFunc.apply("test"));

    BiFunction<Integer,Integer,Integer> biFunction = (x,y) -> x * y;
    System.out.println(biFunction.apply(2, 3));

    Supplier<Map> supplier = () -> new HashMap();
    System.out.println(supplier.get().put("1", "2"));
    System.out.println(supplier.get().get("1"));


    //Collectors
    // 1. 解析定义的方式比如 function -> apply,apply是更高层的抽象，调用      2.为什么是biFunction ,因为任何多个都是两个两个完成的
    System.out.println(Lists.newArrayList("1", "2").stream()
        .reduce(BinaryOperator.maxBy(Comparator.naturalOrder())));
    System.out
        .println(Lists.newArrayList("1", "2").stream().reduce("test", (s, s2) -> s + s2));

    System.out.println(Lists.newArrayList("1", "2").stream()
        .reduce("test", (x, y) -> x + y, BinaryOperator.maxBy(Comparator.naturalOrder())));

    Map<String, String> mm = Maps.newHashMap();
    mm.put("a","a");mm.put("2","2");
    List<String> collect = mm.values().stream()
        .map(entry -> entry.toUpperCase()/*(k,v) -> k.toUpperCase()*/).collect(Collectors.toList());
//    mm.entrySet().stream().map((k,v) -> k+"aa").collect(Collectors.toList())
    mm.forEach((k,v) -> System.out.println(k+v));
    System.out.println(collect);


    //模块化 、 流程化
    //1.关于不知道如何直接跳转到lamrda表达式，可以先new对应的接口，再通过工具修改
    System.out.println(Lists.newArrayList("1", "2","3").stream()
        .collect(Collectors.toMap(String::toUpperCase, String::toLowerCase)));

    System.out.println(Lists.newArrayList("1", "2","3").stream()
        .collect(Collectors.toMap(String::toUpperCase, String::toLowerCase,
            new BinaryOperator<String>() {  //修改到lamda表达式   ==> 虽然是BinaryOperator 实际是内部类重写了该类，定义自己逻辑而不仅仅是maxby/minBy的default方式
              @Override
              public String apply(String s, String s2) {
                return s2 +"xxx" +s;
              }

              //重点重点重点: 从源码看这里的mergeFunction 和supplier 的作用   ==> 实际中解决map的聚合
            },() -> new HashMap<String, String>() {{
              put("1", "2");put("4","4");
            }})));
    System.out.println(Lists.newArrayList("1", "2","3").stream()
        .collect(Collectors.toMap(String::toUpperCase, String::toLowerCase,
            (BinaryOperator<String>) (s, s2) -> s + s2 +"xxx" /*或者这里判断获取对的属性塞进去 a.money + b.money*/,() -> new HashMap<String, String>() {{
              put("1", "2");
            }})));

    //自定义集合
    TreeSet<String> collect1 = Lists.newArrayList("1", "2", "3").stream()
        .collect(Collectors.toCollection(() -> new TreeSet<>()  /**ArrayList::new**/
            /*(Supplier<Collection<? super String>>) () -> new TreeSet<>())*/));

    //看着提示 -> 先忽略泛型  -> 向上抽象(分流程步骤) -> 一些含义不清楚可以看源码，参数是如何使用的，是否符合自己的预期，是否需要重新定义或者改造继承
    Map<String, Map<String, String>> collect2 = Lists.newArrayList("1", "2", "3").stream()
        .collect(Collectors
            .groupingBy((data) -> data.toUpperCase(),
                Collectors.toMap(Function.identity(), Function.identity())));
    System.out.println(collect2);





    //function 与 callable/runnale  与 future 关系
//    new Thread()
//    new Callable<>() {
//      @Override
//      public Object call() throws Exception {
//        System.out.println("===");
//        return null;
//      }
//    }.call();


  }




  enum AA {
    aa("bbb", "vvvv");

    AA(String name, String value) {
      this.name = name;
      this.value = value;
    }

    private String name;
    private String value;
  }

//@AllArgsConstructor

  class cc {

    public cc(int a, String b) {
      this.a = a;
      this.b = b;
    }

    private int a;
    private String b;
//
//  @Override
//  public boolean equals(Object o) {
//    if (this == o) {
//      return true;
//    }
//    if (o == null || getClass() != o.getClass()) {
//      return false;
//    }
//    cc cc = (cc) o;
//    return a == cc.a &&
//        Objects.equals(b, cc.b);
//  }
//
//  @Override
//  public int hashCode() {
//    return Objects.hash(a, b);
//  }
  }
}
