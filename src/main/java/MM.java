import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.persistence.criteria.CriteriaBuilder.In;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.Builder;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.fileupload.MultipartStream.IllegalBoundaryException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFShape;

/**
 * Created by yangwenjun on 2021/9/6 16:54
 */
public class MM {
private static final String aa = "aaa";
public static final int aaa= 1;
//@SneakyThrows
  public static void main(String[] args) {

    System.out.println(String.format("合规校验不通过", "1", "2", "xxxhaoma"));

    System.out.println(Integer.valueOf("0000300"));
    System.out.println(new BigDecimal("0000300"));

    System.out.println(new Integer("1"));


//    Integer i1 = new Integer(0);
//    Integer i2 = new Integer(0);
//    System.out.println(i1.compareTo(i2) == 0);
//
//    double a = 1.5 * 1024 * 1024;
//
//
//    System.out.println((a/ (1024 * 1024)));
////    Pattern.compile(null);

//    JSONObject jsonObject = new JSONObject();
//    jsonObject.put("kprq","20220502");
//
//    //转成 - - 形式
//    JSONObject targetObject = new JSONObject();
//    targetObject.put("billDate", jsonObject.get("kprq"));
//    System.out.println(jsonObject.get("kprq"));
//    System.out.println(JSON.toJSONString(targetObject));
//    System.out.println(JSON.toJSONString(targetObject.toJavaObject(TTTT.class)));


          //83                                                  //
    //2022-05-02   ---->>  yyyy-MM-dd    转成功                转成功
    //20220502  ---->>  yyyyMMdd   失败 1970                   转成功
    //20220502  ---->>  yyyy-MM-dd   失败  1970               转成功
    //2022-05-02 ---->>  yyyyMMdd  失败  2021                 转成功










//    ArrayList<Object> list = Lists.newArrayList();
//    list.add("1");
//    list.add("2");
//    List<Object> collect = list.stream().filter(x -> x.equals("1")).collect(Collectors.toList());
//    System.out.println(collect);
//
//    BigInteger big = new BigInteger("12345678901234567890");
//    System.out.println(big.longValue());
//
//    System.out.println(String.format("%0" + 4 + "d", 7));
////    System.out.println(String.format("%0" + 4, 7));
//    System.out.println(StringUtils.leftPad("7", 4, "0"));
//
//    System.out.println("=========llll----"+"".length());
//
//    System.out.println("ab".substring(0,0));
//    System.out.println("ab".substring(0));
//
////    StringUtils.n
//    Set set = new HashSet<>();
//    set.add(1);
//    set.add(2);
//    List l = new ArrayList();
//    l.add(2);
//    Collection subtract = CollectionUtils.subtract(set, l);
//    System.out.println(subtract);
//
//
//    List ss = Lists.newArrayList();
//    ss.add(1);
//    List s1s = Lists.newArrayList();
//    s1s.add(2);
//
//    Collection intersection = CollectionUtils.intersection(ss, s1s);
//    System.out.println(intersection);
//
//    ss.removeAll(intersection);
//    System.out.println(ss);
//
////    Map<Long, String> mm = Maps.newHashMap();
//    Map<Integer, String> mm2 = Maps.newHashMap();
//
//    mm.put(1L, "1");
//    mm.put(2L, "2");
//    Integer i = 1;
//    System.out.println("===>>>>>"+mm.get(i));




//    String a = "";
//    String b = "";
//    System.out.println(a.equals(b));


//     List<Long> s = new ArrayList<>();
//    s.add(1L);
//    System.out.println(s.stream().collect(Collectors.toList()).toArray(new Integer[0]));
  }

  @SneakyThrows
static void aa(){
//  throw new IllegalBoundaryException("this is test exp");
}

List s = null;



//
////    throw new IOException("ioM");
//
//    if (null == null) {
//
//    }
//    ArrayList<Object> objects = Lists.newArrayList();
//    if (objects == null) {
//
//
//    }
//    if (StringUtils.isAllBlank("")) {
//
//    }
//    MultipartFile f = new MockMultipartFile("test","test".getBytes());
//    byte[] content = IOUtils.toByteArray(f.getInputStream());
//    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content);
//    System.out.println(f.getInputStream());
//    System.out.println(byteArrayInputStream);
//    System.out.println("====>>>>"+byteArrayInputStream.equals(f.getInputStream()));
//    System.out.println("====>>>>"+byteArrayInputStream.equals(f.getInputStream()));
//    System.out.println("====>>>>"+byteArrayInputStream.equals(f.getInputStream()));
//
//
//
//
//    System.out.println(IoUtil.contentEquals(f.getInputStream(), byteArrayInputStream));

//    System.out.println(new String((byte) 0x3C));

//    System.out.println("云南省投资控股集团有限公司".equals("云南省投资控股集团有限公司"));
//    BigDecimal bigDecimal  = new BigDecimal("46529.20");
//    BigDecimal bigDecimal2  = new BigDecimal("0.13");
//    System.out.println(bigDecimal
//        .multiply(bigDecimal2)
//        .setScale(7, RoundingMode.HALF_UP));

//    System.out.println("           --  a b  --    |".trim()); //去掉前后

//    BigDecimal se =
//        einvoiceApplyB.getXmjshj().divide(1.13, 50, RoundingMode.HALF_UP)
//            .multiply(einvoiceApplyB.getSl()).setScale(2, RoundingMode.HALF_UP);

//    String formdata = URLDecoder.decode(HttpServletUtils.getFormData(httpRequest), "UTF-8");
//    accessLog.setRequestdata(formdata);

//  }

  public static String trimTrailing(String str) {
    if (str != null) {
      for (int i = str.length() - 1; i >= 0; --i) {
        if (StringUtils.endsWith(str,"\u007F")) {

        }
        if (str.charAt(i) != ' ') {
          return str.substring(0, i + 1);
        }
      }
    } return str;
  }
}

//@AllArgsConstructor
enum AA{
  aa("bbb","vvvv");

  AA(String name, String value) {
    this.name = name;
    this.value = value;
  }

  private String name;
  private String value;
}

//@AllArgsConstructor

class cc{

  public cc(int a, String b) {
    this.a = a;
    this.b = b;
  }

  private int a;
 private String b;
//
//  @Override
//  public boolean equals(Object o) {
//    if (this == o) {
//      return true;
//    }
//    if (o == null || getClass() != o.getClass()) {
//      return false;
//    }
//    cc cc = (cc) o;
//    return a == cc.a &&
//        Objects.equals(b, cc.b);
//  }
//
//  @Override
//  public int hashCode() {
//    return Objects.hash(a, b);
//  }
}
