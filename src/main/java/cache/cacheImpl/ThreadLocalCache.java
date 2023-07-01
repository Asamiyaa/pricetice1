package cache.cacheImpl;

import com.google.common.collect.Maps;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

/**
 * Created by yangwenjun on 2022/12/6 13:02
 */
public class ThreadLocalCache {

  /**
   * todo : 1.上下文设计
   *        2.传递
   */
  static ThreadLocal<Map<String,Object>> localMap = new ThreadLocal<>();


  public static void main(String[] args) throws Exception {

    HashMap<String, Object> map = Maps.newHashMap();
    map.put("1", "111");
    localMap.set(map);

    System.out.println(localMap.get().get("1"));
    System.out.println(localMap.get().get("2"));

    Callable<Object> callable = Executors
        .callable(() -> {
          try {
            Thread.sleep(3000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          System.out.println("--->>>" + localMap.get().get("1"));
        });
    System.out.println(callable.call()); //111


//    Executors.

//    Thread.sleep(3000);

  }
}
