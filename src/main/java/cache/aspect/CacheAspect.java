package cache.aspect;

import cache.annotation.Cache;
import cache.cacheImpl.CacheDispatcher;
import cache.constant.CacheLevel;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 缓存切面  Created by yangwenjun on 2022/12/5 18:30
 */
@Component
@Aspect
public class CacheAspect {

  @Autowired
  private CacheDispatcher cacheDispatcher;



  @Pointcut("@annotation(cache.annotation.Cache)")
  public void point(){}

  @Around("point()")
  public Object point(ProceedingJoinPoint joinPoint){

    CacheValue cacheValue = getValueFromCache(joinPoint);

    /**
     * todo 3 : 只要有一个缺失、所有的参数就会去查询一次去，因为没有修改参数，可以修改入参，但要小心后面有使用的地方
     *    没有省多少效果
     */
    if (cacheValue.needProceed()){

      Object proceed = new Object();
      try {
        proceed = joinPoint.proceed();

        //todo：5:这里入参有问题，不能再使用获取时对key的处理逻辑了，丢失了;没有减少，如果减少还需要处理proceed = joinPoint.proceed(),修改入参

        putValueToCache(Lists.newArrayList(cacheValue.getKv().keySet()),proceed);

      } catch (Throwable throwable) {
        throwable.printStackTrace();
      }

      return proceed;
//      cacheValue = assembleResult(cacheValue,proceedCacheValue);
    }

    return cacheValue.get();
  }

  /**
   *  部分值在缓存中，部分需要调用获取
   */
//  private CacheValue assembleResult(CacheValue cacheValue, CacheValue proceedCacheValue) {
//
//    return cacheValue;
//  }

  private void putValueToCache(List<String> keys, Object proceed) {
    try {

      //todo:4.将object通过接口、泛型向下限制 ===>>> List
//      cacheDispatcher.putValue();
      List proceedRet = (List<Object>) proceed;
      Map<String,Object> toSaveMap = Maps.newHashMap();

      for (int i = 0; i < proceedRet.size(); i++) {
        toSaveMap.put(keys.get(i), proceedRet.get(i));
      }

      cacheDispatcher.putValue(toSaveMap,CacheLevel.LOCAL);

    }catch(Exception ex){

    }
  }

  private CacheValue getValueFromCache(ProceedingJoinPoint joinPoint) {
    //todo: 修改一  method获取方式 , 强转成任一有用的子类
    Object[] args = joinPoint.getArgs(); //得到的是一个对象，此时
//    //判断 、类型转换 string?
//    Signature signature = joinPoint.getSignature();
//
//    String methodName = signature.getName();
//    Object target = joinPoint.getTarget();
//    //不对，这里target没有具体类型
//    Method method = ReflectionUtils.findMethod(target.getClass(), methodName);

    MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
    Method method = methodSignature.getMethod();

    Cache cacheAnn = method.getAnnotation(Cache.class);
    CacheLevel cacheLevel = cacheAnn.cacheLevel();
    int whichArg = cacheAnn.whichArg();
//    String keyPrefix = cacheAnn.keyPrefix(); //当前方法名，保证当前方法与返回一致
//    String methodName = method.getName();
    String className = method.toString();
    String keyPrefix = className +":"/*+methodName + ":"*/; //todo:这里需要添加类名，否则可能重复
    /**
     * todo:指定参数  奇怪
     */
    Object arg = args[whichArg];
    /**
     * convert and registor  https://cloud.tencent.com/developer/article/1765543
     */
    List<String> argList = Lists.newArrayList();
    String argStr = null;
    if (arg instanceof List) {
       argList = (List)arg;
    } else if(arg instanceof String){
      argStr = String.valueOf(arg);
      argList.add(argStr);
      //添加到list中
    }

    /**
     * 传递 key v 值出去 ，这里可以借助 map或者threadlocal
     */
    List<String> keys = jointKey(keyPrefix, argList);

    Map<String,Object> ret = cacheDispatcher.dispatch(keys, cacheLevel);

    CacheValue cacheValue = judgeNeedProceed(keys,ret);

    /**
     * key value是否对应着呢，万一返回null,底层如何处理
     */
    cacheValue.setKv(ret);

    return cacheValue;
  }

  private CacheValue judgeNeedProceed(List<String> keys, Map<String,Object> ret) {
    CacheValue cacheValue = new CacheValue();
    cacheValue.setNeedProceed(keys.size() != ret.values().stream().filter(x -> !"-1".equals(x)).collect(
        Collectors.toList()).size());
    return cacheValue;
  }

  private List<String> jointKey(String keyPrefix, List<String> argList) {
    if (StringUtils.isEmpty(keyPrefix)) return argList;
    return argList.stream().map(x -> keyPrefix + x).collect(Collectors.toList());
  }


  @Getter
  @Setter
  static class CacheValue {

//    private List<String> keys;
//    private List<Object> values;
    /**
     * 这里设计有问题，应该是map,这样就含有传递映射关系
     */
    private Map<String,Object> kv;

    private boolean needProceed;

    public boolean needProceed() {
      return needProceed;
    }

    public List<Object> get(){
//      return kv.values().stream().map(Map.Entry::getValue).collect(Collectors.toList());
      return (List<Object>) kv.values();
    }
  }
}
