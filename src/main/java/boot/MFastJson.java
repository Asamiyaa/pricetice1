package boot;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.deserializer.ParseProcess;
import com.alibaba.fastjson.serializer.BeanContext;
import com.alibaba.fastjson.serializer.ContextValueFilter;
import com.alibaba.fastjson.serializer.NameFilter;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.util.List;

/**
 * Created by yangwenjun on 2022/10/19 16:47
 */
public class MFastJson {

  public static void main(String[] args) {

    JSONObject jsonObject = new JSONObject();
    jsonObject.putIfAbsent("1",1);

//    System.out.println(JSON.parseObject("{a:b}"));

    /**
     *
     * 过滤   vs   定义不同的展示对象
     *
     *
     *   name : @JSONField name
     *   value : @JSONField 下value的值
     *   o: 该对象
     */
    String abc = JSON.toJSONString("abc", new ContextValueFilter() {
      @Override
      public Object process(BeanContext beanContext, Object o, String name, Object value) {
        if ("abc".equals(value)) {
          return "--->";
        }
        return null;
      }
    });
    System.out.println(abc);

    /**
     * 定制化序列化值
     * public static class Model {
     *     @JSONField(serializeUsing = ModelValueSerializer.class)
     *     public int value;
     * }
     *
     * public static class ModelValueSerializer implements ObjectSerializer {
     *     @Override
     *     public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType,
     *                       int features) throws IOException {
     *         Integer value = (Integer) object;
     *         String text = value + "元";
     *         serializer.write(text);
     *     }
     * }
     *
     *
     * public class BdMaterialDjSerializer implements ObjectSerializer {
     *
     *   @Override
     *   public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType,
     *       int features) throws IOException {
     *     BigDecimal value = (BigDecimal) object;
     *     String text = value.stripTrailingZeros().toPlainString();
     *     serializer.write(text);
     *   }
     *  }
     */


    /**
     * 兼容老接口
     */
    SerializeConfig.getGlobalInstance().addFilter(Object.class, new NameFilter() {
      @Override
      public String process(Object o, String s, Object o1) {
        return s.toUpperCase();
      }
    });

    /**
     * 时间兼容,格式化
     */
    String jsonOutput =
        JSON.toJSONStringWithDateFormat(new Object(), "yyyy-MM-dd");

    /**
     * SerializerFeature ： 序列化特性 https://blog.csdn.net/u010246789/article/details/52539576  个性化配置
     */

    /**
     * API  --- 每个关节都可以动
     *
     *
     *     PropertyPreFilter 根据PropertyName判断是否序列化
         * PropertyFilter 根据PropertyName和PropertyValue来判断是否序列化
         * NameFilter 修改Key，如果需要修改Key,process返回值则可
         * ValueFilter 修改Value
         * BeforeFilter 序列化时在最前添加内容
         * AfterFilter 序列化时在最后添加内容
     */
    String s = JSON.toJSONString(new Object(), new SerializeConfig(), new PropertyFilter() {
      @Override
      public boolean apply(Object o, String s, Object o1) {
        return false;
      }
    }, SerializerFeature.BeanToArray);

    /**
     * feature: https://blog.csdn.net/xinyuan_java/article/details/102699418
     *
     * ParseProcess是编程扩展定制反序列化的接口。fastjson支持如下ParseProcess：
     *
     * ExtraProcessor 用于处理多余的字段；
     * ExtraTypeProvider 用于处理多余字段时提供类型信息。
     */
    String s1 = JSON.parseObject((String) new Object(), String.class, new ParseProcess() {
      @Override
      public int hashCode() {
        return super.hashCode();
      }
    }, Feature.AllowComment);

    /**
     * 泛型
     */
    List<Object> list = JSON.parseObject("...", new TypeReference<List<Object>>() {});



    /**
     * 循环引用与解决
     *      {"$ref":"$"}	引用根对象
     *      {"$ref":"@"}	引用自己
     *      {"$ref":".."}	引用父对象
     *      {"$ref":"../.."}	引用父对象的父对象
     *      {"$ref":"$.members[0].reportTo"}	基于路径的引用
     */

    JSON.toJSONString(new Object(), SerializerFeature.DisableCircularReferenceDetect);

    /**
     * JSONPath
     *
     *      $	根对象，例如$.name
           * [num]	数组访问，其中num是数字，可以是负数。例如$[0].leader.departments[-1].name
           * [num0,num1,num2...]	数组多个元素访问，其中num是数字，可以是负数，返回数组中的多个元素。例如$[0,3,-2,5]
           * [start:end]	数组范围访问，其中start和end是开始小表和结束下标，可以是负数，返回数组中的多个元素。例如$[0:5]
           * [start:end :step]	数组范围访问，其中start和end是开始小表和结束下标，可以是负数；step是步长，返回数组中的多个元素。例如$[0:5:2]
           * [?(key)]	对象属性非空过滤，例如$.departs[?(name)]
           * [key > 123]	数值类型对象属性比较过滤，例如$.departs[id >= 123]，比较操作符支持=,!=,>,>=,<,<=
           * [key = '123']	字符串类型对象属性比较过滤，例如$.departs[name = '123']，比较操作符支持=,!=,>,>=,<,<=
           * [key like 'aa%']	字符串类型like过滤，
           * 例如$.departs[name like 'sz*']，通配符只支持%
           * 支持not like
           * [key rlike 'regexpr']	字符串类型正则匹配过滤，
           * 例如departs[name like 'aa(.)*']，
           * 正则语法为jdk的正则语法，支持not rlike
           * [key in ('v0', 'v1')]	IN过滤, 支持字符串和数值类型
           * 例如:
           * $.departs[name in ('wenshao','Yako')]
           * $.departs[id not in (101,102)]
           * [key between 234 and 456]	BETWEEN过滤, 支持数值类型，支持not between
           * 例如:
           * $.departs[id between 101 and 201]
           * $.departs[id not between 101 and 201]
           * length() 或者 size()	数组长度。例如$.values.size()
           * 支持类型java.util.Map和java.util.Collection和数组
           * .	属性访问，例如$.name
           * ..	deepScan属性访问，例如$..name
           * *	对象的所有属性，例如$.leader.*
           * ['key']	属性访问。例如$['name']
           * ['key0','key1']	多个属性访问。例如$['id','name']
     */
    Object eval = JSONPath.eval(new Object(), "a.bc.");
    JSONPath.arrayAdd(new Object(),"$.store.book[0].title","1111");




  }


}
