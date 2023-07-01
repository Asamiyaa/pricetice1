package cache.annotation;


import cache.constant.CacheLevel;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yangwenjun on 2022/12/5 18:17
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {

  CacheLevel cacheLevel() default CacheLevel.LOCAL ;

//  String keyPrefix();

  int whichArg() default 0;






}
