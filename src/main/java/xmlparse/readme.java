package xmlparse;

import com.google.common.collect.Lists;
import com.google.common.math.LongMath;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by yangwenjun on 2023/8/29 14:39
 *
 *  1.浅谈 Java 主流开源类库解析 XML    https://www.cnblogs.com/java-class/p/6901910.html  --> 不要看到配置就，其实就是属性bean
 *  2.相对于 编译原理 的树形，这里是简单的，本身就有结构(开始于结束是在一层).  编译原理没有结束 加减乘除 或者说场景更多
 *  3.
 *
 */
public class readme {

  public static void main(String[] args) {
//    List fplxs = null;
//    System.out.println(Optional.ofNullable(fplxs).orElse(Lists.newArrayList()));
//    Long l2 = null;
//    long l = LongMath.checkedAdd(1, l2);


//    ]不等于[新华(莎车)电力投资有限公司]
    System.out.println("新华(莎车)电力投资有限公司".equals("新华(莎车)电力投资有限公司"));
    ArrayList<String> strings = Lists.newArrayList("nontax", "invoice", "nontax");
    strings.removeAll(Collections.singletonList("nontax"));
    System.out.println(strings);
  }
}
