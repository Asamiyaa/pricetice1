package boot;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

  @GetMapping("/test111")
  public String test1(@RequestBody @Valid AB a){
    return "annontaion";
  }


}


@Data
class AB{
//  @NotNull
@Pattern(regexp = "06|07", message = "通行费标志的值不正确")
@Size(max = 2, message = "通行费标志长度不能超过2个字符")
  private String a;
}

