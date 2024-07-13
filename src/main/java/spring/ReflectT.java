package spring;

import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.Arrays;

public class ReflectT {

    public static void main(String[] args) throws NoSuchFieldException, NoSuchMethodException {

        Person person = new Person(); person.setName("2");

        //Class -> [interface | enum | annotaion | class | ] -> Field|Contructor|Method|Parameter|Modifier(从这些维度出发看类、对象、调用)->
        // Executable(可执行定义)| TypeVariable(泛型)| AnnotatedType(注解) - 泛型编程  ->  Type|Member|AnnotatedElement
        //Executable(是 Method 和 Constructor 的公共超类。它提供了对方法和构造函数的通用操作，可以用来获取方法和构造函数的详细信息，并动态调用它们)

        //TypeVariable| AnnotatedType反射机制中的两个接口，它们提供了对类型变量和注释类型的详细信息。它们在泛型编程和类型注释的场景中非常有用，帮助开发者在运行时获取类型信息和注释信息
        //TypeVariable 是一个接口，表示一个类型变量，是泛型声明的一部分。类型变量可以用于类、接口、方法或构造函数声明中，表示一个泛型类型参数。泛型编程：在泛型类或方法中使用类型变量，使代码能够处理不同类型的数据。
        //AnnotatedType 使用类型时的实际类型及其附加注释。它包含了在泛型类型和类型注释中的元数据。类型注释处理：获取并处理类型注释，特别是在框架和库中用于注解处理。泛型类型处理：在处理泛型类型时，获取和处理类型上的注释信息，增强类型检查和安全性。
        //TypeVariable 和 AnnotatedType 是 Java 反射机制中的重要接口，分别用于表示泛型类型参数和类型注释。它们在泛型编程和类型注释处理中非常有用，帮助开发者在运行时获取详细的类型信息和注释信息。这些接口在编写通用代码、框架和库开发中发挥了重要作用。理解和掌握它们的使用，可以提高代码的灵活性和可维护性。

        //Type:表示所有类型，包括类、接口、原始类型、数组类型、类型变量和泛型类型。 接口本身没有定义任何方法，它是一个标记接口.具体的类型操作方法由它的实现类提供，例如 Class, ParameterizedType, GenericArrayType, TypeVariable 和 WildcardType。
        //泛型编程：在泛型编程中，Type 接口及其子接口用于表示和操作泛型类型。  反射操作：通过反射获取方法、字段和构造函数的参数和返回类型等信息。
        //Member 是一个接口，用于表示类的成员，包括字段、方法和构造函数。Field, Method 和 Constructor 类都实现了这个接口。
        //反射操作：通过反射获取类的成员信息，如字段、方法和构造函数。 动态调用：通过反射动态调用类的成员方法和访问字段。
        //AnnotatedElement 是一个接口，用于表示可以包含注解的程序元素，包括 Class, Method, Field, Constructor, Parameter 等。
        //注解处理：通过反射获取类、方法、字段等上的注解信息，进行注解处理   框架开发：许多框架（如Spring）使用注解来配置和管理组件，通过 AnnotatedElement 接口获取注解信息。


        //源码中许多地方重写了Type的实现，通过重写将自己的方案实现
        //Class以上都是接口，定义；只有他是实现类, 通过Class类型标识链接ParameterizedType, GenericArrayType, TypeVariable


        //想要任何都可以通过class相关API定位到
        //J 、L和Z。这三个需要特别记忆一下：J表示long；L表示对象；Z表示boolean。

        //定义类传对象 -> 定义泛型传递多种类型对象 -> 定义class传递类型 。 增加对对象和类型的认识，传递的不仅仅是对象，传递的也是类型
        //每个类型都是Class,又可以持有class ; 持有Object相当于持有任意类型的对象(具体就是指定类型);持有Class相当于持有任意类型的类型Class<T> 。对象的类型，类型的类型
        //树结构的嵌套，Method类持有Class<T>,普通的方法可以传递Class<T> , 所以这是在以小树是Class的结构下，嵌套组成更大的树 + 组合模式(主子结构 自己持有自己或者自己的组合) ==>> 现实世界
        //上面的主子结构，就像算法中的递归，
        Class<Person> personClass = Person.class;
        System.out.println(personClass.getDeclaredField("name"));                 //public java.lang.String com.yonyou.einvoice.MockHttp$Person.name
        System.out.println(personClass.getName());                                      //com.yonyou.einvoice.MockHttp$Person   配合数据库串，反射获取对象加载
        System.out.println(personClass.getDeclaringClass());                            //class com.yonyou.einvoice.MockHttp
        System.out.println(personClass.getAnnotatedInterfaces());                       //[Ljava.lang.reflect.AnnotatedType;@783e6358
        System.out.println(personClass.getAnnotatedSuperclass());                       //sun.reflect.annotation.AnnotatedTypeFactory$AnnotatedTypeBaseImpl@17550481
        System.out.println(personClass.getAnnotation(Deprecated.class));                //@java.lang.Deprecated()
        System.out.println(personClass.getCanonicalName());                             //com.yonyou.einvoice.MockHttp.Person
        System.out.println(personClass.getClasses());                                   //[Ljava.lang.Class;@68837a77
        System.out.println(personClass.asSubclass(Object.class));                       //class com.yonyou.einvoice.MockHttp$Person
        System.out.println(personClass.cast(new Person()));                             //com.yonyou.einvoice.MockHttp$Person@6be46e8f


        System.out.println(personClass.getDeclaredConstructor(String.class));           //public com.yonyou.einvoice.MockHttp$Person(java.lang.String)

        System.out.println(personClass.getEnclosingClass());                            //class com.yonyou.einvoice.MockHttp
        System.out.println(personClass.getEnclosingMethod());                           //null
        System.out.println(personClass.getEnclosingConstructor());                      //null
        System.out.println(personClass.getEnumConstants());                             //null
        System.out.println(personClass.getGenericInterfaces());                         //[Ljava.lang.Class;@3567135c
        System.out.println(personClass.getGenericSuperclass());                         //class java.lang.Object
        System.out.println(personClass.getPackage());                                   //package com.yonyou.einvoice
        System.out.println(personClass.getProtectionDomain());                          //ProtectionDomain  (file:/D:/piaoeda-cloud06/service-input-tax/target/test-classes/ <no signer certificates>)sun.misc.Launcher$AppClassLoader@18b4aac2<noprincipals>java.security.Permissions@612679d6(("java.lang.RuntimePermission" "exitVM")("java.io.FilePermission" "\D:\piaoeda-cloud06\service-input-tax\target\test-classes\-" "read"))

        System.out.println(personClass.getResource("a"));                         //null
        System.out.println(personClass.getResourceAsStream("a"));                 //null
        System.out.println(personClass.getTypeName());                                  //com.yonyou.einvoice.MockHttp$Person
        System.out.println(personClass.getTypeParameters());                            //[Ljava.lang.reflect.TypeVariable;@66d1af89
        System.out.println(personClass.getAnnotationsByType(Deprecated.class));         //[Ljava.lang.Deprecated;@52f759d7
        System.out.println(personClass.getDeclaredAnnotationsByType(Deprecated.class)); //[Ljava.lang.Deprecated;@52f759d7
        System.out.println(personClass.getClasses());                                   //[Ljava.lang.Class;@192d3247
        System.out.println(personClass.getSimpleName());                                //Person
        System.out.println(personClass.getSuperclass());                                //class java.lang.Object


        System.out.println(personClass.getDeclaredMethods());                           //[Ljava.lang.reflect.Method;@3ecd23d9
        System.out.println(personClass.getConstructors());                              //[Ljava.lang.reflect.Constructor;@569cfc36
        System.out.println(personClass.getComponentType());                             //null
        System.out.println(personClass.getAnnotations());                               //[Ljava.lang.annotation.Annotation;@64f6106c
        System.out.println(personClass.getModifiers());                                 //8
        System.out.println(personClass.getDeclaredFields());                            //[Ljava.lang.reflect.Field;@4e04a765



        Arrays.stream(Person.class.getDeclaredFields()).forEach(x -> {
            System.out.println(x.getName());                            //name

            //类、对象、反射产生的对象
            //动态设置对象值，从流程、入参中计算
            try {
                System.out.println(x.get(person));                      //2
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            try {
                x.set(person,"333");
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            //从对象值比对 equals -> 对象比对 equals -> 对象和类 instance of | String.class.isInstance(s3) -> 类比对比如这里的String,注解 Type? -> convert
            //类与类 : lass1.isAssignableFrom(class2) 判定 class1对象所表示的类或接口与class2 参数所表示的类或接口是否相同，或是否是其超类或超接口。如果是则返回 true；否则返回 false。如果该 class1表示一个基本类型，且指定的 class2参数正是class1对象，则该方法返回 true；否则返回 false。

            System.out.println(x.getAnnotatedType());                   //sun.reflect.annotation.AnnotatedTypeFactory$AnnotatedTypeBaseImpl@2ef5e5e3
            System.out.println(x.getAnnotationsByType(Deprecated.class));//[Ljava.lang.Deprecated;@548a9f61
            System.out.println(x.getDeclaringClass());                  //class com.yonyou.einvoice.MockHttp$Person   $内部类
            System.out.println(x.getGenericType());                     //class java.lang.String
            System.out.println(x.getModifiers());                       //1
            System.out.println(x.getType());                            //class java.lang.String
            System.out.println(x.getDeclaredAnnotationsByType(Deprecated.class)); //[Ljava.lang.Deprecated;@1753acfe

            System.out.println(x.isEnumConstant());                     //false
            System.out.println(x.isSynthetic());                        //false
            System.out.println(x.isAccessible());                       //false
            System.out.println(x.isAnnotationPresent(Deprecated.class)); //true

            System.out.println(x.toGenericString());                    //public java.lang.String com.yonyou.einvoice.MockHttp$Person.name

            System.out.println(person);

        });


        Arrays.stream(Person.class.getDeclaredMethods()).forEach(x ->{

            System.out.println(x.getName());                                             //getName
            System.out.println(x.getDeclaringClass());                                   //class com.yonyou.einvoice.MockHttp$Person
            System.out.println(x.getModifiers());                                        //1
            System.out.println(x.getAnnotation(InputConctrl1.class));                    //@com.yonyou.einvoice.MockHttp$InputConctrl1(conCtrlField=busiConCtrlVOs, whichArg=0)
            System.out.println(x.getAnnotationsByType(InputConctrl1.class));             //[Lcom.yonyou.einvoice.MockHttp$InputConctrl1;@55f3ddb1
            System.out.println(x.getAnnotations());                                      //[Ljava.lang.annotation.Annotation;@8bd1b6a
            System.out.println(x.getAnnotatedReturnType());                              //sun.reflect.annotation.AnnotatedTypeFactory$AnnotatedTypeBaseImpl@18be83e4
            System.out.println(x.getDeclaredAnnotations());                              //[Ljava.lang.annotation.Annotation;@cb5822
            System.out.println(x.getDeclaredAnnotation(InputConctrl1.class));            //@com.yonyou.einvoice.MockHttp$InputConctrl1(conCtrlField=busiConCtrlVOs, whichArg=0)
            System.out.println(x.getDefaultValue());                                     //null
            System.out.println(x.getExceptionTypes());                                   //[Ljava.lang.Class;@4b9e13df
            System.out.println(x.getGenericExceptionTypes());                            //[Ljava.lang.Class;@2b98378d
            System.out.println(x.getGenericParameterTypes());                            //[Ljava.lang.Class;@475530b9
            System.out.println(x.getGenericReturnType());                                //class java.lang.String
            System.out.println(x.getParameterAnnotations());                             //[Ljava.lang.Class;@4c70fda8
            System.out.println(x.getParameters());                                       //[Ljava.lang.reflect.TypeVariable;@224edc67
            System.out.println(x.getGenericParameterTypes());                            //[Ljava.lang.Class;@14acaea5
            System.out.println(x.getTypeParameters());                                  //[Ljava.lang.reflect.TypeVariable;@224edc67
            System.out.println(x.getParameterTypes());                                  //[Ljava.lang.Class;@14acaea5
            System.out.println(x.getParameterCount());                                  //0
            System.out.println(x.getAnnotatedParameterTypes());                         //[Ljava.lang.reflect.AnnotatedType;@46d56d67
            System.out.println(x.getReturnType());                                      //class java.lang.String
            System.out.println(x.getAnnotatedReceiverType());                           //sun.reflect.annotation.AnnotatedTypeFactory$AnnotatedTypeBaseImpl@d8355a8


        });

        System.out.println("--------------------------------");
        Arrays.stream(Person.class.getMethod("getName123",String.class,Integer.class).getParameters()).forEach(
                param -> {
                    System.out.println(param.getName());                               //name
                    System.out.println(param.getType());                               //class java.lang.String
                    System.out.println(param.getModifiers());
                    System.out.println(param.getParameterizedType());                  //class java.lang.String
                    System.out.println(param.getAnnotations());                        //[Ljava.lang.annotation.Annotation;@299a06ac
                    System.out.println(param.getAnnotation(Deprecated.class));         //@java.lang.Deprecated()
                    System.out.println(param.getAnnotatedType().getAnnotation(Deprecated.class)); //null
                    System.out.println(param.getDeclaringExecutable());                 //public java.lang.String spring.ReflectT$Person.getName123(java.lang.String,java.lang.Integer)
                }
        );

        System.out.println("--------------------------------");
        System.out.println(Person.class.getAnnotation(InputConctrl1.class).conCtrlField());
        System.out.println(Person.class.getAnnotation(InputConctrl1.class).whichArg());


        printGenericType(Person.class);

    }


//使用反射处理泛型类型信息
    public static void printGenericType(Class<?> clazz) {
        Type superclass = clazz.getGenericSuperclass();
        if (superclass instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) superclass;
            Type[] typeArguments = parameterizedType.getActualTypeArguments();
            for (Type typeArgument : typeArguments) {
                System.out.println("--");
                System.out.println(typeArgument.getTypeName());
            }
        }
    }


    @Target({ElementType.METHOD,ElementType.PARAMETER,ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    @Documented
    public @interface InputConctrl1 {

        int whichArg() default 0;

        String conCtrlField() default "busiConCtrlVOs";
    }

    static class Father<String>{

    }

    @Deprecated
    @InputConctrl1
    static class Person extends Father{
        @Deprecated
        public String name;
        @InputConctrl1
        public String getName() {
            return name;
        }
        public String getName123(@InputConctrl1 String name,Integer id) {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Person(String name) {
            this.name = name;
        }

        public Person() {
        }
    }





}

class TypeVariableExample<T extends Number & Comparable<T>> {

        public static void main(String[] args) {
            try {
                // 获取类对象
                Class<TypeVariableExample> clazz = TypeVariableExample.class;

                // 获取类型变量
                TypeVariable<?>[] typeParameters = clazz.getTypeParameters();
                for (TypeVariable<?> typeParameter : typeParameters) {
                    System.out.println("Name: " + typeParameter.getName());                 //T

                    // 获取上界
                    Type[] bounds = typeParameter.getBounds();
                    for (Type bound : bounds) {
                        System.out.println("Bound: " + bound.getTypeName());               //java.lang.Number  java.lang.Comparable<T>
                    }

                    // 获取声明
                    GenericDeclaration declaration = typeParameter.getGenericDeclaration();
                    System.out.println("Declared by: " + declaration);                     //class spring.TypeVariableExample

                    System.out.println("--------------------------");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE_USE)                                               //可以在任意地方使用
                                                                            // ElementType.ETYPE_PARAMETER注解能使用在自定义类型参数（参数的自定义类型可以是javaBean或者枚举等） class  MyClass<@myAnno String>{}
@interface ExampleAnnotation {}

 class AnnotatedTypeExample<@ExampleAnnotation T> {

    public static void main(String[] args) {
        try {
            // 获取类对象
            Class<AnnotatedTypeExample> clazz = AnnotatedTypeExample.class;

            // 获取类型变量
            TypeVariable<?>[] typeParameters = clazz.getTypeParameters();
            for (TypeVariable<?> typeParameter : typeParameters) {
                System.out.println("Type Variable: " + typeParameter.getName());                    //T

                // 获取注释类型
                AnnotatedType[] annotatedBounds = typeParameter.getAnnotatedBounds();               //java.lang.Object
                for (AnnotatedType annotatedBound : annotatedBounds) {
                    System.out.println("Bound: " + annotatedBound.getType().getTypeName());
                    Annotation[] annotations = annotatedBound.getAnnotations();
                    for (Annotation annotation : annotations) {
                        System.out.println("Annotation: " + annotation.annotationType().getName());
                    }
                }

                System.out.println("--------------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



 class AnnotatedElementExample {

    public static void main(String[] args) {
        // 获取类对象
        Class<SampleClass> clazz = SampleClass.class;

        // 获取方法注解
        try {
            Method method = clazz.getDeclaredMethod("annotatedMethod");
            printAnnotations(method);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private static void printAnnotations(AnnotatedElement element) {
        Annotation[] annotations = element.getAnnotations();
        for (Annotation annotation : annotations) {
            System.out.println("Annotation: " + annotation.annotationType().getName());
        }
    }
}

// 示例注解
@Retention(RetentionPolicy.RUNTIME)
@interface SampleAnnotation {}

// 示例类
class SampleClass {

    @SampleAnnotation
    public void annotatedMethod() {}
}