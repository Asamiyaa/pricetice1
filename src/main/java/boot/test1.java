package boot;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * Created by yangwenjun on 2022/9/6 17:14
 */
public class test1 {
  public static void main(String[] args) {

    List input = Lists.newArrayList();
    callA(input);
    System.out.println("===>"+input.size());

    System.out.println("---------------");
    BiMap biMap = HashBiMap.create();
    biMap.put(1, 2);
    biMap.put(2, 4);
    System.out.println(biMap);

    System.out.println(biMap.inverse());

    System.out.println("-------------------");

    HashMap<Object, Object> objectObjectHashMap = Maps.newHashMap();
    objectObjectHashMap.put(1, 2);
    objectObjectHashMap.put(1, 4);
    System.out.println(objectObjectHashMap);

    System.out.println("===>>>>"+"2022-01-01".compareTo("2019-04-01"));
    System.out.println(DateTimeUtils.getCurYearFirst("yyyy-MM-dd"));
    System.out.println("++++>>>>"+DateTimeUtils.getDaysBetweenDates("2021-08-30", "2022-09-02"));

    if (!(true) && true){
      System.out.println("and");
    }

    System.out.println(isDateVail("20220801"));

    People people = new People();

    // 测试数组的复制
    People[] member = new People[3];
    member[0] = new People(1, "老师", 30, 1);
    member[1] = new People(2, "班长", 15, 1);
    member[2] = new People(3, "学生", 15, 1);
    People[] member1 = new People[]{};
    BeanUtils.copyProperties(member, member1);
    System.out.println("是否可以复制数组：" + (member1.length != 0));



    // 测试List的复制（Map也不能复制，测试略）
    List<People> student = new ArrayList<>();
    student.add(member[1]);
    student.add(member[2]);
    List<People> student1 = new ArrayList<>();
    BeanUtils.copyProperties(student, student1);
    System.out.println("BeanUtils.copyProperties是否可以复制List：" + (!student1.isEmpty()));



    // 通过JSON工具实现List的复制（不仅仅是List，数组和Map等也可以通过类似方法实现复制，需要有无参构造方法，否则报错）
    student1 = JSON.parseArray(JSON.toJSONString(student), People.class);
    System.out.println("通过JSON工具复制List：" + student1);
    System.out.println("通过JSON工具是否深复制：" + (student.get(0) != student1.get(0)));


    // 测试是否深复制
    Class1 source = new Class1();
    source.setMember(member);
    source.setTeacher(member[0]);
    source.setStudent(student);
    Class1 target = new Class1();
    BeanUtils.copyProperties(source, target);
//    System.out.println("BeanUtils.copyProperties是否深复制：" + (source.getMember() != target.getMember()));
    System.out.println(JSON.toJSONString(target));

    Class1 aClass = JSON.parseObject(JSON.toJSONString(source, SerializerFeature.DisableCircularReferenceDetect), Class1.class);
    System.out.println(JSON.toJSONString(aClass));
  }

  private static void callA(List input) {
    input = Lists.newArrayList();
    int size = input.size();
    for (Object o : input) {
      
    }
    input.add(1);

  }

  public static final String PATTERN_DEFAULT = "yyyy-MM-dd";
  private static Boolean isDateVail(String date) {
//    DateTimeFormatter dtf = DateTimeFormatter.ofPattern(PATTERN_DEFAULT);
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    boolean flag = true;
    try {
//      LocalDateTime.parse(date, dtf);
      formatter.parse(date);
    } catch (Exception e) {
      System.out.println(e);
      flag = false;
    }
    return flag;
  }
}


//@Data
 class Class1 {
  private People[] member;
  private People teacher;
//  @Transient
  private List<People> student;

  public People[] getMember() {
    return member;
  }

  public void setMember(People[] member) {
    this.member = member;
  }

  public People getTeacher() {
    return teacher;
  }

  public void setTeacher(People teacher) {
    this.teacher = teacher;
  }

  public List<People> getStudent() {
    return student;
  }

  public void setStudent(List<People> student) {
    this.student = student;
  }
}


class People {
 private Integer id;
 private String name;
 private Integer age;
 private Integer sex;

  public People(Integer id, String name, Integer age, Integer sex) {
    this.id = id;
    this.name = name;
    this.age = age;
    this.sex = sex;
  }

  public People() {
  }


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public Integer getSex() {
    return sex;
  }

  public void setSex(Integer sex) {
    this.sex = sex;
  }
}