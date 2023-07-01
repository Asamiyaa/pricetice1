package SQL;

import cache.idTs;
import java.util.List;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.beans.BeansException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

/**
 * Created by yangwenjun on 2022/12/11 10:42  借助template结合其他框架使用
 */
@Component
/**
 * ApplicationRunner是在完全启动后生效，所以上一个runner初始化表在这里读取不到
 */
@Order(96)
public class initSql implements /*ApplicationRunner ,*/ ApplicationContextAware {

  private final String SQL_TMEPLATE = "select id,`name` from %s where id in(%s)";
//  private static final String SQL_TEMPLATE = "select %s id, %s ts from %s where %s in (%s) ";   这里别名  --> 加一层映射或者说转换
  /**
   * 1.jdbcTemplate 与  mybatis 框架区别   使用场景   这里类似于 mybatis中$来传入表名字 ,不写$语法的话，该组件更独立、结合从上下文中获取操作对象
   * 2.元代码结构的理解对框架扩展 。 积累框架中这些核心入口，作为拼接的、积累点  *** . 【 越往下，能力越强 】
   */
  JdbcTemplate jdbcTemplate ;


  public void run(ApplicationArguments args) throws Exception {
    /**
     * 配合切面传入信息   table信息与id信息
     */
    String sql = String.format(SQL_TMEPLATE, "TableTestA", "1,2");
    List<idTs> result = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(idTs.class));
    System.out.println("result is ===>>>"+result);
    System.out.println("----------");

  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
     jdbcTemplate = applicationContext.getBean(JdbcTemplate.class);
  }






}
