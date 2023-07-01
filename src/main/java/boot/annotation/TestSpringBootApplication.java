package boot.annotation;

import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by yangwenjun on 2022/12/12 16:43
 */
@SpringBootApplication
public class TestSpringBootApplication {

  /**
   * 1. 继承  vs  元注解
   * 2. import  ： 借助@Import的帮助，将所有符合自动配置条件的bean定义加载到IoC容器
   *
   *
   * 3.@EnableAutoConfiguration会根据类路径中的jar依赖为项目进行自动配置，如：添加了spring-boot-starter-web依赖，会自动添加Tomcat和Spring MVC的依赖，Spring Boot会对Tomcat和Spring MVC进行自动配置。
   * 借助EnableAutoConfigurationImportSelector，@EnableAutoConfiguration可以帮助SpringBoot应用将所有符合条件的@Configuration配置都加载到当前SpringBoot创建并使用的IoC容器。
   * 就像一只“八爪鱼”一样，借助于Spring框架原有的一个工具类：SpringFactoriesLoader的支持，@EnableAutoConfiguration可以智能的自动配置功效才得以大功告成！
   * SpringFactoriesLoader属于Spring框架私有的一种扩展方案，其主要功能就是从指定的配置文件META-INF/spring.factories加载配置。
   * 提供一种配置查找的功能支持，即根据@EnableAutoConfiguration的完整类名
   * org.springframework.boot.autoconfigure.EnableAutoConfiguration作为查找的Key，获取对应的一组@Configuration类。
   *
   * 从classpath中搜寻所有的META-INF/spring.factories配置文件，并将其中
   * org.springframework.boot.autoconfigure.EnableutoConfiguration对应的配置项通过反射（Java Refletion）实例化为对应的标注了@Configuration的JavaConfig形式的IoC容器配置类，然后汇总为一个并加载到IoC容器。
   * 类似于帮你写了xml配置
   *
   */

  /**
   * run方法加载过程  <--- refresh()
   * 第一部分进行SpringApplication的初始化模块，配置一些基本的环境变量、资源、构造器、监听器；
   * 第二部分实现了应用具体的启动方案，包括启动流程的监听模块、加载配置环境模块、及核心的创建上下文环境模块；
   * 第三部分是自动化配置模块，该模块作为springboot自动配置核心，在后面的分析中会详细讨论。在下面的启动程序中我们会串联起结构中的主要功能。
   */

  /**
   * spring是如何和其他领域的框架结合的
   * mybatis 融入spring : https://www.toutiao.com/article/6791059397167546891/?log_from=7bd40c88d5694_1667053489423
   */

  /**
   * spring是如何启动tomcat的
   * https://www.toutiao.com/article/6869694961353228814/?log_from=ced137ad6b426_1667053365606
   */

}
