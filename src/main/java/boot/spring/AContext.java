package boot.spring;

import com.google.common.collect.Maps;
import java.util.Map;

/**
 * 1.为了不在长链路的参数中一直传递,放了一个上方的保存池
 *
 *      1.跨系统/线程间上下文传递 , MDC全局链路追踪原理与实现    https://juejin.cn/post/6901227625188950030
 *      2.Spring Cloud Feign 参数上下文设计：https://www.jianshu.com/p/f51c0dc29327
 *      3.
 */
public class AContext {


  public static Map<String,Object> contextMap = Maps.newHashMap();

  public static void put(String k,Object v){
    contextMap.put(k, v);
  }

  public static Object get(String k){
    return contextMap.get(k);
  }

}
