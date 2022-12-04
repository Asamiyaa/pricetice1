package hello;

import Common.Result;
import component.Test;
import component.TestCondition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//@RestController("helloController")
public class HelloController implements ApplicationContextAware {

  @Value("${test.a}")
  String testDQ;  // 可以读取到了

  @Value("${test.b}")
  String testDQb;  // 可以读取到了



  //  @Value("${test.json}") //java.lang.IllegalStateException: Cannot convert value of type 'java.lang.String' to required type 'java.util.Map': no matching editors or conversion strategy found
//  @Value("#{${test.json:{\"listHistoryStreamLimit\":10,\"ffmpegCaptureLimit\":10,\"recognizeTargetLimit\":10}}}")//加上# spel解析  //ested exception is org.springframework.expression.spel.SpelEvaluationException: EL1008E: Property or field 'aa' cannot be found on object of type 'org.springframework.beans.factory.config.BeanExpressionContext' - maybe not public or not valid?
//  @Value("#{${test.json:{'listHistoryStreamLimit':10,'ffmpegCaptureLimit':10,'recognizeTargetLimit':10}}}")
  @Value("#{${test.json}}") //#计算性
      Map testJson;

  @Value("#{'aaa'.concat('abc')}") //如何计算
      String testDQbbb;  //联动

  @Value("#{'${test.b}'.concat('abc')}")
  String testDQbbbc;  //联动

  @Autowired
  TestCondition testCondition;

  @Autowired
  Test test;

  ApplicationContext applicationContext;

//  @Autowired
//  Person person;

  //  @Value("abc")
  @Value("${abc}")
//  String abc;
      List abc;

  @GetMapping("/practice/hello")
  public Result hello(@RequestParam String name) throws Exception {
//    System.out.println(abc.get(0));
    System.out.println(applicationContext.getBean("myConfig"));
    System.out.println(applicationContext.getBean("Person"));

//    ApplicationContext



//    test.abc();
//
//testCondition.test();
//
//
//    System.out.println(testDQ); //aaaa
//    System.out.println(testDQb); //不是从applition中的，不能直接读取，需要在启动类上定义名字 或者 重写config类
//
//    System.out.println(testJson);
//
//    System.out.println(testDQbbbc);

    return
        Result.success(new SData(name));
  }


  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  @Data
  private class SData {
    private String name;
    private String currentTime;

    public SData(String name){
      this.name = name;
      this.currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
  }
}
