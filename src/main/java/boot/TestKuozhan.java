package boot;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by yangwenjun on 2022/11/1 11:15
 */
@Component
 class TestKuozhan1 implements ApplicationContextAware , BeanFactoryAware {
/**
 * 如何从spring扩展中获取数据
 *    *          除了提供BeanFactory所支持的所有功能外ApplicationContext还有额外的功能
 *    *          默认初始化所有的Singleton，也可以通过配置取消预初始化。
 *    *          继承MessageSource，因此支持国际化。
 *    *          资源访问，比如访问URL和文件。
 *    *          事件机制。
 *    *          同时加载多个配置文件。
 *    *          以声明式方式启动并创建Spring容器。
 *
 *
 *    starter
 *    spi
 *
 */

/**
 *  初始化方式：
       *  应用场景：在spring容器启动完成后做一些初始化的动作（如加载数据等），常见的办法有：
       *
       * 定义静态常量，随着类的生命周期加载而提前加载（这种方式可能对于工作经验较少的伙伴，选择是最多的）；
       * 实现CommandLineRunner接口；容器启动之后，加载实现类的逻辑资源，已达到完成资源初始化的任务；
       * @PostConstruct；在具体Bean的实例化过程中执行，@PostConstruct注解的方法，会在构造方法之后执行；
       * 　　　　加载顺序为：Constructor > @Autowired > @PostConstruct > 静态方法；
       *
       * 　　　　特点：
       *
       * 只有一个非静态方法能使用此注解
       * 被注解的方法不得有任何参数
       * 被注解的方法返回值必须为void
       * 被注解方法不得抛出已检查异常
       * 此方法只会被执行一次
       * 　　4.实现InitializingBean接口；重写afterPropertiesSet()方法；
 */


  /**
   * spring : 从Spring的两个流程去介绍如何扩展，一个是容器初始化流程，一个是Bean的创建流程。
   *         Aware
   */
  public ApplicationContext applicationContext1;
  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    applicationContext1 = applicationContext;
  }

  public void testAA(){
    Object configrationFatory = applicationContext1.getBean("configrationFatory");
    System.out.println(configrationFatory);

    //获取对应的类型对象
    Map<String, ConfigrationFatory> beansOfType = applicationContext1
        .getBeansOfType(ConfigrationFatory.class);
    System.out.println(beansOfType);

    //获取对应的注解对象
    Map<String, Object> beansWithAnnotation = applicationContext1
        .getBeansWithAnnotation(myCondition.class);
    System.out.println(beansWithAnnotation);

    Environment environment = applicationContext1.getEnvironment();
    System.out.println("00000000000==>>>"+environment);

    ConfigrationFatory bean = beanFactory1.getBean(ConfigrationFatory.class);
    System.out.println(bean);

  }


  public BeanFactory beanFactory1;
  @Override
  public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
    beanFactory1 = beanFactory;
  }

  /**
   * 1.InitialingBean和DisposableBean
   */





  /**
   * @Import  融合不同框架
   *
   */
  /**
   * schema扩展
   */





}

/**
 * 自定义starter
 */
@Component
@Order(15)
class MyStarter implements ApplicationRunner{

//  @XxlJob("myStarter")
  public void test(){
    System.out.println("-----------");
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    System.out.println("--xxljob---stater ---");
    test();
    System.out.println("--xxljob---end ---");
  }
}

/**
 * FactoryBean : 根据不同的配置或者条件, 链接不同的模块或者系统
 */
@Component
class AAA implements FactoryBean,ApplicationContextAware{

  @Override
  public Object getObject() throws Exception {
    if (Objects.isNull(applicationContext1.getBean("aaa"))){
      System.out.println("aaaaaaaaaaaaaaaaaaaaaaaa");
      //return new xxx()  对应的类注入到了spring   ----> 装配自定义的bean,对于不同环境装配 --todo:springboot条件装配
    }
    return null;
  }

  @Override
  public Class<?> getObjectType() {
    return null;
  }

  public ApplicationContext applicationContext1;
  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    applicationContext1 = applicationContext;
  }
}


/**
 * 之前的InitializingBean、DisposableBean、FactoryBean包括init-method和destory-method，
 * 针对的都是某个Bean控制其初始化的操作，而似乎没有一种办法可以针对每个Bean的生成前后做一些逻辑操作，
 * PostProcessor则帮助我们做到了这一点，先看一个简单的BeanPostProcessor。
 *
 * ===>>> 后置处理 ：每次初始化bean
 * ===>>> 切面是 : 每次调用的时候进行通知处理
 */
@Component
class BBB implements BeanPostProcessor{
  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
    if (beanName.equals("sortResolver")){

      System.out.println("====>>>>"+beanName);
    }
    return bean;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    return bean;
  }

}


/**
 * 系统启动后要做什么，负责接收来自应用外部的参数
 *  applicationRunner、CommandLineRunner 获取启动入参
 *    program args :
 *    envoriment variables :
 *
 * 怎样在IntelliJ IDEA中让Java程序读取txt文件作为输入:https://segmentfault.com/q/1010000004480734
 */
@Order(1)
@Component
@Slf4j
class CCC implements ApplicationRunner{

  @Override
  public void run(ApplicationArguments args) throws Exception {
    System.out.println("*********"+ Arrays.toString(args.getSourceArgs()));
  }

  /**
   * 配置中env中可以获取到
   */
  public static void main(String[] args) {
    String abc = System.getenv("abc");
    System.out.println("abc==>"+abc);
    String s = "sdfdf,dfdf,";
    System.out.println(StringUtils.endsWith(s,",")? StringUtils.chop(s):s);
  }
}

/**
 * 远程部署遇到问题
 *  1.META-INF/spring.factories中找不到自动配置类，No auto configuration classes found in META-INF/spring.factories.
 *      https://stackoverflow.com/questions/38792031/springboot-making-jar-files-no-auto-configuration-classes-found-in-meta-inf
 * 2.idea 打包的jar运行报 “XXX中没有主清单属性”
 *      https://blog.csdn.net/zhan107876/article/details/97883972
 * 3.配置idea直接部署到远端服务器
 *
 */

/**
 * 利用不同语言的优势
 *
 * 调用shell文件 、 sql执行、python   、 redis中脚本 、js代码
 */
@Order(200)
@Component
class DDD implements ApplicationRunner{

  @Override
  public void run(ApplicationArguments args) throws Exception {

    System.out.println("echo -------------"+args.getOptionNames());
    /**
     * 0.shell基础篇 ： https://juejin.cn/post/6930013333454061575
     * 1.切换路径
     * 2.权限     https://blog.51cto.com/u_15262460/2883042
     * 3.环境变量问题
     */

    /**
     * 将脚本嵌到项目中拉起：如何找到项目中shell脚本 : https://developer.aliyun.com/article/2362
     */
//    String[] test = new String[] { "/bin/sh", "-c", "touch abc.txt"};
//    String[] test = new String[] { "/bin/sh", "-c", "touch abc.txt"};
//    Process ls = Runtime.getRuntime().exec(test);
//    int i = ls.waitFor();
//    System.out.println(i);

    String shellPath = "/shell/echoSimple";
    JarFile topLevelJarFile = null;
    try {
      topLevelJarFile = new JarFile(shellPath);
      Enumeration<JarEntry> entries = topLevelJarFile.entries();
      while (entries.hasMoreElements()) {
        JarEntry entry = entries.nextElement();
        System.out.println("begin sh ====");
        if (!entry.isDirectory() && entry.getName().endsWith(".sh")) {
          System.out.println("jinru sh ===");
          Process ps=Runtime.getRuntime().exec("sh ");
        }
      }
    }catch (Exception e){}

    /**
     * linux可以执行，但是window上不可以
     */
//    String[] params = new String[] { "/bin/sh", "-c", "ls;pwd"};
//    Process ps=Runtime.getRuntime().exec(params);
//    ps.waitFor();
//
//    BufferedReader bufrIn = new BufferedReader(new InputStreamReader(ps.getInputStream(), "UTF-8"));
//    BufferedReader bufrError = new BufferedReader(new InputStreamReader(ps.getErrorStream(), "UTF-8"));
//
//    // 读取输出 result是shell中的输出
//    StringBuilder result = new StringBuilder();
//    String line = null;
//    while ((line = bufrIn.readLine()) != null || (line = bufrError.readLine()) != null) {
//      result.append(line).append('\n');
//    }
//
//    System.out.println(result);

  }
}

/***
 *
 * 脚本化 - 自动化 - 定时任务监控、推送git、量化....节省个人精力
 *
 * 启动时配合模板引擎、规则引擎、spring SPEL 、 js执行引擎  ， 更高抽象 。 生成代码模板、代码结构
 *
 *  * 语法树解析变量、函数的 API
 *  *     这些接口主要是对一个脚本内容的静态分析，可以作为上下文创建的依据，也可以用于系统的业务处理。
 *  *     比如：计算 “a+fun1(a)+fun2(a+b)+c.getName()” 包含的变量:a,b,c 包含的函数:fun1,fun2
 */

/**
 * 规则引擎   QLExpresion  , 本质： 将String解析为meta,语法解析器,使其可被 ‘语言java..shell’等识别，string更易于传递或者描述
 *
 * 1.规则支持的越多，那么可以对外提供的能力越多，比如可以解析循环，那么可以做个筐子实现循环；或者定义其中的逻辑，更灵活
 * 2.类似于传入一个function , 从原来的值传递一个 动作进来，抽象程度更高
 *
     https://github.com/alibaba/QLExpress

 *
 * QLExpress 脚本引擎被广泛应用在阿里的电商业务场景，具有以下的一些特性:
 *
 * 1、线程安全，引擎运算过程中的产生的临时变量都是 threadlocal 类型。
 * 2、高效执行，比较耗时的脚本编译过程可以缓存在本地机器，运行时的临时变量创建采用了缓冲池的技术，和 groovy 性能相当。
 * 3、弱类型脚本语言，和 groovy，javascript 语法类似，虽然比强类型脚本语言要慢一些，但是使业务的灵活度大大增强。
 * 4、安全控制,可以通过设置相关运行参数，预防死循环、高危系统 api 调用等情况。
 * 5、代码精简，依赖最小，250k 的 jar 包适合所有 java 的运行环境，在 android 系统的低端 pos 机也得到广泛运用。
 *
 */
class Rule {
  public static void main(String[] args) throws Exception {
    String a = "rererere:haiguan,[缴款单位名称]为[太极计算机股份有限公司]不等于[dfdffdfasdsfs],匹配成功";
    System.out.println(StringUtils.substring(a, 0, 30));
    String b = "rere:海关缴款书,[缴款单位名称]为[太极计算机股份有限公司]不等于[fdfdf],匹配成功";
    System.out.println(StringUtils.substring(b, 0, 30));

    ExpressRunner runner = new ExpressRunner();
    DefaultContext<String, Object> context = new DefaultContext<String, Object>();
    context.put("a",1);
    context.put("b",2);
    context.put("c",3);
//    context.put("str", "abcd");
//    context.put("str", "[a,b,c,d]");
    context.put("str", Arrays.asList("1", "2", "a", "b", "c"));
    context.put("true1", true);
    context.put("false1", false);

    //https://blog.hhui.top/hexblog/2021/06/24/210624-QlExpress%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF%E4%BA%8C%EF%BC%9A%E5%9F%BA%E6%9C%AC%E8%AF%AD%E6%B3%95/#8-%E5%AE%B9%E5%99%A8%E9%81%8D%E5%8E%86
    String express = "a+b*c";
    String bo = "a > b";
    String boo = "a in str";
    String b3 = "true1 || false1";
    String b4 = "true1 && false1";

    Object r = runner.execute(express, context, null, true, false);
    Object r1 = runner.execute(bo, context, null, true, false);
    Object r2 = runner.execute(boo, context, null, true, false);
    Object r3 = runner.execute(b3, context, null, true, false);
    Object r4 = runner.execute(b4, context, null, true, false);

    //容器遍历
    context.put("list", Arrays.asList(1, 2, 3));
    Object r5 = runner
        .execute("sum = 0 ;for(i = 0 ; i<list.size() ; i++){sum = sum + list.get(i)};return sum;",
            context, null, true, false);

    //NewList \ NewMap

    System.out.println(r);
    System.out.println(r1);
    System.out.println(r2);
    System.out.println(r3);
    System.out.println(r4);
    System.out.println(r5);

    /**
     * 自定义function \类方法绑定
     */

    String myFun = "function add(int a, int b) {\n"
        + "      return a + b + 100}; return add(1,2);";
    String myFun2 = "function add(int a, int b) {\n"
        + "      return a + b + 100};";
    String myFun1 = "function add(int a, int b) {\n"
        + "    return a + b;\n"
        + "}\n"
        + "\n"
        + "function sub(int a, int b ) {\n"
        + "    return a - b;\n"
        + "}\n"
        + "\n"
        + "return add(10, 20) + sub(4, 2);";
    Object execute = runner.execute(myFun, context, null, false, false);
    System.out.println(execute);

    /**
     * 当需要入参时，则需要绑定java
     */
//    runner.addFunctionOfClassMethod("f",TestKuozhan.class.getName(),myFun2,new Class[]{},null);



  }
}


/**
 * 模板引擎
 *      1.Mybatis常用工具类（二）-- ScriptRunner、SqlRunner  执行脚本、 初始化基本信息   ： https://ibit.tech/archives/mybatis-common-tools-class-script-sql-runner
 *          简便方法：
 *                #spring.datasource.schema=classpath:db/schemaT1.sql
 *                #spring.datasource.data=classpath:db/data.sql
 *      2.模板引擎动态管理表、schema、不同数据库，引入判断
 *      3.从配置文件 - sql工厂 - 获取执行   / selector 不同数据库
 */
@Component
class M implements ApplicationRunner{
    private static String className   	= "org.h2.Driver";
    private static String url         			= "jdbc:h2:mem:dbtest";
    private static String username		= "sa";
    private static String password		= "sa";
    private static Connection connection      = null;

  /**
   * main方法同样可以测试
   */
    public static void main(String[] args)
        throws ClassNotFoundException, SQLException, FileNotFoundException, InterruptedException {
      Class.forName(className);
      connection = DriverManager.getConnection(url, username, password);
      ScriptRunner scriptRunner = new ScriptRunner(connection);
      Resources.setCharset(Charset.forName("UTF8"));
//		scriptRunner.setLogWriter(null);
      scriptRunner.runScript(new FileReader(new File("D:\\testSQL")));
      scriptRunner.closeConnection();

      Thread.sleep(100000);
      connection.close();


    }

  /**
   * 启动spring项目，访问并查看，而不是启动main
   *   todo: 1.加载路径    2.不同的数据库配置bean   3.原生的springEL以及meta拆分组合
   */
  @Override
  public void run(ApplicationArguments args) throws Exception {
    Class.forName(className);
    connection = DriverManager.getConnection(url, username, password);
    ScriptRunner scriptRunner = new ScriptRunner(connection);
    Resources.setCharset(Charset.forName("UTF8"));
//		scriptRunner.setLogWriter(null);
    scriptRunner.runScript(new FileReader(new File("D:\\testSQL")));
//    scriptRunner.runScript(Resources.getResourceAsReader("init-table.sql"));
//    scriptRunner.closeConnection();

    /**
     * 引入模板引擎
     *  为了解决用户界面（显示）与业务数据（内容）分离而产生的。他可以生成特定格式的文档，常用的如格式如HTML、xml以及其他格式的文本格式。
     */
    /* 1.初始化 Velocity */
    VelocityEngine velocityEngine = new VelocityEngine();
    velocityEngine.setProperty(VelocityEngine.RESOURCE_LOADER, "file");
    velocityEngine.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH, "D:\\");
    velocityEngine.init();

    /* 2.创建一个上下文对象 */
    VelocityContext context = new VelocityContext();

    /* 3.添加你的数据对象到上下文 */
    context.put("name", "Victor Zhang");
    context.put("project", "Velocity");

    context.put("a", true);
    context.put("b", false);
    context.put("tableA", "TableTestA");
    context.put("tableB", "TableTestB");

    /* 4.选择一个模板 */
    Template template = velocityEngine.getTemplate("hello.vm");

    /* 5.将你的数据与模板合并，产生输出内容 */
    StringWriter sw = new StringWriter();
    template.merge(context, sw);
    System.out.println("final output:\n" + sw);

    /**
     * 模板引擎与规则引擎  、 模板与sql  、
     */
    scriptRunner.runScript(new StringReader(sw.toString()));
    scriptRunner.closeConnection();
    connection.close();

    /**
     * 不同的数据库selector \数据库配置 - bean configuration
     */
  }
}


/**
 * 自定义解析引擎 - spring EL ： 构建强大且易用的规则引擎 ：https://tech.meituan.com/2017/06/09/maze-framework.html
 *                          https://ranying666.github.io/2021/03/11/rule-engine/
 *                             二、架构图
                             * 三、配置模块(config)
                             * 四、异常模块(Exception)
                             * 五、匹配模块(match)
                             * 六、解析模块(parse)
                             * 七、规则模块(rule)
                             * 八、指令模块(instruction)
                             * 九、其他
 *

 *
 */

/**
 * 1.spring EL 自定义扩展
 * 2.合规校验 meta的拆分 - 流程抽象 - 组件抽象 - 实现 - 问题
 */


/**
 * 流程 precheck
 */



@SpringBootTest(classes = App.class)
@RunWith(SpringRunner.class)
public class TestKuozhan {

  @Autowired
  private TestKuozhan1 testKuozhan1;

  @Test
  public void testkuozhan(){
    testKuozhan1.testAA();

  }
}
