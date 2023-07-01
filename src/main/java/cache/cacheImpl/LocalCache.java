package cache.cacheImpl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 *  本地缓存  Created by yangwenjun on 2022/12/5 15:27
 */
@Component
public class LocalCache{

  /**
   * 方案1：实现google中CacheLoader接口，来省去手动put值，以及这么多参数实现，比如并发、容量.....
   */
//  LoadingCache<String, Object> cache = CacheBuilder.newBuilder()
//
//      //设置并发级别为8，并发级别是指可以同时写缓存的线程数
//      .concurrencyLevel(8)
//      //设置缓存容器的初始容量为10
//      .initialCapacity(10)
//      //设置缓存最大容量为100，超过100之后就会按照LRU最近虽少使用算法来移除缓存项
//      .maximumSize(100)
//      //是否需要统计缓存情况,该操作消耗一定的性能,生产环境应该去除
//      .recordStats()
//      //设置写缓存后n秒钟过期
//      .expireAfterWrite(60, TimeUnit.SECONDS)
//      //设置读写缓存后n秒钟过期,实际很少用到,类似于expireAfterWrite
//      //.expireAfterAccess(17, TimeUnit.SECONDS)
//      //只阻塞当前数据加载线程，其他线程返回旧值
//      //.refreshAfterWrite(13, TimeUnit.SECONDS)
//      //设置缓存的移除通知
//      .removalListener(notification -> {
//        System.out.println(notification.getKey() + " " + notification.getValue() + " 被移除,原因:" + notification.getCause());
//      })
//      //build方法中可以指定CacheLoader，在缓存不存在时通过CacheLoader的实现自动加载缓存
//      .build(new CacheLoader<String, Object>() {
//        @Override
//        public String load(String key) throws Exception {
//          return null;
//        }
//      });


  /**
   * 方案2：手写 + 切面 环绕通知  : 超时策略、清楚策略...
   */
  Map<String,Object> cache = Maps.newHashMap();

  {
    cache.put("public java.util.List boot.SpringTestController111.test1(java.util.List):232", "cache@#@232");
  }
  //todo:3 com.google.common.cache.CacheLoader$InvalidCacheLoadException: CacheLoader returned null for key 232.




  public Map<String,Object> get(List<String> keys){
    Map<String,Object> ret = Maps.newHashMap();
    if (CollectionUtils.isEmpty(keys)) return ret;

     keys.stream().forEach(key -> {
      String value = (String)cache.get(key);
      if (StringUtils.isEmpty(value)){
        value= "-1";
      }
       ret.put(key, value);
    });

     return ret;

  }


  public void put(Map<String, Object> kv) {
//    kv.entrySet().stream().forEach(cache::pu);
    cache.putAll(kv);

  }
}
