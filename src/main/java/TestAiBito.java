import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestAiBito {

    public static void main(String[] args) {
        System.out.println("---");

        Supplier supplier = () -> "1212";
        System.out.println(supplier.get());

        BooleanSupplier supplier1 = () -> false;
        System.out.println(supplier1.getAsBoolean());

        MySupplier mySupplier = () -> "222";
        System.out.println(mySupplier.getMyTest());



        Predicate<String> predicate = (t) -> t.equals("abc");
        System.out.println(predicate.test("a"));

        BiPredicate<String, Long> biPredicate = (t, p) -> p.equals(Long.valueOf(t));
        System.out.println(biPredicate.test("12", 12L));

        ToDoubleFunction<String> toDoubleFunction = (t) -> Double.parseDouble(t);
        System.out.println(toDoubleFunction.applyAsDouble("555"));
        BiFunction<String,String,String> biFunction = (k,v) -> k + v;
        System.out.println(biFunction.apply("2", "3"));


        String[] array={"abc","ab","abcd"};
        Arrays.sort(array, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        }); // 方式一
        Arrays.sort(array,(a,b)->b.length()-a.length());//更简单的方式


        BiConsumer<String, String> biConsumer = (a, b) -> System.out.println(a + "|" + b);
        biConsumer.andThen((c,d) -> System.out.println(c+"---"+d)).accept("1", "2");


//        BinaryOperator
//                UnaryOperator

        /**
         * public static<T> Stream<T> iterate(final T seed, final UnaryOperator<T> f)
         * public static <T> Stream<T> concat(Stream<? extends T> a, Stream<? extends T> b)
         * public static<T> Builder<T> builder()
         * public static<T> Stream<T> of(T t)
         * public static<T> Stream<T> empty()
         * public static<T> Stream<T> generate(Supplier<T> s)
         */
        //怎么用？？/
        Stream.generate(() -> Lists.newArrayList("1","3","5","2","0"));
        Stream.of( Lists.newArrayList("1","3","5","2","0"));
        Optional<String> first = Lists.newArrayList("1", "3", "5", "2", "0").stream()
                .filter(StringUtils::isNotEmpty)
                .map(x -> x.toLowerCase(Locale.ROOT))
//                .flatMap(x -> x.toLowerCase(Locale.ROOT))
                .distinct()
                .sorted(String::compareTo)
                .limit(4)
                .skip(1)
                .peek(x -> x.getBytes(StandardCharsets.UTF_8))
//                        .count()
//                .findFirst();
//                .max(Comparator.naturalOrder());
//        .reduce("1",(a1,b1) -> a1 + b1);
                //啥也不做返回流，二次逻辑使用 ，结果对比，比如一个算法，另一个算法对同一组数据处理
//                .collect(Collectors.toList())
                /**
                 * java: 对于collect(java.util.stream.Collector<java.lang.Object,capture#1, 共 ?,java.util.Optional<T>>), 找不到合适的方法
                 *     方法 java.util.stream.Stream.<R>collect(java.util.function.Supplier<R>,java.util.function.BiConsumer<R,? super java.lang.String>,java.util.function.BiConsumer<R,R>)不适用
                 *       (无法推断类型变量 R
                 *         (实际参数列表和形式参数列表长度不同))
                 *     方法 java.util.stream.Stream.<R,A>collect(java.util.stream.Collector<? super java.lang.String,A,R>)不适用
                 *       (推断类型不符合上限
                 *         推断: java.lang.Object
                 *         上限: T,java.lang.Comparable<? super T>,java.lang.Object)
                 *
                 *         问题：为什么这里推断不出来
                 */

                .collect(Collectors.mapping((String x) -> x.toUpperCase(Locale.ROOT), Collectors.maxBy(Comparator.naturalOrder())));//todo:::  思考可能用在哪些场景下
        first.ifPresent(x -> x.getBytes(StandardCharsets.UTF_8));


        List<String> list = Arrays.asList("1", "3", "5", "2", "0");
//        Optional<String> max = list.stream()
//                .collect(Collectors.mapping(x -> x.toUpperCase(Locale.ROOT),
//                        Collectors.maxBy(Comparator.naturalOrder())));
////        Optional<String> collect = list.stream().map(x -> x.toUpperCase(Locale.ROOT)).collect(Collectors.maxBy(Comparator.naturalOrder()));
////        Optional<String> collect1 = list.stream().collect(Collectors.mapping((String x) -> x.toUpperCase(Locale.ROOT),Collectors.maxBy(Comparator.naturalOrder())));
////        System.out.println(max.orElse("No maximum found"));
//        System.out.println(collect.get());
//        System.out.println(collect1);

//        System.out.println(Stream.of(null).findFirst());
//        Stream.builder().build()

//        Stream<Double> stream = Stream.iterate(2.0, decimal -> decimal > 0.25, decimal -> decimal / 2);

//        Stream<Integer> stream = Stream.iterate(1, integer -> integer + 1).collect(Collectors.groupingBy());
//        stream.forEach(System.out::println);

        Converter<String, String> converter = CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.UPPER_CAMEL);
        System.out.println(converter.convert("a_bb"));

    }


}


