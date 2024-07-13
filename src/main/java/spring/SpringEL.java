package spring;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/test")
public class SpringEL {


    //static 静态属性早于spring动态加载，输出null
//    @Value("#{user.username}")
//    static String testName ;


    @Value("#{user.username}")
    String testName ;

    //用了spring后，这种加载就不具有动态性，所以只是加载一些固定的值
    //@Value和springEL的组合
    {
        //动态块也早于spring
        System.out.println("=>"+testName);
    }



//    @Value("#{user.setAge(null)}")//set方法没有返回无法再次赋值
    @Value("#{user.getAge()}")
    Integer age;
    @Value("#{user.getAgeTest(50)}")
    Integer ageGip;

    //允许你在表达式中直接定义列表。这对于临时需要列表的场景非常有用  #{[1, 2, 3, 4, 5]}  #{{'key1': 'value1', 'key2': 'value2'}}
    @Value("123")
    String fee;

    @Value("#{10 / 2}")
    Integer div;

    @Value("#{'abc'.compareTo('bb') > 1}")
    boolean comp;

    @Value("#{true || false}")
    boolean tf;


    @Value("#{user.getMap()}")
    HashMap<String, String> mm;
    @Value("#{user.getll()}")
    List<String> ll;
    // Property or field 'mm' cannot be found on object of type 'org.springframework.beans.factory.config.BeanExpressionContext' - maybe not public or not valid?
    // spring 和这种java原始都会回到  java，此时的mm等不在spring context中
//    {
//        mm = new HashMap<String, String>() {
//            {
//                put("1", "spring");
//                put("2", "Java");
//            }
//        };
//    }
//    List<String> ll;
//    {
//        ll = new ArrayList<String>(){
//            {
//                add("yywwjj");
//            }
//        };
//    }


    //写成成员属性呢?
    //还是报，此时mm是当前对象的属性，而当前对象并没有初始完，所以不能这么写  @Value("#{mm.get("1")}")
    @Value("#{user.getMap().get('1')}")
    String mv;
    @Value("#{user.getll().get(0)}")
    String lv;

    //投影
    @Value("#{user.users().![username]}")
    List<String> names;
    @Value("#{user.users().?[age > 2]}")
    List<User> ages;

    //静态方法
    @Value("#{T(java.lang.Math).random()}")
    String stiF;

    //正则
    @Value("#{user.username matches '^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\\\.[a-zA-Z]{2,}$'}")
    String nameB;

//    自定义函数  Spring EL允许你注册自定义函数，这些函数可以在EL表达式中调用。自定义函数通过实现特定的接口或使用Spring的@Value和MethodInvokingFactoryBean来定义。
    //要实现自定义函数，你需要创建一个Java类，实现Spring的org.springframework.expression.ExpressionParser接口，或者使用Spring提供的StandardEvaluationContext来注册你的函数。

    //变量和作用域  在Spring EL中，你可以定义和使用变量。这些变量可以根据它们的作用域（如方法作用域、请求作用域、会话作用域等）进行存储和访问。
    // 设置变量（通常在Spring配置中完成）
//#set($var = 'someValue')
//
//// 使用变量
//#{$var}
//    请注意，上面的#set指令不是Spring EL标准语法的一部分，但某些Spring EL的扩展或模板引擎（如Thymeleaf）可能支持这种语法来设置变量。在纯Spring EL表达式中，变量的设置通常是通过Spring的上下文管理来完成的。


//    类型引用和类型转换
//使用T()运算符，你可以引用Java类型，并在必要时执行类型转换。这对于访问静态方法或执行类型转换特别有用
// 类型转换
//#{T(java.lang.Integer).valueOf('42')}  // 将字符串'42'转换为Integer类型

//    17. 模板文字
//    在某些情况下，你可能希望在EL表达式中使用模板文字，这些模板文字允许你插入表达式的值。虽然这不是Spring EL核心功能的一部分，但某些与Spring集成的模板引擎（如Thymeleaf或FreeMarker）提供了这种功能。
//
//            18. 安全性和限制
//    由于Spring EL非常强大，因此在使用时需要注意安全性。默认情况下，Spring会限制EL表达式的某些功能（如访问Java类、调用静态方法等），以防止潜在的安全风险。你可以通过配置来放宽这些限制，但这需要谨慎考虑。
//            ————————————————

    //如何访问成员变量,动态运行中
    @RequestMapping("/test")
    public void test(){
        System.out.println(testName);
        System.out.println(age);
        System.out.println(ageGip);
        System.out.println(fee);
        System.out.println(div);
        System.out.println(comp);
        System.out.println(tf);

        System.out.println(mm);
        System.out.println(ll);
        System.out.println(mv);
        System.out.println(lv);

        System.out.println(names);
        System.out.println(ages);

        System.out.println(stiF);
        System.out.println(nameB); //false
    }



























}

//@Data
@Component("user")
@AllArgsConstructor
@NoArgsConstructor
class User{
    public String username = "ywj";
    Integer age = 50;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public Integer getAge() {
        return 100;
    }

    //调用才会生效，spring不会自动调用
    public void setAge(Integer age) {
        this.age = 100;
    }

    //如果这里成员age没有值，就会报错，因为 ： 调用才会生效，spring不会自动调用
    public Integer getAgeTest(Integer gip){
        return Objects.isNull(age) ? 0 : age  + gip;
    }

    //
    public Map<String,String> getMap(){
        HashMap<String, String> mms = Maps.newHashMap();
        mms.put("1","spring");
        mms.put("2","java");
        return mms;
    }

    public List<String> getll(){
        return Lists.newArrayList("1");
    }

    public List<User> users(){
        User user0 = new User("2",2);
        User user1 = new User("3",3);
        User user2 = new User("4",4);
        ArrayList<User> users = Lists.newArrayList(
                user0,user1,user2
        );
        return users;
    }
}
