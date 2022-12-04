package component;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yangwenjun on 2021/10/21 20:46
 */

@Component
@RestController
public class Test {


  @Retryable(maxAttempts = 6, value = Exception.class, backoff = @Backoff(delay = 5000L, multiplier = 1))
  @Recover
  public void abc() throws Exception {

    System.out.println(new Date()+"======");
//    int i = 0 ;
    for (int j = 0; j < 4; j++) {
      throw new Exception("abc");
    }

  }
  @RequestMapping("/abc")
  public void abc2() throws Exception {
    System.out.println("abc");
    System.out.println("abc");System.out.println("abc");
    System.out.println("abc");

  }

//  @Data
@Setter
@Getter
  @AllArgsConstructor
@NoArgsConstructor
//  @ToString
  public static class A {
    String age;
  }

//  @Data
@Setter
@Getter
  @AllArgsConstructor
  @NoArgsConstructor
//  @ToString
  public static class B {
    String name;
    A a;


  }

  /**
   * 1.复制 == 浅拷贝
   * 2.实现clone接口并重写方法   |  实现 serializable接口  == 深拷贝
   *      每一个内部都要.clone，嵌套麻烦
   * 3.Apache、Spring、Cglib、json 的beancopy效率对比  == 深拷贝
   *      基于名称反射获取 name1和原文中name无法匹配，无法赋值
   *      类型是否校验  这里的integer但是原文中是string,没有赋值
   *      命名和类型必须都匹配 ,才能拷贝全 。相同类型不用考虑一定可以； 拷贝是可以不同类型的,基本类型会赋值,但属性不同类型就不行了,因为是反射
   */

//  @Data
  @Setter
  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
//  @ToString         //toString展示详情 或者 @data中包含 ---> JSON.toJsonString来完成嵌套对象的输出
  static class C implements Cloneable{
    @JSONField
    String name;    //基于名称反射获取 name1和原文中name无法匹配，无法赋值
    A a;   //类型是否校验  这里的integer但是原文中是string,没有赋值
            // ===》》》》 命名和类型必须都匹配 。。 拷贝一定是可以不同类型的。。但属性不同类型就不行了

    @Override
    public C clone() throws CloneNotSupportedException {
      return (C)super.clone();
    }
  }


  public static void main(String[] args) throws CloneNotSupportedException {

      TestFunction testFunction = new TestFuntionImpl();
       System.out.println(testFunction.testR());

       TestFunction testFunction1 = new TestFunction() {
         @Override
         public String testR() {
           return "null";
         }
       };
      System.out.println(testFunction1.testR());


      //传递行为

















//
//    System.out.println(Stream.generate(() -> "a").limit(10).collect(Collectors.toList()));
//
//    IntStream.range(1, 4).reduce(10, (a, b) -> a + b);
//
//
//    Stream<Integer> stream = Stream.of(25, 50, 75, 100, 125, 150);
//    // true for stream element 50
//    Map<Boolean, List<Integer>> m = stream.collect(Collectors.partitioningBy(a -> a > 50));
//    System.out.println("Stream ="+ m);
//
//    System.out.println(m.get(true));

//    ExpressionParser parser = new SpelExpressionParser();
//    Expression expression = parser.parseExpression("'SpEL'.concat(' thinking')");
//    String result = (String) expression.getValue();
//    System.out.println(result);
//
//    User user = new User();
//    user.setName("valuewithTime");
//    user.setAge(23);
//    ExpressionParser parser = new SpelExpressionParser();
//    EvaluationContext context=new StandardEvaluationContext(user);
//    Expression expression = parser.parseExpression("name");
//    String result = expression.getValue(context,String.class);
//
//


    //




    //
////    System.out.println(Stream.of().findFirst().get());  //Exception in thread "main" java.util.NoSuchElementException: No value present
//    System.out.println(Stream.of(1).findFirst().map(x -> x + 1).orElse(111));
//    // orelse两层含义 包含了get   ***
//    System.out.println(Stream.of().findFirst().map(x -> x+"1").orElse("111"));
////    orelse是所有false分支的统一输出，不用每层都orelse....
//    //思路：所有的optional是null的时候都走到了orelse分支，所以是统一的意外分支，对于不同意外取不同值是不通的
//    System.out.println(Stream.of(1).findFirst().map(x -> x+"1").filter(x -> x.equals("11")).orElse("111"));
//
//    System.out.println(null + "1");
//
//
//    //进来就包装对象，orelse ..,后面就...链式调用
//    B b = new B();
//
////    Optional.of(b).map(x -> x.getA().age).orElse("this is return");
//    //Exception in thread "main" java.lang.NullPointerException  + 内部进行了.age造成空指针
//
//    String this_is_return = Optional.of(b).map(x -> x.getA()).map(x -> x.getAge())
//        .orElse("this is return");
//    String this_is_return1 = Optional.ofNullable(b).map(x -> x.getA()).map(x -> x.getAge())
//        .orElse(default1());
//    String this_is_return2 = Optional.ofNullable(b).map(x -> x.getA()).map(x -> x.getAge())
//        .orElseGet(() -> "abc");  //todo:为什么可以this::xxx方法 如何等价于supplier
////.orElseGet((x) -> "abc"); expect 0 but 1;
//    System.out.println(this_is_return);
//    System.out.println(this_is_return1);
//    System.out.println(this_is_return2);;
//
//    B b1 = new B();
//    A a = new A();
//    b1.setA(a);
//    //如果单if , 不满足不管是否orelse不做 , map 必须返回.这值得就是else啥也不做
//    //不用嵌套，表示else啥也不做
////    String age = Optional.ofNullable(b).map(x -> x.getA()).map(x -> {
////      x.setAge("1");
////      return x;
////    }).get().getAge();//报错：not method
//
//    //-------------
//
//
//    String age1 = Optional.ofNullable(b).map(x -> x.getA()).map(x -> {
//      x.setAge("1");
//      return x;
//    }).orElseGet(A::new).getAge(); //必须要有orelseget...，这样才能连接起来后面的getage...所有向get --> orelse...get...
//
//    System.out.println(age1);
//
//    String age12 = Optional.ofNullable(b1).map(x -> x.getA()).map(x -> {
//      x.setAge("1");
//      return x;
//    }).orElseGet(A::new).getAge(); //必须要有orelseget...，*********这样才能连接起来后面的getage...所有向get --> orelse...get...
//
//    System.out.println(age12);
//
//    //orElseGet(A::new)  这里newdui应对象。。。 或者null
//
//
//    //---------filter-----
//    //filter也是后续判断ispresent ;才会后续执行  否则走orelse....
//
//
//
//    //suplier  consumer
//    //一个方法就是这些。只是参数不同！？
//
//
//
//    //stream -- optional  findfirst终结操作，返回optional,意味可能存在，可能不在
//    System.out.println(Stream.of("1").filter(x -> x.equals("1")).findFirst().orElse("bbb"));
////    System.out.println(Stream.of(null).filter(x -> x.equals("1")).findFirst().orElse("a")); //空指针
////    System.out.println(Stream.of(null).filter(Optional::isPresent)filter(x -> x.equals("1")).findFirst().orElse("a"));
//    //是否需要校验null,调用stream
//    List ll = null;
////    System.out.println(ll.stream().collect(Collectors.toList()));
//    B b2 = new B();
////    A a2 = new A();
////    b2.setA(a2);
//    //stream判空
////    List<String> collect = Stream.of(b2).map(x -> x.getA()).map(x -> x.getAge())
////        .collect(Collectors.toList());
////    System.out.println(collect);
//    //如何list为空
//    B b3 = Optional.ofNullable(b2).orElseGet(B::new);
//    System.out.println(
//        Stream.of(b3).filter(Objects::nonNull).map(x -> x.getA()).filter(Objects::nonNull).map(x -> x.getAge())
//            .collect(Collectors.toList()));
////上面存在A,所以可以正常走下来  第一步的orelesegeth进来后的filter起到对list即元素的判断  ===》  CollectionUtils.isempty 入口直接判断
//
//    //****  每个map后是否都要单独判断是否为null？ --> 需要即map映射后需要加一filter才能再次map   ****
//    //从3个地方校验  入口 ， 进来元素 ， map后
//
//
//
////    Stream.of()
//
//
//    //这么写正常了
//    //JsonDataT(id=3, name=课程, sysProperty=[SysProperty(name=java课程, info=[js, sql, rabbitmq]), SysProperty(name=spring课程, info=[spring简介, spring注解, spring配置文件]), SysProperty(name=python课程, info=[语句, 语法])])
//
//
//
//
//
////    //copy -- json - 序列化
////    B b = new B("1",new A("1"));
////
////    B b1 = new B();
////
////    BeanUtils.copyProperties(b,b1);
////
////    System.out.println(b1);
////
////
////    C c = new C();
////
////    BeanUtils.copyProperties(b,c);
////
////    System.out.println(c);
////    System.out.println(JSON.toJSONString(c));  //依赖getxx方法
////
////    String s = "[{\"a\":{\"age\":\"1\"},\"name\":\"1\"},{\"a\":{\"age\":\"1\"},\"name\":\"1\"}]";
////    String slist = "{\"a\":{\"age\":\"1\"},\"name\":\"1\"}";
////    System.out.println(JSON.parseArray(s).toJavaList(C.class));
//////    System.out.println(JSON.parseArray(s,A.class));???
////
////    C c2 = c;
////    boolean s1 = c2 == c;
////
////    C c3 = c.clone();       //implements Cloneable  标识接口  1.需要实现接口  2.有多少嵌套都要重写
////    boolean s2 = c3 == c;
////
////
////    System.out.println("=="+ s1 );
////    System.out.println("=="+ s2 );
////
//////    System.out.println("==>"+JSON.parseArray(slist).get(0)); []
////    System.out.println(JSON.parseObject(slist).fluentPut("double", "1.343").getFloat("double"));//
//////    slist = JSON.parseObject(slist).fluentPut("double", "1.343");
////    System.out.println("==>"+JSON.parse(slist));
////
////    /**
////     * Json - jsonarray - 二者互通操作 list - stream
////     *                  - map Map mm = JSON.parseObject(slist);  map = jsonobject  map也可以直接转为 string JSON.toJsonString....
////     *
////     *fluent和getFlloat   JSON.parseObject(slist).fluentPut("double", "1.343").getFloat("double")
////     * fluent可以添加对象 ，结合stream map进行过程操作
////     *
////     *                  */
//////    JSONArray.parseArray(JSON.parseArray(""), C.class);
//////    JSONArray.pa
////
//////    JSONObject jsonObject = JSON.parseObject(slist, SortField);
////    System.out.println("===>"+JSONPath.containsValue(c,"$.name","1")); //    System.out.println(jsonObject);
////
////
////
//////fluent
////
//////    System.out.println("this is a test");
//////    System.out.println(Base64.getEncoder().encodeToString("this is a test".getBytes()));
//////    System.out.println(Base64.getEncoder().encodeToString("this is a test".getBytes(
//////        Charset.defaultCharset())));
//////    byte[] bytes = "this is a test".getBytes(
//////        Charset.defaultCharset());
//////    System.out.println(new String(Base64.getDecoder().decode(Base64.getEncoder().encodeToString("this is a test".getBytes()))));
//  }
//
//  static String default1(){
//    return "defalutl --->  this is return";
//  }
//   String default2(){
//    return "defalut2 --->  this is return";
  }
}
