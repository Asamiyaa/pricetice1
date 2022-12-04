package boot;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by yangwenjun on 2022/10/18 19:48
 */
public class Function {

  /**
   * Funcion , 原函数
   */
  /**
   *  -> 相当于方法中的 {} 。。。现在简化了直接参数和操作
   */
  static Consumer<String> consumer = x -> x.compareTo("1"); //省略
  static Supplier<String> supplier = () ->  "1"; //省略了 return ,方法的定义
  static java.util.function.Function<String, String> function1 = x -> x + "function";
  static Predicate<String> predicate = x -> x.compareTo("1")>0;     //更灵活的定义了一个类，进行组装   Objects::noNull


  static BiConsumer<String,String> biConsumer = (x,y) -> System.out.println(x+"-->"+y);
  static BiFunction<String, String, Integer> biFunction = (x, y) -> x.compareTo(y);
  //biPredicate

  //IntConsumer、IntFunction、IntPredicate、IntSupplier、LongConsumer、LongFunction、LongPredicate、LongSupplier、DoubleConsumer、DoubleFunction、DoublePredicate、DoubleSupplier


  public static void main(String[] args) {
    consumer.accept("--->1==>");
    System.out.println(supplier.get());
    System.out.println(function1.apply("test"));

    System.out.println(java.util.function.Function.identity());
    System.out.println(function1.compose(function1).andThen(function1).apply("--->")); //先执行compose方法参数before中的apply方法，然后将执行结果传递给调用compose函数中的apply方法在执行

    System.out.println(predicate.and(predicate).or(predicate).test("3"));    // 更加拆分、模块化、组件化

    biConsumer.accept("aa", "bb");



    /**
     * static -- Function中的identity ; 类似于工具类   /  default -- 类似于抽象方法，或者模板
     */

/**
 * -----------------Stream-------高级函数---------------------
 */
    Stream.of(1,2,3,4).filter(x -> x > 1 ).map(x -> x.intValue()).forEach(x -> System.out.println(x));
    //----构建-------------predicate--返回true/false-------function--返回值-----consumer----无返回

//    Stream.of(1,2,3,4).filter(x -> x >1 && x >2) //可以将x >1 抽象成一个predicate
    Predicate<Integer> x1 = x -> x > 1;  Predicate<Integer> x2 = x -> x > 2;
    System.out.println(Stream.of(1, 2, 3, 4).filter(x1.or(x2)).count());   //更高级别抽象，比如图搜中的模板方法；    组件级别更底层(方法)、抽象级别更高

    /**
     * 将函数作为参数
     */
//    private static List<String> map( int[] ids, IntFunction<String> f )
//    {
//      return Arrays.stream(ids).mapToObj( f ).collect( Collectors.toList());
//    }

    /**
     * stream生成方式
     */
    System.out.println(IntStream.of(1, 2, 3).sequential().summaryStatistics().getMax());
    IntStream.generate(() -> 1);
    IntStream.range(1, 3);
//    IntStream.iterate(1,(x,y) -> x+y);

    /**
     * 高级函数
     * 	Intermediate --> Stream：
     * 	map (mapToInt,mapToLong flatMap 等)、 filter、 distinct、 sorted、 peek、 limit、 skip、 parallel、 sequential、 unordered
     * 	asDoubleStream ...、boxed、
     *
     * 	Terminal --> 有输出, 真实对象类型
     * 	forEach、 forEachOrdered、 toArray、 iterator、
     * 	min、 max、count、sum、average、
     * 	reduce、 collect、flatmap
     * 	anyMatch、 allMatch、noneMatch、
     * 	findFirst、 findAny、
     *
     *
     * 	Short-circuiting --> 快速返回：
     * 	anyMatch、 allMatch、 noneMatch、 findFirst、 findAny、 limit
     */

    /**
     * distinct 对比的时对象的equals和hash
     */
    Stream.of(1,3).distinct().skip(0).limit(1).map(v -> v+"tow").sorted().forEach(System.out::println);



    System.out.println("0000"+IntStream.of(1, 2, 3).summaryStatistics().getMax());
    System.out.println(IntStream.of(1, 2, 3).mapToObj(String::valueOf)); //int --> obj

    /**
     * 它可以将流中的元素转变成另外一个不同的对象，例如一个List，Set或Map。collect 接受入参为Collector（收集器），
     * 它由四个不同的操作组成：供应器（supplier）、累加器（accumulator）、组合器（combiner）和终止器（finisher）
     */
    StringBuffer collect1 = Stream.of("1", "2", "3")
        .collect(StringBuffer::new, (x, y) -> x.append(y), (x, y) -> x.append(y));

    String collect = Stream.of("1", "2", "3").collect(Collectors.joining(",", "a", "b"));
    System.out.println(collect);

    Stream.of(1,2,3).collect(Collectors.toList());
    String collect2 = Stream.of("1", "2", "3").collect(Collectors.joining());
    System.out.println("--->"+collect2);

    Map<String, String> collect3 = Stream.of("1", "2", "3")
        .collect(Collectors.toMap(x -> x, y -> y, (k1, k2) -> k1));
    System.out.println("--->"+collect3);

    /**
     * collectors 是对上面collect后面内容的抽象，使其更简化
     * Collector接口是使得collect操作强大的终极武器,对于绝大部分操作可以分解为旗下主要步骤,
     * 提供初始容器->加入元素到容器->并发下多容器聚合->对聚合后结果进行操作,
     * 同时Collector接口又提供了of静态方法帮助你最大化的定制自己的操作,官方也提供了Collectors这个类封装了大部分的常用收集操作.
     */

    /**
     * groupingBy是toMap的一种高级方式,弥补了toMap对值无法提供多元化的收集操作,比如对于返回Map<T,List<E>>这样的形式toMap就不是那么顺手,
     * 那么groupingBy的重点就是对Key和Value值的处理封装.分析如下代码,其中classifier是对key值的处理,mapFactory则是指定Map的容器具体类型,
     * downstream为对Value的收集操作,具体代码这里不做分析,无非是把值一个一个的put进指定容器
     *
     *
     * //原生形式
     *    Lists.<Person>newArrayList().stream()
     *         .collect(() -> new HashMap<Integer,List<Person>>(),
     *             (h, x) -> {
     *               List<Person> value = h.getOrDefault(x.getType(), Lists.newArrayList());
     *               value.add(x);
     *               h.put(x.getType(), value);
     *             },
     *             HashMap::putAll
     *         );
     *
     */
    System.out.println(Stream.of("1", "2", "3")
        .collect(Collectors.groupingBy(x -> x))); //function 作为classfy

    Stream.of("1", "2", "3")
        .collect(Collectors.groupingBy(String::toString, HashMap::new, Collectors.mapping(String::toString, Collectors.toSet())));

    Double collect4 = Stream.of("1", "2", "3")
        .collect(Collectors.averagingDouble(Double::valueOf));
    System.out.println(collect4);

    /**
     * 接着操作
     */
    List<String> collect5 = Stream.of("1", "2", "3")
        .collect(Collectors.collectingAndThen(Collectors.toList(), x -> {System.out.println(x); return x;}));
    System.out.println("=%%==>"+collect5);

    /**
     * collectors.max(Commpare....)
     */
    String ss = Stream.of("33", "2", "3").max((x, y) -> x.compareTo(y)).map(x -> x).toString();
    String ss1 = Stream.of("33", "2", "3").max(Comparator.naturalOrder()).orElse("1");
    System.out.println(ss1);

    Map<Boolean, List<String>> collect6 = Stream.of("1", "2", "3")
        .collect(Collectors.partitioningBy(x -> x.compareTo("1") > 0, Collectors.toList()));
    System.out.println(collect6);

    Integer collect7 = Stream.of(1, 2, 3)
        .collect(Collectors.reducing(0, x -> x + 1, (x, y) -> x + y));
    System.out.println(collect7);
    Integer reduce = Stream.of(1, 2, 3)
        .reduce(0, (x, y) -> x + y);//BinaryOperator<T> extends BiFunction<T,T,T>
    System.out.println(reduce);



    /**
     * 自定义  -- 思考聚合的步骤
     *    1.函数特点     (xx,xx) -> {function(xx)}
     *    2.聚合函数嗲用
     *    3.collector自定义
           *      //初始容器
           *      Supplier<A> supplier();
           *     //加入到容器操作
           *     BiConsumer<A, T> accumulator();
           *     //多容器聚合操作
           *     BinaryOperator<A> combiner();
           *     //聚合后的结果操作
           *     Function<A, R> finisher();
           *     //操作中便于优化的状态字段
           *     Set<Characteristics> characteristics();
     *
     *
     *     joining()
     *          容器: StringBuilder::new
     *          加入容器操作: StringBuilder::append
     *          多容器合并: r1.append(r2); return r1;
     *          聚合后的结果操作: StringBuilder::toString
     *          优化操作状态字段: CH_NOID
             *     public static Collector<CharSequence, ?, String> joining() {
             *         return new CollectorImpl<CharSequence, StringBuilder, String>(
             *                 StringBuilder::new, StringBuilder::append,
             *                 (r1, r2) -> { r1.append(r2); return r1; },
             *                 StringBuilder::toString, CH_NOID);
             *     }

     */
    /**
     * 执行顺序 , 每一步都是将所有的集合的数据处理了，再到下一个映射节点； 所以max,sum都是可以的。 和全局for...区分开
     */
    Stream.of("a1", "a2", "a3")
        .map(s -> s.substring(1)) // 对每个字符串元素从下标1位置开始截取
        .mapToInt(Integer::parseInt) // 转成 int 基础类型类型流
        .max() // 取最大值
        .ifPresent(System.out::println);

    /**
     *  * --但是 ，对于没有这种 ‘聚合’ 操作，则是垂直顺序输出，来优化性能，减少操作要素
     */

    Stream.of("d2", "a2", "b1", "b3", "c")
        .map(s -> {
          System.out.println("map: " + s);
          return s.toUpperCase(); // 转大写
        })
        .anyMatch(s -> {
          System.out.println("anyMatch: " + s);
          return s.startsWith("A"); // 过滤出以 A 为前缀的元素
        });

    /**
     * 注意：
     *    1.中间顺序  先扫秒链上有没有聚合操作(max , sum , sort ...)；没有垂直；这时候先filter ... 再map...可以减少数据量
     *    2.数据流复用问题,通过supplier包装 ; 通supplier包装来完成像一个提供器
     *    3.操作调用的是map.merge方法,该方法遇到value为null的情况会报npe,即使你使用的是hashMap可以接受null值,也照样报.搞不懂这里为什么这样设计.
     *      未指定冲突合并策略,也就是第三个参数BinaryOperator<U> mergeFunction时遇到重复的key会直接抛IllegalStateException,因此需要注意.
     *    4.当遇到复杂的判断时，需要将函数式展开写逻辑
     *
     */

    Stream<String> stream =
        Stream.of("d2", "a2", "b1", "b3", "c")
            .filter(s -> s.startsWith("a"));

    stream.anyMatch(s -> true);    // ok
//    stream.noneMatch(s -> true);   // exception


    Supplier<Stream<String>> streamSupplier =
        () -> Stream.of("d2", "a2", "b1", "b3", "c")
            .filter(s -> s.startsWith("a"));

    streamSupplier.get().anyMatch(s -> true);   // ok
    streamSupplier.get().noneMatch(s -> true);  // ok

    /**
     * 当遇到复杂的判断时，需要将函数式展开写逻辑   biOprator 比 biFuntion更多的提供了
     * 先说一个结论，非并行流下第三个参数没用。流中元素只有1个的情况下，即使指定parallel也不会走并行。
     * 使用三个参数的reduce，务必注意线程安全。
     */
    Optional<Integer> reduce1 = Stream.of(1, 2, 3).reduce((integer, integer2) -> {
      if (integer > 1) {
        return integer + integer2;
      } else {
        return integer2;
      }
    });
    System.out.println(reduce1);

    ArrayList reduce2 = Stream.of(1, 2, 3)
        .reduce(new ArrayList(), new BiFunction<ArrayList, Integer, ArrayList>() {    //累加器  从原来的的 x + y; 容器,如果没有new ArrayList,则就是简单类型
          @Override
          public ArrayList apply(ArrayList arrayList, Integer integer) {
            arrayList.add(integer);
            return arrayList;
          }
        }, new BinaryOperator<ArrayList>() {  //并行下才有意义，存在集合和集合分而治之的效果。注意安全，需要将入参中的list进行包装Collections.synchronizedList(a)
          @Override
          public ArrayList apply(ArrayList arrayList, ArrayList arrayList2) {
            return null;
          }
        });
    System.out.println(reduce2);

    /**
     * flatmap
     */
    List<List<Integer>> ll = new ArrayList<>();
    ll.add(Lists.newArrayList(1, 2, 3,9));
    List<Object> collect8 = ll.stream().flatMap(Collection::stream).collect(Collectors.toList());
    System.out.println(collect8);

    /**
     * 自定义
     *    结果容器提供者(supplier)
     *    累加元素(accumulator),创建函数实现增加元素至结果容器。
     *    合并器(combiner),在reduction序列操作中，提供者(supplier) 和 累加器(accumulator) 已经足够了，但为了能够实现并行操作，我们需要实现一个合并器。合并器(combiner)是定义两个结果如何合并的函数。
     *    最后一个转换,增加一个函数，其映射结果容器至我们需要的类型
     */
    Stream.of(1, 2, 3).collect(Collector.of(() -> new int[1],
        (result, item) -> result[0] += item.intValue(),
        (result1, result2) -> {
          result1[0] += result2[0];
          return result1;
        },
        total -> total[0]));



    /**
     * stream简化
*      							：：使用  --- 一个lambda表达式只调用一个已经存在的方法（不做其它）， 所以这才有了方法引用！
* 										以下是Java 8中方法引用的一些语法：
*
* 												静态方法引用（static method）语法：classname::methodname 例如：Person::getAge
* 												对象的实例方法引用语法：instancename::methodname 例如：System.out::println
* 												对象的超类方法引用语法： super::methodname
* 												类构造器引用语法： classname::new 例如：ArrayList::new
* 												数组构造器引用语法： typename[]::new 例如： String[]:new
     */


    /**
     * 结合Optional
     */
    IntStream.of(1, 2, 3).filter(x -> x > 1).findFirst().ifPresent(x -> System.out.println(x));

    String ss2 = Stream.of("33", "2", "3").max((x, y) -> x.compareTo(y)).map(x -> x).toString();
    String ss3 = Stream.of("33", "2", "3").max(Comparator.naturalOrder()).orElse("1");
//    Stream.of("33", "2", "3").sorted(Comparator.naturalOrder().reversed());
    System.out.println(ss1);

    Object o = Optional.empty().orElseGet(() -> "1");
    System.out.println("optinal --> "+o);

    /**
     * 借助orElse
     */
    System.out.println(Optional.of("1").orElse("2"));
    /**
     * 借助map  orelse是所有相同else的统一处理形式。。比如一连串get值中，如果任意为null,都会走到orelse分支
     * 一个orElse/orThrowabel就代表了所有操作过程中为null异常的情况，一旦遇到直接跳到这里给默认值或者异常，不会往下走了
     */
//    Optional.of("3").map(x -> x + "eee").ifPresent(x -> x);

    String x4 = Optional.of("3").map(x -> x + "eee").orElse("4");
    System.out.println(x4);

    Optional.ofNullable(null).orElseThrow(() -> new RuntimeException("eee"));

    /***
     *  // 第一种方式：存在空指针的风险，只要与一个对象为空就会空指针
     * 												//        String countryName = user.getAddress().getCountry().getCountryName();
     * 												//        System.out.println( "第一种方式：" + countryName );
     * 												//
     * 												//        // 第二种方式：各种if判断避免了空指针，但是if层级太深，代码冗长
     * 												//        if (user != null) {
     * 												//            Address address = user.getAddress();
     * 												//            if (address != null) {
     * 												//                Country country = address.getCountry();
     * 												//                if (country != null) {
     * 												//                    String couName = country.getCountryName();
     * 												//                    System.out.println( "第二种方式：" + couName );
     * 												//                }
     * 												//            }
     * 												//        }
     *
     *
     * 												        // 第三种方式：代码简洁，避免空指针,无论那一步为空都会返回自定义异常
     * 												        String countryNameEx = Optional.ofNullable( user )
     * 												                .map( User::getAddress )
     * 												                .map( Address::getCountry )
     * 												                .map( Country::getCountryName )
     * 												                .orElseThrow( () -> new RuntimeException( "第四部异常验证！" ) );
     * 												        System.out.println( "第四种方式：" + countryNameEx );
     */






    /**
     * Java8里面的java.util.Spliterator接口有什么用？  https://segmentfault.com/q/1010000007087438
     */

    /**
     * List compare
     */
    List<Integer> compare = Lists.newArrayList();
    compare.add(1);compare.add(2);
    compare.sort(Comparator.comparing(x -> x.toString()).thenComparing(x -> x.toString()));
  }

}

