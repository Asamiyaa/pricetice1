package hello;

import boot.App;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by yangwenjun on 2021/10/26 16:41
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class TestBean {
  @Test
  public  void testImportBean(){
//    AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(MyConfig.class);
//    Person bean = annotationConfigApplicationContext.getBean(Person.class);
//    System.out.println(bean);
//    Map mapBean=  annotationConfigApplicationContext.getBeansOfType(Color.class);
//    for(String item:beanNames){
//      System.out.println("bean名称："+item+"--- 输出bean对象："+mapBean.get(item));
//    }
  }
}

