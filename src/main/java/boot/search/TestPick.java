package boot.search;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * Created by yangwenjun on 2022/12/10 20:58
 *
 *
 *
 *    算法  ： https://www.yuque.com/asamiya/xdk2lb/qngadg
 *        基础：数据结构：数据组织编排   线性表 、散列表 、树、图
 *        ----------- 指导思想 --加入性能、大数据量考虑   https://www.yuque.com/asamiya/xdk2lb/heax1q-----------------
 *  *                0.分治
 *  *                1.动态规划
 *  *                2.贪心
 *  *                3.回溯
 *  *                4.完美分支
 *
 *         1.排序
 *         2.定位(查找)
 *              无结构  (有结构可以转为无结构,转是为了这里的数据都有用)
 *                 思路
 *                  1.map  --> spel( 复杂点： 如何融合其他知识领域比如spring-别人已经给你准备好数据,如何组织入参-从哪里哪个领域获取)
 *                             比如PathPattern，AntPathMatcher、parser、上下文、
 *
 *              有结构 (有结构 保留结构 1.为了其他信息获取   2.大量无用数据)
 *                 思路
 *                  1.顺序查找    https://zhuanlan.zhihu.com/p/64940290
 *                  2.二分查找
 *                  3.插值查找
 *                  4.斐波那契查找
 *                  5.分块查找
 *                  6.树表查找
 *                 具体实现：
 *                  1.xpath  --> 算法(动态规划....)
 *
 *
 *      -------------人工智能 、自动学习 ------------------
 *                0.蚁群算法
 *                1.退火算法
 *                ...
 *
 */
public class TestPick {

  static ExpressionParser parser = new SpelExpressionParser();
  public static void main(String[] args) throws NoSuchMethodException {

    Map<String,String> map = Maps.newHashMap();
    map.put("ab", "aabb");

    String ab = map.get("ab");
    System.out.println(ab);

    /**
     * springEl表达式   vs 规则引擎(QLExpress)
     * Spring Expression Language (SpEL) 是强大的表达式语言，支持查询、操作运行时对象图，以及解析逻辑、算术表达式。SpEL可以独立使用，无论你是否使用Spring框架。
     *
     *    一、基本表达式： 字面量表达式、关系，逻辑与算数运算表达式、字符串连接及截取表达式、三目运算及Elivis表达式、正则表达式、括号优先级表达式；
     *    二、类相关表达式： 类类型表达式、类实例化、instanceof表达式、变量定义及引用、赋值表达式、自定义函数、对象属性存取及安全导航表达式、对象方法调用、Bean引用；
     *    三、集合相关表达式： 内联List、内联数组、集合，字典访问、列表，字典，数组修改、集合投影、集合选择；不支持多维内联数组初始化；不支持内联字典定义；
     *    四、其他表达式：模板表达式。
     *
     *    https://cloud.tencent.com/developer/article/1676200
     */
    Expression expression = parser.parseExpression("1+2");
    System.out.println(expression.getValue());

    //string解析为其他类型的能力，无需自己转换
    Expression exp = parser.parseExpression("666");
    Integer value = exp.getValue(Integer.class);
    System.out.println(value);

    //解析对象
    InnerEl innerEl = new InnerEl("el",1);
    String jsonObj = JSON.toJSONString(innerEl);
    System.out.println(jsonObj);
//    Expression expression1 = parser.parseExpression(jsonObj); //No converter found capable of converting from type [java.util.Collections$UnmodifiableMap<?, ?>] to type [boot.search.TestPick$InnerEl]
//    InnerEl value1 = expression1.getValue(InnerEl.class);
//    System.out.println(value1);

    //解析方法调用 -- 元数据 - 字符串处理 - 解析 - 执行逻辑
    //执行各种操作(比较、逻辑、算术)   and . or ...
    Expression expression1 = parser.parseExpression("'ab'.contains('a')");
    System.out.println(expression1.getValue());
    Expression expression2 = parser.parseExpression("'a,b,c'.split(',')[1]");
    System.out.println(expression2.getValue());
    System.out.println("逻辑运算"+parser.parseExpression("true and false").getValue());
    //List
    String expression9 = "{{1+2,2+4},{3,4+4}}";
    //手动指定泛型结构  List<List<Integer>>
    List<List<Integer>> result3 = parser.parseExpression(expression9).getValue(List.class);
    result3.get(0).set(0, 1);
    System.out.println("result3 is =>>>"+result3);
    //集合投影  (list|map）.![投影表达式]”
    //集合选择  (list|map).?[选择表达式]”   #list.?[#this>4].![value+1]" 这里的value指的是map中的value,集合用this
    List<Integer> list = new ArrayList<Integer>();
    list.add(1);
    list.add(4);
    list.add(5);
    list.add(7);
    EvaluationContext context1 = new StandardEvaluationContext();
    context1.setVariable("list", list);
    Collection<Integer> result1 = parser.parseExpression("#list.?[#this>4]").getValue(context1, Collection.class);
    Collection<Integer> result2 = parser.parseExpression("#list.?[#this>4].![#this+1]").getValue(context1, Collection.class);
    result1.forEach(System.out::println);
    result2.forEach(System.out::println);

    //访问对象属性和方法
    Expression name = parser.parseExpression("name");
    System.out.println(name.getValue(innerEl));
    Expression expression3 = parser.parseExpression("getName().equals('el1')");
    System.out.println(expression3.getValue(innerEl));
    //多个对象和变量
    InnerEl el2 = new InnerEl("el2", 2);
    StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();
    standardEvaluationContext.setVariable("el", innerEl);
    standardEvaluationContext.setVariable("el2",el2);
    // #从上下文获取,赋值
    Expression expression4 = parser.parseExpression("((#el.getName() + '3').equals('el2')) or true");
    Boolean value1 = expression4.getValue(standardEvaluationContext, Boolean.class);
    System.out.println("多个对象获取"+value1);

    parser.parseExpression("#el.name").setValue(standardEvaluationContext, "el222222222222222222");
    System.out.println(parser.parseExpression("#el").getValue(standardEvaluationContext, innerEl.getClass()));


    //自定义函数
    StandardEvaluationContext context = new StandardEvaluationContext();
    context.registerFunction("isURLValid",
        TestPick.class.getDeclaredMethod("isValid", new Class[] { String.class }));

    String expression5 = "#isURLValid('http://google.com')";

    Boolean isValid = parser.parseExpression(expression5).getValue(context, Boolean.class);
    System.out.println(isValid);

    //bean
    DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
    beanFactory.registerSingleton("inner",innerEl);
    StandardEvaluationContext standardEvaluationContext1 = new StandardEvaluationContext();
    standardEvaluationContext1.setBeanResolver(new BeanFactoryResolver(beanFactory));
    SpelExpressionParser spelExpressionParser = new SpelExpressionParser();
    InnerEl value2 = spelExpressionParser.parseExpression("@inner").getValue(standardEvaluationContext1,InnerEl.class);
    System.out.println(value2);

    //表达式模板  -- 模板解析配合塞值
    TemplateParserContext templateParserContext = new TemplateParserContext("%{","}");
    Expression expression6 = parser.parseExpression("%{#name}你好，欢迎来到",templateParserContext);
    standardEvaluationContext1.setVariable("name","asamiya");
    String value3 = expression6.getValue(standardEvaluationContext1, String.class);
    System.out.println(value3);


  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  static class InnerEl {
    private String name;
    private int age;
  }

  public static boolean isValid(String param){
    return "www.baidu.com".equals(param);
  }
}


//@Component
class TestSpringEl{
//不能注入
//      @Autowired
//      private ExpressionParser expressionParser1;
















}
