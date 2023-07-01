package boot;

import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yangwenjun on 2023/4/19 11:05
 */

@RestController
@RequestMapping("/spring")
public class CC {

  @Autowired
  testTransationNew testTransationNew;

  @GetMapping("/transction")
  public String test() throws InterruptedException, SQLException {
    testTransationNew.save();
    return "annontaion";
  }

  public void out(String s){
    s = "ss" + s;
  }
}