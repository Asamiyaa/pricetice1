package boot.annotation;

/**
 * Created by yangwenjun on 2022/10/20 10:06
 */


import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.stream.Stream;
import lombok.Data;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 1.annotation是基于jdk的而不是spring的，所以无需启动spring,启动spring是为了和后面的spring的接口扩展
 *             注意和spring转换连接的地方
 *
 * 2.如何找到呢？ 动态、运行时 ; 切面 - jdk / cglib
 *               方法注解、属性注解
 *
 * 3.核心操作类    Class（全局视角） 、 反射(全局操作)
 *
 *
 */
public class AnnMain implements ITest {

  public static void main(String[] args)
      throws NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    /**
     * 属性注解
     */
    BeanTest beanTest = new BeanTest();

    /**
     * 获取
     */
//    SpringTest annotation = beanTest.getClass().getAnnotation(SpringTest.class);   //这个对象上没有注解，应该是对象属性
//    Field testName = beanTest.getClass().getField("testName");//只能获取public
    Field testName = beanTest.getClass().getDeclaredField("testName");
    SpringTest annotation = testName.getAnnotation(SpringTest.class);
    System.out.println("ann name / count "+annotation.name() + annotation.count());

    Method test = AnnMain.class.getDeclaredMethod("test");
    SpringTest annotation1 = test.getAnnotation(SpringTest.class);
    System.out.println("method ann name / count "+annotation1.name() + annotation1.count());

    /**
     * 获取到注解可以做什么操作
     *      1.获取注解上对应的标识
     *      2.将标识代表的逻辑作用到 '被标识的地方'  ; 校验、塞值、
     */
    if ("Asamiya".equals(annotation.name())) {
      testName.setAccessible(true);
      testName.set(beanTest,"modifyle");
      System.out.println("===>>>>"+testName.get(beanTest));

      System.out.println(testName.getGenericType());
    }


    if ("AsamiyaMethod".equals(annotation1.name())) {
      System.out.println("testMethod before");
      /**
       * 方法的meta : 修饰符、返回、入参、异常
       * 注解的meta:  返回
       *
              模块：https://www.cnblogs.com/throwable/p/12272229.html#%E4%BB%80%E4%B9%88%E6%98%AF%E5%8F%8D%E5%B0%84
                     * 4.Type接口
                     * 5.AnnotatedElement接☐
                     * 6.Member接☐
                     * 7.AccessibleObject类
                     * 8.GenericDeclaration接☐
                     * 9.Executable:类
                     * 10.Modifier
                     * 11.Class类
                     * 12.Constructor类
                     * 13.Method类
                     * 14.Field类
                     * 15.Parameter类
       *
       */
      Object invoke = test.invoke(new AnnMain());

      System.out.println("testMethod after");
    }




    /**
     * 为了扩展，不能指定注解在 '哪里' ， 所以有了切面 - 切点 ;交给编译来帮忙找哪里有注解
     *    1.jdk  Proxy   接口实现
     *    2.cglib        继承
     *    3.asm          静态待代理
     */
    AnnMain annMain = new AnnMain();
    ITest testInvocation = (ITest)Proxy.newProxyInstance(annMain.getClass().getClassLoader(), new Class[]{
            ITest.class},
        new InvocationHandler() {
          @Override
          public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("before jdk invocation");
            Object invoke = method.invoke(annMain, args);
            System.out.println("before jdk invocation");
            return invoke;
          }
        });

    testInvocation.test();


    /**
     * 1.通过切面来灵活获取要拦截的方法、动态性、全局性、拦截后再去获取属性、注解等信息；和上面的注解处理联系起来
     * 2.比上面的注解、等更高维度、更前
     *
     * 面向切面编程AOP，引入了切面、切点、通知。。。 更加关注 '切' 上的东西
     *      1.方法执行前、执行时、执行后、返回值后、异常后要执行的操作。
     *          @Before 通知方法还会在目标方法调用之前执行
     *          @Around 通知方法会将目标方法封装起来
     *          @AfterThrowing 通知方法会在目标方法抛出异常后调用
     *          @AfterReturning 通知方法会在目标方法返回后调用
     *          @After 通知方法会在目标方法返回或抛出异常后调用
     *
     *      2.横切关注点	从每个方法中抽取出来的同一类非核心业务
     *        *切面(Aspect)	封装横切关注点信息的类，每个关注点体现为一个通知方法。
     *        *通知(Advice)	切面必须要完成的各个具体工作
     *        目标(Target)	被通知的对象
     *        代理(proxy)	向目标对象应用通知之后创建的代理对象
     *        *连接点(Joinpoint)	横切关注点在程序代码中的具体体现，对应程序执行的某个特定位置。
     *        切入点(pointcut)	执行或找到连接点的一些方式
     *      3.spring
     *          AnnAspect
     */





    /**
     * 获取更多的数据，来源比如spring;更高维的聚合
     *    1.spring
     *    2.T
     *    3.容器
     *          1.RequestAttributes ra = RequestContextHolder.getRequestAttributes();  ServletRequestAttributes sra = (ServletRequestAttributes) ra;
     *
     */


  }

  /**
   * 方法注解
   */
  @Override
  @SpringTest(name = "AsamiyaMethod" , count = 300)
  public void  test(){
    System.out.println("test method done");
  }

}

/**
 *  切面
 *      @Component   ： 必须加上，否则不走spring
 *      @Aspect  : Aspectj框架
 *
 *
 *      访问： http://localhost:8080/spring/annotation
 */
@Component
@Aspect
class AnnAspect{

  /**
   * 1.execution([权限修饰符] [返回值类型] [简单类名/全类名] [方法名] ([参数列表]))
   * 2.不同的通知使用场景、以及哪些参数、获取到哪些值
   *        1.不同维度的限制，比如方法、参数、注解、bean、target...
   *        2.JointPoint 、Object result / exception / ProcessdingJointPoint
   *
   *
   */
//  @Before("execution(* *.*())")
//  @Before("execution(public String boot.SpringTestController.*())")
//  @Before("@")  通过参数限制
    @Before("@annotation(boot.annotation.SpringTest)")
    public void aroundDone(JoinPoint joinPoint)
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
    System.out.println("annAspect");
    System.out.println(joinPoint);

    /**
     * joinPoint 扩展哪些
     *    1.Signature 包装了方法签名 ， 联系到上面的获取注解等方式
     */
    Signature signature = joinPoint.getSignature();

    /**
     * 获取类
     */
    Class declaringType = signature.getDeclaringType();
    Annotation[] annotations = declaringType.getAnnotations();
    Stream.of(annotations).forEach(x -> {

      System.out.println("aspect anns ---> "+x);
    });

    /**
     * 获取方法
     */
    String name = signature.getName();
    System.out.println("aspect anns2 ---> "+name); //test
    Method method = declaringType.getMethod(name);
    Object invoke = method.invoke(joinPoint.getTarget(), joinPoint.getArgs());
    System.out.println("====>>> 组装调用"+invoke);


    System.out.println("annAspect");
  }

  /**
   * 必须要有返回的，否则就会吞掉正常的返回信息
   */
  @Around("@annotation(boot.annotation.SpringTest)")
  public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    Signature signature = proceedingJoinPoint.getSignature();
    String name = signature.getName();
    System.out.println(name);

    return proceedingJoinPoint.proceed();
  }

}

/**
 * =================基础准备
 */

/**
 *  * Created by yangwenjun on 2022/10/20 11:41
 *  *
 *  *  作为jdk动态代理的定义
 */
interface ITest{
  void test();
}

/**
 * Created by yangwenjun on 2022/10/20 10:03
 * 自定义test注解
 *
 *  1.定义要控制的维度
 *  2.元注解    @inherit  @Repeat
 *
 * @author Administrator
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD,ElementType.PARAMETER})
 @interface SpringTest {

  String name() default "";

  int count() default 0;

}


@Data class BeanTest {

  @SpringTest(name = "Asamiya",count = 30)
  private String testName;

}

@RestController
@RequestMapping("/spring")
class SpringTestController {

  @GetMapping("/annotation")
  @SpringTest
  public String test(){
    return "annontaion";
  }

  /**
   * spring启动类
   */
//  @SpringBootApplication(scanBasePackages = {"component","boot"})
//  @PropertySource({"classpath:application.properties"})
//  public class App {
//    public static void  main(String[]args){
//      SpringApplication.run(boot.App.class,args);
//    }
//  }


}