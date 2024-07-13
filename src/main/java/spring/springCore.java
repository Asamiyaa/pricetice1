package spring;

import com.google.common.collect.Maps;
import component.TestAnn;
import org.springframework.core.convert.support.DefaultConversionService;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.*;
import java.util.*;

import static java.lang.annotation.ElementType.*;

@testAn(name = "one")
public class springCore {

    @testAn(name = "tone")
    @MyServiceAnnotation(description = "00")
    String testFiled;

    public static void main1(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

//        DefaultConversionService convert = new DefaultConversionService();
//
//        Integer convert1 = convert.convert("1", Integer.class);
//        System.out.println(convert1);
//
//        Optional convert2 = convert.convert("1", Optional.class);
//        System.out.println(convert2);
//
//
//        class son{
//            public void say(){
//                System.out.println("hello");
//            }
//        }
//        class father extends son{}

//        Object convert3 = convert.convert(new son(), father.class); //No converter found capable of converting from type [spring.springCore$1son] to type [spring.springCore$1father]
//        System.out.println(convert3 instanceof father);
//        System.out.println(convert3 instanceof son);

//        Method say = convert.convert(new son(), son.class.getMethod("say").getClass()); // No converter found capable of converting from type [spring.springCore$1son] to type [java.lang.reflect.Method]
//        say.setAccessible(true);
//        System.out.println(say.invoke(new son()));


//        HashMap<Object, Object> objectObjectHashMap = Maps.newHashMap();
//        objectObjectHashMap.put("1","1");
//        Object convert3 = convert.convert(objectObjectHashMap, TreeMap.class);
//        System.out.println(convert3 instanceof HashMap);  //targetType.getType().isInstance(source);
//
//
//        printGenericType(Object.class);
//        ArrayList<Integer> integers = new ArrayList<>();
//        printGenericType(integers.getClass());


        System.out.println(TestEnum.AENUM);
        TestEnum a = null;

        System.out.println(a == TestEnum.AENUM); //false;  无需判空
        System.out.println(TestEnum.AENUM.equals(a)); //false; 后者允许为空
//        System.out.println(a.equals(TestEnum.AENUM)); //npe


        EnumSet enumSet = EnumSet.allOf(TestEnum.class);
        System.out.println(enumSet);
        EnumSet<TestEnum> aenum = EnumSet.of(TestEnum.AENUM);
        System.out.println(aenum);
        EnumMap enumMap = new EnumMap(TestEnum.class);
        System.out.println(enumMap);

//        TestEnum testEnum = new TestEnum();

    }

    /**
     * 1.先写枚举api
     *  research:java enum best
     *      --> https://www.baeldung.com/a-guide-to-java-enums   https://betterprogramming.pub/when-to-and-when-not-to-use-enums-in-java-8d6fb17ba978
     *      --> https://docs.oracle.com/javase/8/docs/api/
     * 2.看如何和class更加灵活
     *      --> https://docs.oracle.com/javase/8/docs/api/
     *      --> https://www.baeldung.com/a-guide-to-java-enums
     *      --> https://www.geeksforgeeks.org/reflection-in-java/
     * 3.搜索类似实现
     *      --> https://sourcegraph.com/search
     *      --> https://searchcode.com/?q=equals+hashcode++file%3AObjectUtils&lan=23   比如：equals hashcode  file:ObjectUtils
     * 4.转换 数组、--> 更高纬，更抽象
     *      --> 自己写
     *      --> 准确描述 引导chatgpt写
     */
    enum TestEnum{
        AENUM,
        BENUM,
        ;

        public static boolean getxx(String name){
            String name1 = AENUM.name();  //利用转成string处理 而不是直接对比
            int ordinal = AENUM.ordinal();  //利用下标来得到默认的属性
            return Arrays.stream(values()).anyMatch(x -> x.name().equalsIgnoreCase(name)); //利用values获取整理list,配合static,或者 成员初始化
        }
    }

    public static <T extends Enum<T>> boolean equal(T originEnum, Object enumValue){

        if (enumValue instanceof String){
            // 1. 不知道枚举中属性，虽然这里有Enum中高维name,ordinal,values等，
            // 2. 这里配合class使用，class.getEnumConstants()
            // 3. 使用ObjectUtils ,Objects 看已经完成的实现，比如spring等，对数组、null处理
            // 4. 对String , Integer 等转换 数组、--> 更高纬，更抽象 都使用String.valueOf..通用能力

        }

        return true;
    }


        //public class Enums {
        //  private static Random rand = new Random(47);
        //
        //  public static <T extends Enum<T>> T random(Class<T> enumClazz) {
        //    return random(enumClazz.getEnumConstants());
        //  }
        //
        //  public static <T> T random(T[] values) {
        //    return values[rand.nextInt(values.length)];
        //  }
        //}








//泛型是堆叠效果，看成object处理即可；泛型只在编译器有用，为了不在运行时报错，提前检测，推断
//Java 中处理泛型的方法和技巧包括定义泛型类和泛型接口、泛型方法、有界类型参数、通配符、泛型数组、类型擦除与类型检查、泛型与反射以及泛型与集合。通过这些方法和技巧，可以编写类型安全、可重用且易于维护的代码。

//    public static <T extends Number> <B extends Number> void printNumber(T number,B b) {
    //申明            返回值前定义    定义了有界则意味这可以调用其方法，更高级别
      public static <T extends Number , B extends Number> void printNumber(T number,B b) {
        System.out.println(number);
    }

    //这种是不是只能配合通用的class进行处理
    public static void printLowerBoundedList(List<?> list) {
        for (Object element : list) {
            System.out.println(element);
        }
    }

    //Java 不支持直接创建泛型数组，但可以使用 Array.newInstance 方法间接创建。
    public static <T> T[] createArray(Class<T> clazz, int length) {
        return (T[]) Array.newInstance(clazz, length);
    }

    //Java 的泛型是通过类型擦除实现的，在运行时泛型类型信息会被擦除，因此需要注意类型检查和转换
    public static <T> void checkType(T obj) {
        if (obj instanceof String) {
            System.out.println("It's a String");
        } else {
            System.out.println("It's not a String");
        }
    }

    //反射
//    Java 中反射相关的类和接口包括 Class、Constructor、Method、Field、Array、Modifier、Proxy、InvocationHandler、AccessibleObject、Type、ParameterizedType 和 Annotation。
//    通过这些类和接口，可以在运行时动态地获取类的信息、创建类的实例、调用类的方法和访问类的字段，从而实现灵活和动态的编程。












    //================>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {


        System.out.println("--------------------------------");


        Class<springCore> springCoreClass = springCore.class;
        testAn annotation = springCoreClass.getAnnotation(testAn.class);
        System.out.println(annotation.name());

        Field testAn = springCore.class.getDeclaredField("testFiled");
        testAn.setAccessible(true);
        spring.testAn annotation1 = testAn.getAnnotation(testAn.class);
        System.out.println(annotation1.name());
        //结合reflet
        springCore springCore = new springCore();testAn.set(springCore,"2222");
        String o = (String)testAn.get(springCore);
        if (o.equalsIgnoreCase("2222") && annotation1.name().equalsIgnoreCase("tone")){
            System.out.println("--->>>>>");
        }

        MyServiceAnnotation annotation2 = testAn.getAnnotation(MyServiceAnnotation.class);
        System.out.println(annotation2.description());

        CustomTransactional annotation3 = testAn.getAnnotation(CustomTransactional.class);
        System.out.println(annotation3.readOnly());


    }





















}



