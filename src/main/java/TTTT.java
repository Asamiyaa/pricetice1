import com.alibaba.fastjson.annotation.JSONField;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by yangwenjun on 2022/5/27 15:38
 */
@Getter
@Setter
@NoArgsConstructor
public class TTTT {

  @JSONField(format = "yyyyMMdd")
  private Date billDate;

}
