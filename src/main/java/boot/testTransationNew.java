package boot;

import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by yangwenjun on 2023/4/19 10:55
 */
@Component
public class testTransationNew {

  @Autowired
  testTransationNew1 testTransationNew1;

  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public void save() throws InterruptedException, SQLException {

    int i = testTransationNew1.save1();
//    int i = boot.testTransationNew.testTransationNew1.save1();

    System.out.println("---save-");
  }


}
