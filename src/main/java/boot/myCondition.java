package boot;

import boot.myCondition.MyCondition;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Created by yangwenjun on 2022/10/28 22:15
 */
//  @ConditionalOnProperty("${a.b}") //这里没有$也可以，从配置文件中获取a.b属性值,但是还是写上符合要求
  @Target({ ElementType.TYPE, ElementType.METHOD })
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  @Conditional(MyCondition.class)
  public @interface myCondition {

  /**
     * 可以更复杂的扩展比如表达式，在MyCondition实现中完成SPEL解析
     */
    String[] condition() default {};

  /**
   * static 类更聚合
   */

  static class MyCondition implements Condition {

    @Override
    public boolean matches(
        ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
      Environment environment = conditionContext.getEnvironment();
//      Object a = conditionContext.getBeanFactory().getBean("a");
      /**
       * MultiValueMap
       */
      String[] condition = (String[])annotatedTypeMetadata.getAnnotationAttributes("boot.myCondition")
          .get("condition");

      /**
       * 自定义计算方式、标识符比如 {} 等信息
       */
//    if (condition.equals("con")) return true;
      return 1 == 1;
    }
  }
  }




