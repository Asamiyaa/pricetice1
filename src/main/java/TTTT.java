import boot.CC;
import cn.hutool.core.lang.Tuple;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.criteria.CriteriaBuilder.In;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.jasper.tagplugins.jstl.core.Out;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;

/**
 * Created by yangwenjun on 2022/5/27 15:38
 */
@Getter
@Setter
@NoArgsConstructor
public class TTTT {
//
//  static void method1(Consumer<String> consumer, String conStr) {
//    consumer.accept(conStr);
//  }

  public static void main(String[] args) throws InterruptedException, FileNotFoundException {

//    Consumer<String> consumer = (str) -> "1".equals(str);  //形参 、、 方法
//    consumer.accept("t"); //这里是直接写了参数 ， 如果在套一层方法，则需要掺入
//

//    System.out.println("2  2".equals("2 2"));

//    ]不等于[中信建投证券股份有限公司北京昌平昌崔路证券营业部|
//    System.out.println("中信建投证券股份有限公司北京昌平证券营业部".equals("中信建投证券股份有限公司北京昌平昌崔路证券营业部"));
//    dateStr.
//    HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
//    objectObjectHashMap.put("1","1");
//    objectObjectHashMap.put("2","2");
//    Object remove = objectObjectHashMap.remove("1");
//    System.out.println(remove);
//    System.out.println(objectObjectHashMap);

//    Tuple objects = new Tuple(1, 2, "1");
//    ArrayList<Object> ad = Lists.newArrayList();
//    adkkkkkfdfkj.add(objects);
//    ad.add(objects);
//    boolean contains = ad.contains(objects);
//    System.out.println("--"+contains);
//
//    boolean contains1 = ad.contains(new Tuple(1, 2, "1"));
//    System.out.println("--"+contains1);

    Tuple objects = new Tuple("1", "3");
    ArrayList<String> strings = Lists.newArrayList("1", "2");
    ArrayList<String> string2 = Lists.newArrayList();
//    BeanUtils.copyProperties(strings,string2);
    List<String> strings3 = JSON.parseArray(JSON.toJSONString(strings), String.class);
    System.out.println(strings3);

    System.out.println(string2);

//    Integer i1 = new Integer("1");
//    Integer v2 = new Integer("1");
//    System.out.println(i1 != v2.intValue());
//
//    Map<Object, Integer> collect = Stream.of(1, 2).collect(Collectors.toMap(x -> x ,null));

    CheckInvDto checkBillResultDTO = new CheckInvDto();
    checkBillResultDTO.setResult(0);

//      String s1 = String.valueOf(
//          checkBillResultDTO.getResult() == 0 ? 1 : checkBillResultDTO.getResult() == 2 ? 2 : "");
//
//      String s2 = checkBillResultDTO.getResult().intValue() == CheckInvResultEnum.SUCCESS.getIndex()
//          ? SuspectFlagEnum.COMPLIANCE.getValue()
//          : String.valueOf(checkBillResultDTO.getResult()).equals(SuspectFlagEnum.SUSPECT.getValue())
//              ? SuspectFlagEnum.SUSPECT.getValue() : null;
//
//      System.out.println(s1);
//      System.out.println(s2);

    Integer i1 =
        CheckInvResultEnum.SUSPECT.getIndex().equals(checkBillResultDTO.getResult()) ? 1 : null;

    Integer i2 = CheckInvResultEnum.SUSPECT.getIndex().equals(checkBillResultDTO.getResult())
        ? SuspectStatusEnum.NOT_DEAL.getValue() : null;

    System.out.println(i1);
    System.out.println(i2);

//    Map mm = new HashMap();
////    mm.keySet().toArray()[0];
//
////    String i = "1111";
//////    enoughHuiwen3(i);
//////    System.out.println(i);
//    CC cc = new CC();
//    String s = "dfdff";
//    cc.out(s);
//
//    System.out.println(s);
//    String s = "test";
//    enoughHuiwendayu3(s);
//    System.out.println(s);

//    File file = new File("/tmp/fileservice-5772126044679651802/是佛法是佛法选会计10：3是佛法选会计10：3是佛法选会计10：3是佛法选会计10：3是佛是佛法选会计10：3是佛法选会计10：3是佛法选会计10：3是佛法选会计10：3法选会计10：3是佛法选会计10：3是佛法选会计10：3是佛法选会计10：3选会计10：30 (3).pdf.down。是佛法是佛法选会计10：3是佛法选会计10：3是佛法选会计10");
//    boolean b = file.renameTo(new File(
//        "fileservice-是佛法是佛法选会计10：3是佛法选会计10：3是佛法选会计10：3是佛法选会计10：3是佛是佛法选会计10：3是佛法选会计10：3是佛法选会计10：3是佛法选会计10：3法选会计10：3是佛法选会计10：3是佛法选会计10：3是佛法选会计10：3选会计10：30 (3).pdf"));
//
//    File file1 = new File(
//        "C:\\Users\\Administrator\\Downloads\\æä½³æä½³éä¼è®¡10ï¼3æä½³éä¼è®¡10ï¼3æä½³éä¼è®¡10ï¼3æä½³éä¼è®¡10ï¼3æä½ä½³éä¼è®¡10ï¼3æä½³éä¼è®¡10ï¼3æä½³éä¼è®¡10ï¼3æä½³éä¼è®¡10ï¼3æ³éä¼è®¡10ï¼3æä½³éä¼è®¡10ï¼3æä½³éä¼è®¡10ï¼3æä½³éä¼è®¡10ï¼3éä¼è®¡10ï¼30.pdf");
//    FileOutputStream fileOutputStream = new FileOutputStream(file1);
//    System.out.println(fileOutputStream);

//    System.out.println(file);
//    System.out.println(b);

////    longestPalindrome
//
//    //s = "babad"
//    //输出："bab"
//
//
//    //结构  （首尾对应 | 偶数 baab  奇数 bab  | 存在且顺序和 -> 保存值、idx ）|需要set -> map 记录idx，应为idx需要满足某个东西 ）
//    // |  最长  滑动窗口 - 移动
//
//    String s = "babad";
//
//    //模型 - 辅助
//    Map<Character, Integer> mm = new HashMap<>();
//    int left = 0; int right = 0 ; int max = 0; String maxS = "";
//
//    //边界  先写结构后写内容
//    if (s.length() <= 2) {
//      maxS =  "";
//    }
//
//    if (s.length() == 3) {
//      if (enoughHuiwen3(s)) {
//        maxS = s;
//      }else{
//        maxS = "";
//      }
//    }
//
//
//    //套滑动窗口进来
//    //循环中三要素 满足机制
//    //abbcbba   abba  abcba 当出现重复时，预测下一个是否等于 [与该元素重复的前面元素的前一个]，重复的元素要塞入覆盖吗？需要记录 abccdccba
//    //1.回文规律无法描述  - 无法实现    2.实现方式无法确定  滑动窗口 vs dp
//    while(right < s.length()){
//
//      if(mm.get()){
//
//      }
//
//      //相对于滑动窗口的左侧移动,回文不是立刻，而是右侧继续移动，判断是否满足回文，不满足时再移动左侧
//      //如何判断回文？  idx?加起来相同 <-- 准确描述规律
//
//      enoughHuiwendayu3(s);
//
//      mm.put();
//
//    }
//
//
//
//

//    int i = numWays(7);
//    System.out.println(i);

  }


  static enum SuspectStatusEnum {
    NOT_DEAL(1, "未处理", "UID:P_BASEDOC-BE_17E979C605400022"),
    HAS_RELEASE(2, "已放行", "UID:P_BASEDOC-BE_17E979C605400023"),
    HAS_FORBID(3, "已禁止", "UID:P_BASEDOC-BE_17E979C605400024");

    private Integer value;
    private String name;
    /**
     * name的多语码值
     */
    private String nameLang;

    public Integer getValue() {
      return value;
    }

    public void setValue(Integer value) {
      this.value = value;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    SuspectStatusEnum(Integer value, String name, String nameLang) {
      this.value = value;
      this.name = name;
      this.nameLang = nameLang;
    }

    public static String getName(int value) {
      for (SuspectStatusEnum suspectStatusEnum : values()) {
        if (value == suspectStatusEnum.getValue()) {
          return suspectStatusEnum.getName();
        }
      }
      return null;
    }

    public boolean match(Integer value) {
      return Objects.isNull(value) ? false : this.getValue().compareTo(value) == 0;
    }

    private static List<Integer> status;

    static {
      status = Arrays.stream(values()).map(SuspectStatusEnum::getValue)
          .collect(Collectors.toList());
    }

    public static boolean isSupportedStatus(Integer curStatus) {
      return Objects.isNull(curStatus) ? false : status.contains(curStatus);
    }

    public static boolean isSupportedStatus(List curStatus) {
      return CollectionUtils.isEmpty(curStatus) ? false : status.containsAll(curStatus);
    }
  }

  @Getter
  @Setter
  static class CheckInvDto {

    /**
     * 结果码
     */
    private String code;

    /**
     * 发票代码
     */
    private String fpDm;

    /**
     * 发票号码
     */
    private String fpHm;

    /**
     * 校验结果
     */
    private Integer result;

    /**
     * 原因
     */
    private String reason;

    /**
     * 针对显示疑票类型
     */
    private Integer suspectType;

    private String suspectRule;

    private String bz;

  }


  static enum SuspectFlagEnum {

    COMPLIANCE("1", "合规", "UID:P_BASEDOC-BE_17E979C605400041"),
    SUSPECT("2", "存疑", "UID:P_BASEDOC-BE_17E979C605400042"),
    UN_COMPLIANCE("3", "不合规", "UID:P_BASEDOC-BE_17E979C605400043");

    private String value;
    private String name;
    /**
     * name的多语码值
     */
    private String nameLang;

    SuspectFlagEnum(String value, String name, String nameLang) {
      this.name = name;
      this.value = value;
      this.nameLang = nameLang;
    }


    public String getValue() {
      return value;
    }


    public void setValue(String value) {
      this.value = value;
    }


    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public static String getName(String value) {
      for (SuspectFlagEnum zfztEnum : values()) {
        if (value.equals(zfztEnum.getValue())) {
          return zfztEnum.getName();
        }
      }
      return null;
    }

    public boolean match(String value) {
      return Objects.isNull(value) ? false : this.getValue().equals(value);
    }

  }

  static enum CheckInvResultEnum {
    /*
     * 成功
     */
    SUCCESS(0, "成功", "UID:P_INPUT-BE_17E9775E04900020"),
    /**
     * 失败
     */
    FAIL(1, "失败", "UID:P_INPUT-BE_17E9775E0490001E"),
    /**
     * 疑票
     */
    SUSPECT(2, "疑票", "UID:P_INPUT-BE_17E9775E0490001F");

    private Integer index;

    private String value;
    /**
     * value的多语码值
     */
    private final String valueLang;

    CheckInvResultEnum(Integer index, String value, String valueLang) {
      this.index = index;
      this.value = value;
      this.valueLang = valueLang;
    }


    public Integer getIndex() {
      return index;
    }

    public void setIndex(Integer index) {
      this.index = index;
    }

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }

    public static String getValueByIndex(Integer index) {
      for (CheckInvResultEnum resultEnum : values()) {
        if (resultEnum.getIndex().equals(index)) {
          return resultEnum.getValue();
        }
      }
      return null;
    }
  }

  private static void enoughHuiwendayu3(String s) {
    method4(s);

  }

  private static void method4(String s) {
    s = "s" + "232";
  }


  private static boolean enoughHuiwen3(String s) {
    s = s + "222222";

    return String.valueOf(s.charAt(0)).equals(String.valueOf(s.charAt(2)));

  }

  public static int numWays(int n) {
    if (n == 1) {
      return 1;
    }
    if (n == 2) {
      return 2;
    }
    return numWays(n - 1) + numWays(n - 2);
  }

}
