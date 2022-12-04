package boot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by yangwenjun on 2022/10/25 20:32
 *
 *
 *      1.配置的多维度、关于配置的扩展。定义一个配置层，层中数据可以来自环境变量、k8s、数据库、而不仅仅是application.property
 *      2.配置动态   动态配置：https://www.toutiao.com/article/7153429993425748487/?log_from=6855b3da10036_1666445071743
 *
 *
 *      1.source
 */
//@Configuration
//@ConfigurationProperties(prefix = "testp")
class TestConfigurationTest {

  /**
   * 属性
   */
  private String a;
  private String b;

  public TestConfigurationTest() {
  }

  public void setA(String a) {
    this.a = a;
  }

  public void setB(String b) {
    this.b = b;
  }

  public boolean equals(final Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof TestConfigurationTest)) {
      return false;
    }
    final TestConfigurationTest other = (TestConfigurationTest) o;
    if (!other.canEqual((Object) this)) {
      return false;
    }
    final Object this$a = this.getA();
    final Object other$a = other.getA();
    if (this$a == null ? other$a != null : !this$a.equals(other$a)) {
      return false;
    }
    final Object this$b = this.getB();
    final Object other$b = other.getB();
    if (this$b == null ? other$b != null : !this$b.equals(other$b)) {
      return false;
    }
    return true;
  }

  protected boolean canEqual(final Object other) {
    return other instanceof TestConfigurationTest;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $a = this.getA();
    result = result * PRIME + ($a == null ? 43 : $a.hashCode());
    final Object $b = this.getB();
    result = result * PRIME + ($b == null ? 43 : $b.hashCode());
    return result;
  }

  public String toString() {
    return "TestConfigurationTest(a=" + this.getA() + ", b=" + this.getB() + ")";
  }

  public String getA() {
    return this.a;
  }

  public String getB() {
    return this.b;
  }
//  private
//  @Configuration
//  @ConfigurationProperties(prefix = "susan.test")
//  @Data
//  public class MyConfig {
//    private String userName;
//  }


//  @PropertySource()


  //注入list
//  @Configuration
//  @ConfigurationProperties(prefix = "susan.test")
//  @Data
//  public class MyConfig {
//    private List<String> list;
//  }


}

/**
 * 1.从前面的配置 --> property工厂
 * 2.从不同的地方来配置
 */
@Configuration
class ConfigrationFatory{

  @Bean
  @ConfigurationProperties("testp")
  public TestConfigurationTest testConfigurationTest(){
    return new TestConfigurationTest();
  }



}

/**
 * 从spring -> 组件化  --> 通过static变量来工具化
 *
 */
@Component
class SpringHolder implements ApplicationContextAware{
  static ApplicationContext applicationContext;
  @Override
  public void setApplicationContext(ApplicationContext a) throws BeansException {
    applicationContext = a;
  }

  public static <T>T getValue(Class<T> cls){
    return applicationContext.getBean(cls);
  }
}


/**
 *  常量
 */
class Constant{
  /**
   * 枚举
   *      1.配合内部类 和 枚举属性
   */
  enum testEnum{
    A(1,"a"),B(2,"b");
    private int age;
    private String name;


    testEnum(int i, String b) {
    }
  }

  /**
   * 常量
   */
  public static final String cons = "abc";

  /**
   * 从数据库 --> 缓存(线程、本地、redis)
   */

  private static TestConfigurationTest testConfigurationTest;

  /**
   * 从配置
   *
   *      1.spring -- 工具类化
   *            问题：为什么属性值没有赋值？？  https://www.jianshu.com/p/2854d8984dfc
   *                  这里的启动的是spring容器加载，所以这里new 没有获取到对应的值   ---    不行
   *
   *        换一种
   */
  public static AnnotationConfigApplicationContext getSpringHolder() {
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
        ConfigrationFatory.class);
    /**
     * 从哪里加载的
     */
    /**
     * 单例、懒加载
     */
   return context;
  }

  static TestConfigurationTest tt = getSpringHolder()
      .getBean(TestConfigurationTest.class);

  public static String aaa = getSpringHolder()
      .getBean("testConfigurationTest", TestConfigurationTest.class).getA() + "===>>>>";

  /**
   * 如何动静结合
   *          参考：从spring -> 组件化  --> 通过static变量来工具化    上面
   */

  /**
   * 从不同environment中获取数据
   *      0.从property   不同环境
   *      1.多数据源判断  --- update动态刷新   --- key / value- json/ -- 业务如何处理  -- 同一环境开关
   *              dataSource.select
   *      2.从部署环境、system、系统文件 。 配合shell、cmd等执行方式  k8s
   */




  /**
   * 建表脚本、从不同的k8s中读取，并通过
   */




}


@SpringBootTest(classes = App.class)
@RunWith(SpringRunner.class)
public class TestConfiguration{

  @Autowired
  private TestConfigurationTest testConfiguration;

  @Test
  public void test(){
//    TestConfiguration
    System.out.println(testConfiguration.getA());
    System.out.println(testConfiguration.getB());

//    System.out.println(Constant.aaa);
//    System.out.println(Constant.tt);

    TestConfigurationTest value = SpringHolder.getValue(TestConfigurationTest.class);
    System.out.println(value);

    /**
     * 通过@ConditonalOnProperty来判断多来源加载
     *      @ConditionalOnClass(此类存在配置生效)
     *      @ConditionalOnMissingClass（此类不存在配置生效）
     *      @ConditionalOnBean
     *      @ConditionalOnMissingBean
     *      @ConditonalOnProperty（某个参数）     判断有这个配置才注入这个类
     *      @ConditonalOnResource（某个资源）
     *      @ConditonalOnWebApplication
     *      @ConditionalOnNotWebApplication
     *      @ConditionalOnExpression
     */
    c value1 = SpringHolder.getValue(c.class);
    value1.test();

    /**
     * 自定义条件注解
     *    Condition 类以及 ConditionEvaluator 相关，它的主要作用可以概括为：
     *    判断 bean 实例注册的条件是否满足，我们可以通过实现 Condition 接口来定义 bean 注册的条件
     *
     *    根据是否满足某个特定条件来决定是否创建某个特定的Bean。
     */

    d value2 = SpringHolder.getValue(d.class);
    value2.test();

    /**
     *
     */


  }

}

@ConditionalOnExpression("'${a.b}'.equals('123') ? true:false")
@Component
//自定义其他注解
class c {
    public void test(){
      System.out.println("===>>>conditionalTest");
    }
}


@myCondition(condition = "{con}")
@Component
class d {
  public void test(){
    System.out.println("===>>>conTest");
  }
}
