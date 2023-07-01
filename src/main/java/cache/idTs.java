package cache;

import lombok.Data;

/**
 * Created by yangwenjun on 2022/12/27 11:32
 */
@Data
public class idTs implements ICommonVO {

  private String ts;
  private Long id;

  @Override
  public String dbTableName() {
    return "ab_table";
  }

  @Override
  public String getTs() {
    return ts;
  }
}
