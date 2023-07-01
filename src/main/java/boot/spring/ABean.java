package boot.spring;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Optional;
import lombok.AllArgsConstructor;

/**
 * 1.一个对象包的层数越多，那么他的能力增强越多   wrapper
 *
 * 问题
 *      1.bean的嵌套 - 循环依赖  ： https://pdai.tech/md/spring/spring-x-framework-ioc-source-3.html
 *      2.bean的存放 - 缓存
 *      3.状态限制  : https://cloud.tencent.com/developer/article/1497831
 *
 */
public class ABean {


  public static Map<String,Object> beanContainer = Maps.newHashMap();

  static {
    beanContainer.put("abean", new ABean());
    beanContainer.put("abeanW", new ABeanWrapper("abeanW",new ABean()));
  }


  public static void main(String[] args) {

    Object abean = beanContainer.get("abean");
    System.out.println(abean instanceof ABean);

    ABean abean1 = getBean("abean", ABean.class);
    System.out.println(abean1 instanceof ABean);

    String a = null;
    System.out.println(Optional.ofNullable(a).map(String::length).orElse(1));


  }


  /**
   *   重载增强
   */
  public static <T> T getBean(String beanName,Class<T> cls){
    return (T)beanContainer.get(beanName);
  }

  @AllArgsConstructor
  static class ABeanWrapper{
    private String wrapperName;
    private ABean aBean;
  }

}
