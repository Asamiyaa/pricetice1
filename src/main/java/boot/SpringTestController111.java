package boot;

import SQL.initSql;
import cache.ConCtrlDTO;
import cache.annotation.Cache;
import cache.annotation.InputConctrl;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yangwenjun on 2022/10/20 09:54
 */
@RestController
@RequestMapping("/spring")
public class SpringTestController111 {

  @GetMapping("/annotation111")
  public String test(){
    return "annontaion";
  }


  @PostMapping("/inputCtrl")
  @InputConctrl
  public String test2(@RequestBody ConCtrlDTO ctrDto){
    return "annontaion";
  }

  @PostMapping("/test111")
  @Cache
//  public String test1(@RequestBody @Valid AB a){
//    return "annontaion";
//  }
  public List<String> test1(@RequestParam List<String> a){

    /**
     * 实际操作返回
     */
    return a.stream().map(x ->{
      return "afdfsdf";
    }).collect(Collectors.toList());
  }


  @Autowired
  private SQL.initSql initSql;
  @PostMapping("/sql")
  public String sql() throws Exception {
    initSql.run(null);
    return null;
  }

}


@Data
class AB{
//  @NotNull
@Pattern(regexp = "06|07", message = "通行费标志的值不正确")
@Size(max = 2, message = "通行费标志长度不能超过2个字符")
  private String a;
}

