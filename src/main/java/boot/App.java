package boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.retry.annotation.EnableRetry;

//@SpringBootApplication(scanBasePackages = {"hello","load","invoice","jwt","redis","component"})
@SpringBootApplication(scanBasePackages = {"component","boot","Common","hello","load","redis","shell","utils","cache","SQL"})
//@EnableJpaRepositories(basePackages = "invoice")
@EntityScan(basePackages = "invoice")
//@PropertySource({"classpath:testDQ.properties"/*,"classpath:config/config.properties"*/})
@PropertySource({"classpath:application.properties"})
//对于不是appLition必须指定读取或者使用重写property 或者说 bootstrap中定义spring扫描哪些config  -- https://www.cnblogs.com/duanxz/p/4520627.html
//SpringCloud入门之常用的配置文件 application.yml和 bootstrap.yml区别
//https://www.cnblogs.com/BlogNetSpace/p/8469033.html
@EnableRetry
public class App {
//是因为启动的时候还不能读取到吗？
//  @Value("${test.a}")
//  String testDQ;
  @Autowired
  testTransationNew testTransationNew;
  public static void  main(String[]args) throws InterruptedException {
    SpringApplication.run(App.class,args);

    //验证能否读取resources下面不是application命名的properties
//    new App().tt();


    Thread.sleep(1000);


  }
//  private void tt(){
//    System.out.println(testDQ);   //null
//  }



}
