package boot.mapstruct;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by yangwenjun on 2022/12/10 20:21
 */
@Data
@AllArgsConstructor
public class Dest {
  private String name;
  /**
   * fastjson注解  nameFilter / contextValueFilter
   */
  private int age;
  private InnerSource innerSource;


  public Dest() {
  }
}