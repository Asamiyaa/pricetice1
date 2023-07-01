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

/**
 * 此接口也是Spring 3.0新增，用于统一化 底层类型转换实现的差异，对外提供统一服务，所以它也被称作类型转换的门面接口，从接口名称xxxService也能看出来其设计思路。它主要有两大实现：
 *
 * GenericConversionService：提供模版实现，如转换器的注册、删除、匹配查找等，但并不内置转换器实现
 * DefaultConversionService：继承自GenericConversionService。在它基础上默认注册了非常多的内建的转换器实现，从而能够实现绝大部分的类型转换需求
 * ConversionService转换服务它贯穿于Spring上下文ApplicationContext的多项功能，包括但不限于：BeanWrapper处理Bean属性、DataBinder数据绑定、PropertySource外部化属性处理等等。因此想要进一步深入了解的话，ConversionService是你绕不过去的坎。
 *
 * void setValue(Object value)：设置属性值
 * Object getValue()：获取属性值
 * String getAsText()：输出。把属性值转换成String输出
 * void setAsText(String text)：输入。将String转换为属性值类型输入
 *
 * xxxRegistry用于管理（注册、修改、删除、- 查找）一类组件，当组件类型较多时使用注册中心统一管理是一种非常有效的手段。诚然，PropertyEditor就属于这种场景，管理它们的注册中心是PropertyEditorRegistry。
 *
 *
 * @FunctionalInterface
 * public interface Converter<S, T> {
 * 	T convert(S source);
 * }
 *
 * final class StringToNumberConverterFactory implements ConverterFactory<String, Number> {
 *
 *    @Override
 *  public <T extends Number> Converter<String, T> getConverter(Class<T> targetType) {
 * 		return new StringToNumber<T>(targetType);
 *  }
 *
 *
 * 	System.out.println(converterFactory.getConverter(Integer.class).convert("1").getClass());
 *
 * 相较于前两个，这是最灵活的SPI转换器接口，但也是最复杂的。
 *
 *
 *
 * StreamConverter
 *
 *
 * 因为有了ConversionService提供的强大能力，我们就可以在基于Spring/Spring Boot做二次开发时使用它，提高系统的通用性和容错性。
 */





