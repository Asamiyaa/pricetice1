package boot;

import com.alibaba.fastjson.JSON;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by yangwenjun on 2022/10/21 16:04
 *
 * @Value 能力测试
 */
@Component
class TestValue {

  /**
   * 1.json作为配置,
   * 2.中间加个配置层，将从property和数据读取到的内容聚合
   *
   */

//    @Value("a.b:2")
//    private  String  name ;   //a.b:2

//    @Value("${a.b:2}")
//    private String  name ;    //2  如果找不到对应的属性就是默认值

  @Value("${a.b:2}")
  private String  name ;        //从application.property 对应的值   123
  /**
   * 支持基础类型自动转换，不一定非得是String
   *     整型：byte、short、int、long
       * 浮点型：float、double
       * 布尔型：boolean
       * 字符型：char
       * 相对应地提供了8种包装类：
       *
       * 整型：Byte、Short、Integer、Long
       * 浮点型：Float、Double
       * 布尔型：Boolean
       * 字符型：Character
   *
   *
   * 数组
   */
  @Value("${a.c:2}")
  private int age;
  @Value("${a.z:tom,ary,sum}")
  private String[] names;



  @Value("${a.b1:2}")
  private String  name1 ;        //传递json   {'a':'c','b':'bb','c':'a'}

//  @Value("${a.b1:2}")
//  private Tmp  name2 ;        //是否自动映射 ,不能, String不能转为对象，只能通过代码转


//  @Value("${susan.test.list}")  //配合[]会报错
//  List<String> list;

  @Value("#{'${susan.test1.list:}'.split(',')}")  //1.使用 #  2.定义成 a = 1,2,3  3.默认值是空List
      //包含一个空串 ，但是想要一个空集合
  List<String> list1;
  @Value("#{'${susan.test2.list:}'.empty?null:'${susan.test2.list:}'.split(',')}")
      //包含一个空串 ，但是想要一个空集合
      List<String> list2;
  @Value("#{${a.m}}")
  private Map nameMap;

  /**
   * 注入对象、及属性
   */
//  @Autowired
  @Value("#{myConfiguration}")
  private MyConfiguration configuration;
  @Value("#{myConfiguration.testProperty}")
  private String testProps;
  @Value("#{myConfiguration.test1}")
  private String test1;
  @Value("#{myConfiguration.getTest1()}")
  private String test2;

  /**
   * 静态注入
   */
  @Value("#{T(java.lang.Math).random()}")
  private double randomValue;


  /**
   * 逻辑运算
   */
  //    @Value("#{roleService.DEFAULT_AGE > 16 and roleService.roleName.equals('苏三')}")
//    private String operation1;
  @Value("#{${a.b}.equals('16') ? ${a.b}: '苏三' }")
  private String realRoleName;

  public void nameOut(){
      System.out.println("TestValue is "+name);
      System.out.println("TestValue1 is "+name1);
//      System.out.println("TestValue1 is "+name2);

    /**
     * json的传递更灵活
     */
    Tmp tmp = JSON.parseObject(name1, Tmp.class);
    System.out.println("tmp is "+ tmp);

    /**
     * 定义一个配置层，层中数据可以来自环境变量、k8s、数据库、而不仅仅是application.property
     * @Link: TestConfiguration 类
     */

    /**
     * 静态替换、静态可以作为工具灵活使用；借助了方法@Value注入
     */
    System.out.println(MyConfiguration.testProperty);

    System.out.println(age);                                      //99

    System.out.println(Arrays.toString(names));                   //[tom, ary, sum]

    /**
     * 从json --> 数组 --> 集合（List,Set）/ Map-类似于Json
     * $取值  #计算
     */
//    System.out.println(list); //报错
    System.out.println(list1);  //[10, 11, 12, 13]
    System.out.println(list2);  //null

    System.out.println(nameMap);

    /**
     * 通过@Value注入对象、及对象属性,配合static,  vs  从数据库查询值，每次 *  vs 保存static（安全）
     */

    System.out.println("====>"+configuration);
    System.out.println(testProps);
    System.out.println(test1);
    System.out.println(test2);

    /**
     * 注入静态类
     */
    System.out.println(randomValue);

    /**
     * 逻辑运算
     *     ${}  主要用于获取配置文件中的系统属性值。
     *     #{}  主要用于通过spring的EL表达式，获取bean的属性，或者调用bean的某个方法。还有调用类的静态常量和静态方法。
     */
    System.out.println(realRoleName);

    /**
     * 批量注入实现类
     */
    //批量注入类型
//    @Autowired
    List<A> a;   //注入所有得A,并发执行

    /**
     * 手动实现 Spring @Value 属性动态刷新
     *    https://blog.csdn.net/qq_41490274/article/details/125136130
     */


  }

  /**
   * 测试类
   */


  @Data
  @ToString
  static class Tmp{
    private String a;
    private String b;
    private String c;
  }


}

/**
 * todo: 关于配置的扩展    定义中间类 和  Configuration是有区别的
 */
@Component
@Getter
class MyConfiguration{

//  @Value("${a.b1:2}")
//  public static String testProperty;   //null

  public static String testProperty;
  @Value("${a.b1:2}")
  public String test1;

  @Value("${a.b1:2}")
  public void setTestProperty(String property){
    MyConfiguration.testProperty = property;
  }


  /**
   * 或者配合成员变量得get方法来复制
   */


}

@SpringBootTest(classes = App.class)
@RunWith(SpringRunner.class)
public class TestValueTest{

  @Autowired
  private TestValue testValue;

  @Test
  public void test(){
     testValue.nameOut();
  }


}


