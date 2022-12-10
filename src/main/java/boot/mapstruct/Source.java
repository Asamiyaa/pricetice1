package boot.mapstruct;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by yangwenjun on 2022/12/10 20:22
 */
@Data
@AllArgsConstructor
public class Source {
  private String name;
  private int age;
  private InnerSource innerSource;

  public Source() {
  }
}
