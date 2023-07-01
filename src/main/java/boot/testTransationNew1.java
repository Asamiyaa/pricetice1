package boot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * Created by yangwenjun on 2023/4/19 10:55
 */
@Component
public  class testTransationNew1 {

  @Autowired
  JdbcTemplate jdbcTemplate;
  @Autowired
  PlatformTransactionManager manager;

  @Autowired
  DataSource dataSource;

  @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
  public int save1() throws InterruptedException, SQLException {
    //方式一:设置非自动提交
//    DefaultTransactionDefinition dt = new DefaultTransactionDefinition();
//    TransactionStatus status = manager.getTransaction(dt);
//    jdbcTemplate.getDataSource().getConnection().setAutoCommit(false);


    //方式二:jdbc模式
//    String JDBC_DRIVER = "org.h2.Driver";
//    String JDBC_URL = "jdbc:h2:tcp://localhost:9999/dbtest?useSSL=false&serverTimezone=UTC";
//    //数据库的用户名与密码
//    String USER = "sa";
//    String PASS = "sa";

    /**
     * org.h2.jdbc.JdbcSQLException: Connection is broken: "unexpected status 1213486160" [90067-197]
     * 	at org.h2.message.DbException.getJdbcSQLException(DbException.java:357)
     */
//    Connection connection = null;
//    Statement statement = null;
//    try {
//      Class.forName(JDBC_DRIVER);
//      connection = DriverManager.getConnection(JDBC_URL,USER,PASS);
//      connection.setAutoCommit(true);
//      statement = (Statement) connection.createStatement();
//      String sql = "insert into test values(1,100)";
//      boolean execute = statement.execute(sql);
////      //展开查询到的数据
////      while(rSet.next()) {
////        //这里getString()方法中的参数对应的是数据库表中的列名
////        String get_name = rSet.getString("uuid");
////        String get_sex = rSet.getString("Address");
////        //输出数据
////        System.out.println("主键:"+get_name);
////        System.out.print("地址:"+get_sex);
////      }
//      //依次关闭对象
////      rSet.close();
//      statement.close();
//      connection.close();
//    } catch (Exception e) {
//      e.printStackTrace();
//    }

//    int update = jdbcTemplate.update("insert into test values(1,100)");

    Connection connection = dataSource.getConnection();
    connection.setAutoCommit(false);
    Statement statement = connection.createStatement();
    boolean execute = statement.execute("insert into test values(1,100)");
//    connection.commit();  //可以提交


//    Thread.sleep(5000);
    System.out.println(" ==>>>  save1 done");

    return 1;


  }

}