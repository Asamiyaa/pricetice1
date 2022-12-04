package component;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Created by yangwenjun on 2021/10/18 13:54
 */

//@ConditionalOnMissingBean(name={"testCon"})
//@ConditionalOnClass(TestCondition.class)
//@ConditionalOnExpression("1==1")
@ConditionalOnProperty(name="a.b",havingValue = "true",matchIfMissing = true)
@Component
public class TestCondition {

  public void test(){
    System.out.println("testConditon");
  }
}
