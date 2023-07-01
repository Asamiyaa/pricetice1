package cache.aspect;

import cache.ICommonVO;
import cache.annotation.InputConctrl;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

/**
 * Created by yangwenjun on 2022/12/27 10:59
 */
@Aspect
@Component
public class InputConCtrlAspect {
  @Pointcut("@annotation(cache.annotation.InputConctrl)")
  public void concurrentControlAnnotationPointcut() {
  }

  @Around("concurrentControlAnnotationPointcut()")
  public Object concurrentCheck(ProceedingJoinPoint joinPoint) throws Throwable {
    Object[] args = joinPoint.getArgs();
    List<String> lockedKeys = new ArrayList<>();

    /**
     *  ProceedingJoinPoint  -- methodSignature -- Method -- Ann -- Filed
     *  ReflectionUtils
     */
    MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
    Method method = methodSignature.getMethod();
    InputConctrl annotation = method.getAnnotation(InputConctrl.class);
    //todo:1.传递的限制有用吗，是否不合理  答：arg.getClass()获取
    String conCtrlFieldStr = annotation.conCtrlField();
//    Class conCtrlFieldClass = annotation.containConCtrlFieldClass();
    int whichArg = annotation.whichArg();

    try {
      Object arg = args[whichArg];
      Field conCtrlField = ReflectionUtils.findField(arg.getClass(), conCtrlFieldStr);
      //todo:2.强转  如何传递而不仅仅是object . 答:如果是则直接转，如果有 转型 就要小心
      ReflectionUtils.makeAccessible(conCtrlField);
      List<ICommonVO> field = (List<ICommonVO>) ReflectionUtils.getField(conCtrlField, arg); //已知效果，没有问题

      /**
       * try {
       *       for (Object arg : args) {
       *         // commonVO 进行加锁+校验ts
       *         if (isCommonVO(arg)) {
       *           // 单个参数
       *           x.doLockAndCheckTs(new ICommonVO[]{(ICommonVO) arg}, lockedKeys);
       *         } else if (isArray(arg) && isCommonVO(Array.get(arg, 0))) {
       *           //数组参数
       *           x.doLockAndCheckTs((ICommonVO[]) arg, lockedKeys);
       *         } else if (isCollection(arg)) {
       *           // Collection 参数
       *           ICommonVO[] commonVOS = collectionToArray((Collection) arg);
       *           x.doLockAndCheckTs(commonVOS, lockedKeys);
       *         }
       *       }
       *
       *
       private ICommonVO[] collectionToArray(Collection collection) {
       int index = 0;
       Object array = Array.newInstance(ICommonVO.class, collection.size());
       for (Object item : collection) {
       ICommonVO value = (ICommonVO) item;
       Array.set(array, index, value);
       index++;
       }
       return (ICommonVO[]) array;
       }

       private boolean isCollection(Object arg) {
       return arg != null && arg instanceof Collection;
       }

       private boolean isArray(Object arg) {
       return arg != null && arg.getClass().isArray();
       }

       private boolean isCommonVO(Object arg) {
       return arg != null && arg instanceof ICommonVO;
       }
       *
       *
       */
      System.out.println("field ==>"+field);
      //强转
//      List<ICommonVO> collect = field.stream().map(x -> {
//        ICommonVO x1 = (ICommonVO) x;
//        return x1;
//      }).collect(Collectors.toList());

//      ConcurrentControlUtils.doLockAndCheckTs(field, lockedKeys);
      return joinPoint.proceed();
    } finally {
      //释放锁
//      ConcurrentControlUtils.finishLockAndCheck(lockedKeys);
    }
  }
}
