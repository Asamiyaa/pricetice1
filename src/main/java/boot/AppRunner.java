package boot;

import java.util.Arrays;
import java.util.Properties;
import java.util.Set;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by yangwenjun on 2022/11/9 11:17
 */
@Order(10)
@Component
public class AppRunner implements ApplicationRunner {

  @Override
  public void run(ApplicationArguments args) throws Exception {
    System.out.println("----***----"+ Arrays.toString(args.getSourceArgs()));
    Set<String> optionNames = args.getOptionNames();
    System.out.println("----___***"+optionNames);

    String foo = System.getenv("foo");
    String getenv = System.getenv("dev.name");
    System.out.println("foo is -"+foo+" name is -"+getenv);
  }

  /**
   * 配置中env中可以获取到
   */
  public static void main(String[] args) {
    String abc = System.getenv("abc");
    System.out.println("abc==>"+abc);
  }
}
