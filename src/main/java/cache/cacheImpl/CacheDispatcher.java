package cache.cacheImpl;

import cache.constant.CacheLevel;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *  缓存分发器 Created by yangwenjun on 2022/12/5 19:12
 */
@Component
public class CacheDispatcher {

  @Autowired
  private LocalCache localCache;


  /**
   * https://juejin.cn/post/6979800528662298654
   */
//  public List<Object> dispatch(List<String> keys , CacheLevel cacheLevel){
  public Map<String,Object> dispatch(List<String> keys , CacheLevel cacheLevel){

    if (CacheLevel.LOCAL.equals(cacheLevel)){
      return localCache.get(keys);
    }


    return null;
  }


  public void putValue(Map<String,Object> kv ,CacheLevel cacheLevel){
    if (CacheLevel.LOCAL.equals(cacheLevel)){
       localCache.put(kv);
    }
  }

}
