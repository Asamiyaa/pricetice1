package spring;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(value={CONSTRUCTOR, FIELD, LOCAL_VARIABLE, METHOD, PACKAGE, PARAMETER, TYPE})  //场景 扩展
@Nonnull
@Component
@Service
@Inherited
@interface testAn{    //注解的组合

    String name() default "test";
}


@testAn(name = "Base Class Annotation")
class BaseClass {
    // Base class code
}

class SubClass extends BaseClass {   // 继承了注解
    // Sub class code
}


@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@interface CustomTransactional {
    // 自定义属性
    boolean readOnly() default false;
}

@Target({ElementType.METHOD, ElementType.TYPE, FIELD})
@Retention(RetentionPolicy.RUNTIME)
@CustomTransactional @interface MyServiceAnnotation {        //组合注解是通过在一个注解上应用其他注解来实现复用和扩展的。组合注解可以有效地减少样板代码，提高代码的可读性和可维护性。
    //通过元注解和组合注解，可以在实际开发中实现类似继承的效果，使得注解更加灵活和强大
    // 自定义属性
    String description() default "";
}

@Service
@MyServiceAnnotation(description = "This is a service layer")
 class MyService {
    @Override
    public String toString() {
        return "MyService";
    }
}

