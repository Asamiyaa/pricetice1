package boot;

import com.alibaba.fastjson.JSON;
import java.util.Date;
import java.util.HashMap;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

/**
 * Created by yangwenjun on 2022/8/2 15:43
 */
@Setter
@Getter
public class Main {
    String name ;
    private Inner1 inner1;
    private Inner2 inner2;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Inner1 getInner1() {
    return inner1;
  }

  public void setInner1(Inner1 inner1) {
    this.inner1 = inner1;
  }

  public Inner2 getInner2() {
    return inner2;
  }

  public void setInner2(Inner2 inner2) {
    this.inner2 = inner2;
  }

  public static void main(String[] args) {
//      JSONObject targetObject = new JSONObject();
//      targetObject.put("filepath","aaa");
//      targetObject.put("filePath", "bbbbb");
//      A a = targetObject.toJavaObject(A.class);
//      System.out.println(a);
//      Main main = new Main();main.setName("a");
//      Inner1 inner1 = new Inner1();inner1.setA("a");
//      main.setInner1(inner1);
//      System.out.println(JSON.toJSONString(main));
//
//      Main main2 = new Main();
//      BeanUtils.copyProperties(main,main2);
//      System.out.println(JSON.toJSONString(main2));
      System.out.println("中节能太阳能科技巣湖有限公司".equals("中节能太阳能科技巢湖有限公司"));

      Main main = new Main();
      Inner1 inner1 = new Inner1();
      inner1.setA("a");
      main.setName("aaaaa");
      main.setInner1(inner1);

      Main2 main2 = new Main2();
      BeanUtils.copyProperties(main,main2);
      System.out.println(JSON.toJSONString(main2));

//      System.out.println("2".equals(null));

      Date date = new Date();
      System.out.println(date);
      String json = JSON.toJSONString(date);
      System.out.println(json);
      HashMap<String, Object> data = JSON.parseObject(json, HashMap.class);
      System.out.println("----"+data);

    }




}

@Setter
@Getter
  class Inner1{
  String a;

  public String getA() {
    return a;
  }

  public void setA(String a) {
    this.a = a;
  }
}

@Setter
@Getter
  class Inner2{
  String a;
}

@Setter
@Getter
 class Main2 {

  String name;
  private Inner2 inner2;

  public static void main(String[] args) {
//      JSONObject targetObject = new JSONObject();
//      targetObject.put("filepath","aaa");
//      targetObject.put("filePath", "bbbbb");
//      A a = targetObject.toJavaObject(A.class);
//      System.out.println(a);
//      Main main = new Main();main.setName("a");
//      Inner1 inner1 = new Inner1();inner1.setA("a");
//      main.setInner1(inner1);
//      System.out.println(JSON.toJSONString(main));
//
//      Main main2 = new Main();
//      BeanUtils.copyProperties(main,main2);
//      System.out.println(JSON.toJSONString(main2));

    Inner1 inner1 = new Inner1();
    inner1.setA("a");

    Inner2 inner2 = new Inner2();
    BeanUtils.copyProperties(inner1, inner2);
    System.out.println(JSON.toJSONString(inner2));

//      System.out.println("2".equals(null));

  }

}

@ToString
class A {

  private String filepath;
  private String filePath;

  public String getFilepath() {
    return filepath;
  }
  public void setFilepath(String filepath) {
    this.filepath = filepath;
  }

  public String getFilePath() {
    return filePath;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }


}



