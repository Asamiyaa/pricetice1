package cache.annotation;

import cache.ConCtrlDTO;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created by yangwenjun on 2022/12/27 14:14
 * todo: 注解的继承 、import注解解决对象
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
//@InputConctrl
@Autowired    //分模块定义 可以方便
@Qualifier("aaaa")
public @interface Def {
  String conCtrlField() default "idTsList";
  Class containConCtrlFieldClass() default ConCtrlDTO.class;

  int whichArg() default 0;
}
