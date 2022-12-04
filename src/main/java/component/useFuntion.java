package component;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by yangwenjun on 2021/12/7 20:36
 */
public class useFuntion {



  //调用
  public static void main(String[] args) {
    //区分开optional和stream中的map,前者返回optional所以最后的orelse...可以总览
    //但是stream中的map返回的是stream，所以继续往下，需要进行一次判空 *****

    Integer integer = Optional.ofNullable(null)
        .map(x -> x.toString())
        .map(x -> x.toCharArray())
        .map(x -> x.length).orElse(1);
    System.out.println(integer); //1

    Integer integer1 = Stream.of(1,2,3).max((a, b) -> b - a).orElseGet(() -> 0);
    System.out.println(integer1);


  }

}
