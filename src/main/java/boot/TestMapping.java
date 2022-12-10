package boot;

import boot.mapstruct.Dest;
import boot.mapstruct.InnerSource;
import boot.mapstruct.Source;
import com.alibaba.fastjson.JSON;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.util.Assert;

/**
 * Created by yangwenjun on 2022/10/28 14:39
 *  原则
 *      1.相同的含义字段应该在数据库、对象中同样类型、同样名称 ，减少转换 ，保障统一性
 *
 *  发生转化的地方
 *      1.领域进出、接口入参
 *      2.string、序列化作为传递 --> 解析为对象操作
 *      3.格式化问题
 */
public class TestMapping {

  /** 0.字段设置是否合理
   *  1.convert <source,desc>  对应的函数式
   *  2. dto 转换   beancopy
   *  3.
   */
  public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {

    ConvertImpl convert = new ConvertImpl();
    String abc = convert.convert("abc", (a) -> a.toUpperCase());
    System.out.println(abc);    //ABC

    /**
     * 几种对象属性转换
     *    1.类型不同不赋值
     *    2.嵌套对象也会赋值,名称不同不赋值。如果名称不同则需要单独处理 -- 转换设置  比如读取固定配置关系
     *    3.性能  mapStruct - beanCoper - spring - （fast - apache）太差
     */
    Source source = new Source("S",1,new InnerSource("innerSource"));
    Dest desc = new Dest();

    BeanUtils.copyProperties(source,desc,"age"); //可忽略
    System.out.println("beanUtils ignore===> "+desc);
    BeanUtils.copyProperties(source,desc);
    System.out.println("beanUtils ===> "+desc); //类型不一致也不会copy ,比如一个int,一个string = null;

    org.apache.commons.beanutils.BeanUtils.copyProperties(source,desc);
    System.out.println("apache beanutils"+desc);
    org.apache.commons.beanutils.BeanUtils.copyProperty(source,"setName","nameCopy");

    Dest dest = JSON.parseObject(JSON.toJSONString(source), Dest.class);
    System.out.println("fastJson copy ==>"+desc);

    /**
     * 这种方案动态生成一个要代理类的子类,其实就是通过字节码方式转换成性能最好的get和set方式,重要的开销在创建BeanCopier，整体性能接近原生代码处理，比BeanUtils要好很多，尤其在数据量很大时，但是针对复杂场景支持能力不足。
     */
    BeanCopier beanCopier = BeanCopier.create(Source.class, Dest.class,false);
    beanCopier.copy(source,desc,null);
    System.out.println("beanCoper ==>"+desc);

    /**
     * mapStruct -- https://developer.aliyun.com/article/786127  相当于框架为我们编译生成了get/set方法，避免反射
     */
    Dest dest1 = SourceMapper.instance.toConvert(source);
    System.out.println("sourceMapper ==> "+dest1);

    /**
     * ================================================
     */

    /**
     * 写框架定义 类型转换 比如适配int / list  string的转换关系  ==> spring convert regist   https://cloud.tencent.com/developer/article/1765543
     *
     *  场景：1.框架   标量、 集合、时间、兜底
     *       2.属性填充   定义转换接口，对象实现该接口，固定 '属性' 就会被遍历赋值
     */

    /**
     * 格式化      https://cloud.tencent.com/developer/article/1771077
     *        1.时间   时间
     *        2.数字   精度、百分、格式化
     */

  }




}

/**
 *  抽取变量
 *      1.泛型
 *      2.function 抽取行为
 */
interface Convert<SOURCE,DESC>{

  DESC convert(SOURCE source, Function<SOURCE, DESC> function);
}


class ConvertImpl implements Convert<String,String> {

  @Override
  public String convert(String s, Function<String, String> function) {
    Assert.notNull(s,"not null");
    String ret = function.apply(s);
    Assert.notNull(ret,"not null");
    return ret;
  }
}

/**
 * mapperstruct
 */
@Mapper
interface SourceMapper{
  //必须放在这里
  SourceMapper instance = Mappers.getMapper(SourceMapper.class);
  /**
   * 更多set指定
   */
  Dest toConvert(Source source);

}






