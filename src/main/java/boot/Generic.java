package boot;

import com.google.common.collect.Lists;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Created by yangwenjun on 2022/10/20 19:34
 */
public class Generic {

  /**
   *  1.泛型是对类型的向上抽象 ; 函数是对方法的向上抽象,将行为传递
   *  2.使用全局的Object灵活性最高，但失去了约束,逻辑上，每一步都需要强转
   *  3.泛型擦除  List<Object>不是List<String>的父类
   *  4.
   */
  public static void main(String[] args) {

    TestA<ITest> iTestTestA = new TestA<>();
    iTestTestA.test(() -> {
      System.out.println("--->test");
    });

    String s = "s";
    iTestTestA.test2(s);

    /**
     * 泛型和function结合    3applyBeforeapplyAfter
     */
    iTestTestA.test3("3", new Function<String, String>() {
      @Override
      public String apply(String string) {
        return string + "applyBefore";
      }
    }, new Function<String, Void>() {
      @Override
      public Void apply(String string) {
        System.out.println(string + "applyAfter");
        return null;
      }
    });

    /**
     * 多泛型参数配合,确保映射联系就好 <T,E,K/V>
     *
     *   条件 - 多路召回 - 打分 - 去重 - 排序 - 输出 。。 局部搜/万物搜/多模搜/渐进搜
     *
     * public abstract class Test<QUERY, CONDITION, RESPONSE, RESULT>
     *        private ppp1<QUERY, CONDITION> pppppp;
     * 			  private List<ppp2<CONDITION, RESPONSE>> dddd;
     *
     *
     * 			  通过子类构造实例化对应的一套组件    *****
     *
     *
     * 			  实例化： new T()  不行 , 需要 传入Class , t = clazz.newInstance();
     */


    /**
     * 通配符 上下限细化约束
     */
    ArrayList<Object> l1 = Lists.newArrayList();
    ArrayList<String> l2 = Lists.newArrayList();
//    iTestTestA.test4(l2,l1);
    iTestTestA.test4(l1,l2);


    /**
     * 与反射结合,更高的维度
     *
     *      1.Class<T>
     *      2.List<T>
     *      3.interface<T>   abstract<T>
     *
     *
     *     1.泛型出现之后，也就扩充了数据类型。从只有原始类型扩充了参数化类型、类型变量类型、泛型数组类型，也就是Type的子接口。
     *       是为了程序的扩展性，最终引入了Type接口作为Class，ParameterizedType，GenericArrayType，TypeVariable和WildcardType这几种类型的总的父接口
     *       Class（类）是Java对现实对象的抽象，而Type是对Java语言对象的抽象。 ***
     *               0.raw type：原始类型，对应Class
     *               1.ParameterizedType： 表示一种参数化的类型，比如Collection，即普通的泛型。
* 									 2.TypeVariable：是各种类型变量的公共父接口，就是泛型里面的类似T、E。
* 									 3.GenericArrayType：表示一种元素类型是参数化类型或者类型变量的数组类型，比如List<>[]，T[]这种。
* 									 4.WildcardType：代表一种通配符类型表达式，类似? super T这样的通配符表达式。
     *
     *
     *
     *
     *     2.Type[] getActualTypeArguments()     简单来说就是获得<>里的类型参数的类型，可能有多个类型参数，例如Map<K, V>，也可能没有类型参数
     *       Type getRawType()                   返回声明此 Type 的类或接口，简单来说就是返回<>前面那个类型，例如Map<K ,V>返回的是Map
     *
     *
     *       参考：https://www.cnblogs.com/baiqiantao/p/7460580.html
     *
     *
     *       1.需要实例化对应的类型，比如基本类型支持、转换； 映射框架
     *       2.通过.getMethod -- Method获取对应泛型
     *
     *
     *
     */
    Class<TestA> testAClass = TestA.class;
    Type[] genericInterfaces = testAClass.getGenericInterfaces();
    Stream.of(genericInterfaces).forEach(System.out::println); //无

    TypeVariable<Class<TestA>>[] typeParameters = testAClass.getTypeParameters();
    Stream.of(typeParameters).forEach(System.out::println);  //T

    List<String> ll = new ArrayList<>();
    TypeVariable<? extends Class<? extends List>>[] typeParameters1 = ll.getClass()
        .getTypeParameters();
    Stream.of(typeParameters1).forEach(System.out::println); // E

    String typeName = ll.getClass().getGenericSuperclass().getTypeName();
    System.out.println(typeName); //java.util.AbstractList<E>

    Type[] actualTypeArguments = ((ParameterizedType) ll.getClass().getGenericSuperclass())
        .getActualTypeArguments();
    Stream.of(actualTypeArguments).forEach(System.out::println);

  }


}


class TestA<T extends ITest> {

  /**
   * 作为属性灵活使用
   *    1.配合Function
   */
  T t ;
  public void test(T t){
    t.test();
  }

  /**
   * 作为参数
   *    1.倒着定义、先写常规 -> 抽象 -> 定义
   *
   */
  public <T1> void test2(T1 t){
    System.out.println(t);
  }


  public <T2> void test3(T2 t2 , Function<T2,T2> before , Function<T2,Void> after){
    Function function = before.andThen(after);
    function.apply(t2);
  }

  /**
   * 从src -> dst , dst范围比src大，也就是只能向上转型，所以 ? super E
   */
  public <E> void test4(List<? super E> dst, List<E> src){
    for (E e : src) {
      dst.add(e);
    }
  }
}

/**
 *  定义上下限
 */
interface ITest{
  void test();
}