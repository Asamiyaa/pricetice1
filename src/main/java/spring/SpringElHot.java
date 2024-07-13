package spring;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpringElHot {

    public static void main(String[] args) {
        ExpressionParser parser = new SpelExpressionParser();
//        Expression exp = parser.parseExpression("1 + 2.44f");
        Expression expression6 = parser.parseExpression("1>2 or 1<2 or 1+2 > 2");
        System.out.println(expression6.getValue());


        //方法属性调用
        Expression exp1 = parser.parseExpression("'Hello World'.concat('!').toLowerCase()");
        System.out.println(exp1.getValue());

        Expression exp2 = parser.parseExpression("'123'.bytes");
        byte[] value = (byte[]) exp2.getValue();
        System.out.println(value);

        //属性值的相应设置来支持嵌套属性。也可以访问公共字段
        Expression exp3 = parser.parseExpression("'Hello World'.bytes.length");
        System.out.println(exp3.getValue());

        //属性与值、对象
        User user = new User();user.setUsername("abc");
        Expression exp4 = parser.parseExpression("username");  //已知对象，自由定义或者的属性值、方法和比较对应的值； 规则引擎
        System.out.println(exp4.getValue(user));

        //Type Conversion 类型转换  默认情况下，SpEL 使用 Spring core （ org.springframework.core.convert.ConversionService ） 中提供的转换服务。此转换服务附带了许多用于常见转换的内置转换器，但也是完全可扩展的，因此您可以在类型之间添加自定义转换。此外，它还具有泛型感知能力。这意味着，当您在表达式中使用泛型类型时，SpEL 会尝试转换以保持它遇到的任何对象的类型正确性。
        //这在实践中意味着什么？假设赋值 using setValue() 用于设置 List 属性。属性的类型实际上是 List<Boolean> 。SpEL 识别出列表中的元素 Boolean 在放入列表之前需要转换为。下面的示例演示如何执行此操作。
        System.out.println(exp4.getValue(user, String.class));


        //EvaluationContext 该接口用于解析属性、方法或字段，并帮助执行类型转换
        //SimpleEvaluationContext ：公开基本 SpEL 语言功能和配置选项的子集，用于不需要 SpEL 语言语法全部范围且应有意义限制的表达式类别。示例包括但不限于数据绑定表达式和基于属性的筛选器
        //StandardEvaluationContext ：公开完整的 SpEL 语言功能和配置选项集。您可以使用它来指定默认根对象，并配置每个可用的与评估相关的策略



        class Simple {
            public List<Boolean> booleanList = new ArrayList<>();
            public List<String> auto1 = new ArrayList<>();
        }
        Simple simple = new Simple();
        simple.booleanList.add(true);

        EvaluationContext context = SimpleEvaluationContext.forReadOnlyDataBinding().build();

        parser.parseExpression("booleanList[0]").setValue(context, simple, "false");
        Boolean b = simple.booleanList.get(0);
        System.out.println(b);

        //**分析器，相对于反射，可以更好的结合 String 自定义或者传入扩展，操作对象
        EvaluationContext context2 = new StandardEvaluationContext();
        Expression ep = parser.parseExpression("username");
//        EL1010E: Property or field 'username' cannot be set on object of type 'spring.User' - maybe not public or not writable?
//        ep.setValue(context,user,"admin");
        ep.setValue(context2,user,"admin");
        System.out.println(user.getUsername());


        //Parser Configuration 解析器配置
        //可以使用解析器配置对象 （ org.springframework.expression.spel.SpelParserConfiguration ） 来配置 SpEL 表达式解析器。配置对象控制某些表达式组件的行为。例如，如果索引到数组或集合中，并且指定索引处的元素为 null ，则 SpEL 可以自动创建该元素。这在使用由属性引用链组成的表达式时非常有用。如果索引为数组或列表，并指定超出数组或列表当前大小末尾的索引，则 SpEL 可以自动扩展数组或列表以容纳该索引。为了在指定的索引处添加元素，SpEL 将在设置指定值之前尝试使用元素类型的默认构造函数创建元素。如果元素类型没有默认构造函数， null 则将添加到数组或列表中。如果没有知道如何设置值的内置或自定义转换器， null 则将保留在指定索引处的数组或列表中。下面的示例演示如何自动增加列表。

        //各种边界、场景适配扩展,各种构造重载，各种auto自动化
        SpelParserConfiguration spelParserConfiguration = new SpelParserConfiguration(true,true);
        SpelExpressionParser spelExpressionParser = new SpelExpressionParser(spelParserConfiguration);
        Expression expression = spelExpressionParser.parseExpression("auto1[4]");
        System.out.println("==>>>"+expression.getValue(simple));


        //SpEL Compilation SpEL 编译
        //Spring 为 SpEL 表达式提供了一个基本的编译器。表达式通常被解释，这在评估过程中提供了很大的动态灵活性，但不能提供最佳性能。对于偶尔的表达式使用，这很好，但是，当由其他组件（如 Spring Integration）使用时，性能可能非常重要，并且实际上不需要动态。
        //SpEL 编译器旨在满足这一需求。在评估期间，编译器会生成一个 Java 类，该类在运行时体现表达式行为，并使用该类实现更快的表达式计算。由于在表达式周围缺少键入，编译器在执行编译时使用在表达式的解释式计算期间收集的信息。例如，它不能纯粹从表达式中知道属性引用的类型，但在第一次解释的计算期间，它会找出它是什么。当然，如果各种表达式元素的类型随时间变化，基于此类派生信息进行编译可能会在以后造成麻烦。因此，编译最适合于其类型信息在重复计算时不会更改的表达式。
        //默认情况下，编译器不处于打开状态，但您可以通过两种不同方式之一打开它。您可以使用解析器配置过程（前面讨论过）或在 SpEL 用法嵌入到另一个组件中时使用 Spring 属性来打开它。本节将讨论这两个选项。
        //org.springframework.expression.spel.SpelCompilerMode 三种模式之一下运行
                //OFF （默认值）：编译器已关闭。  IMMEDIATE ：在即时模式下，表达式会尽快编译。这通常是在第一次解释评估之后。如果编译的表达式失败（通常是由于类型更改，如前所述），则表达式计算的调用方将收到异常
                //在混合模式下，表达式会随着时间的推移在解释模式和编译模式之间静默切换。在经过一定次数的解释性运行后，它们将切换到已编译的表单，如果编译的表单出现问题（例如，如前所述，类型更改），表达式将再次自动切换回解释式表单。稍后，它可能会生成另一个编译的表单并切换到它。基本上，用户在模式下 IMMEDIATE 获得的异常是在内部处理的。
                //IMMEDIATE 模式之所以存在，是因为 MIXED 模式可能会导致具有副作用的表达式出现问题。如果编译后的表达式在部分成功后爆炸，则它可能已经执行了影响系统状态的操作。如果发生这种情况，调用方可能不希望它在解释模式下以静默方式重新运行，因为部分表达式可能会运行两次。
        //指定编译器模式时，还可以指定（ ClassLoader 允许传递 null ）。编译的表达式在根据提供的任何项创建的子 ClassLoader 项中定义。重要的是要确保，如果指定了 a ClassLoader ，它可以看到表达式计算过程中涉及的所有类型。如果未指定 ClassLoader ，则使用默认 ClassLoader 值（通常是在表达式计算期间运行的线程的上下文 ClassLoader ）。

        SpelParserConfiguration config = new SpelParserConfiguration(SpelCompilerMode.IMMEDIATE,
                SpringElHot.class.getClassLoader());
        SpelExpressionParser parser9 = new SpelExpressionParser(config);
        Expression expr = parser9.parseExpression("payload");

        //Compiler Limitations 编译器限制


        SpelExpressionParser parser1 = new SpelExpressionParser();
        Expression expression1 = parser1.parseExpression("1 + 2");
        String value2 = expression1.getValue(String.class);
        System.out.println(value2);

        System.out.println(parser1.parseExpression("null").getValue());


        User user1 = new User("1", 1);
        //Property or field 'user1' cannot be found on object of type 'spring.User' - maybe not public or not valid?
//        Expression expression2 = parser1.parseExpression("user1.username");
        //控制反转的感觉，框架的感觉，动态的感觉
        Expression expression2 = parser1.parseExpression("username");
        String value1 = expression2.getValue(user1, String.class);
        System.out.println(value1);

        // List and Array navigation
        // evaluates to "Wireless communication"
        //String invention = parser.parseExpression("members[0].inventions[6]").getValue(context, user1, String.class);

        //map
        // evaluates to "Idvor"
        //String city = parser.parseExpression("officers['president'].placeOfBirth.city").getValue(String.class);
        // setting values  map-key-v list - obj - placeOfBirth - country
        //parser.parseExpression("officers['advisors'][0].placeOfBirth.country").setValue(user1,"Croatia");

        Expression expression3 = parser1.parseExpression("{a,bc,c,d}");
        //Property or field 'a' cannot be found on null  --> 定义的变量无法找到对应的对象
//        String value3 = expression3.getValue(String.class);


        Expression expression4 = parser1.parseExpression("{1,3,34,5}");
//        StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();
        List value3 = expression4.getValue(List.class);
        System.out.println(value3);

        //为什么这里可以使用a,b 这些,而上面不行？
        Expression expression5 = parser.parseExpression("{{'a','b'},{'x','y'}}");
        System.out.println(expression5.getValue(List.class));

        Map mapOfMaps = (Map) parser.parseExpression("{name:{first:'Nikola',last:'Tesla'},dob:{day:10,month:'July',year:1856}}").getValue();
        System.out.println(mapOfMaps);


        int[] numbers1 = (int[]) parser.parseExpression("new int[4]").getValue(context);

// Array with initializer
        int[] numbers2 = (int[]) parser.parseExpression("new int[] {1, 2, 3}").getValue(context);

// Multi dimensional array
        int[][] numbers3 = (int[][]) parser.parseExpression("new int[4][5]").getValue(context);
        System.out.println(numbers1);
        System.out.println(numbers2);
        System.out.println(numbers3);


        //为什么还么有转列表，不是16个，如果按照取余
        Map<String, Integer> mm = new HashMap<>();
        Integer put = mm.put("1", 1);
        Integer put29 = mm.put("2", 2);
        Integer put28 = mm.put("3", 2);
        Integer put27 = mm.put("4", 2);
        Integer put26 = mm.put("5", 2);
        Integer put25 = mm.put("6", 2);
        Integer put24 = mm.put("7", 2);
        Integer put23 = mm.put("8", 2);
        Integer put22 = mm.put("9", 2);
        Integer put21 = mm.put("10", 2);
        Integer put11 = mm.put("11", 2);
        Integer put12 = mm.put("12", 2);
        Integer put13 = mm.put("13", 2);
        Integer put14 = mm.put("14", 2);
        Integer put15 = mm.put("15", 2);
        Integer put16 = mm.put("16", 2);
        Integer put17 = mm.put("17", 2);
        Integer put18 = mm.put("18", 2);
        Integer put19 = mm.put("19", 2);


        int i = 3 & 1;

        int i2 = 3 & 2;
        System.out.println(i);
        System.out.println(i2);

        Integer integer = mm.get("1");


    }



}
