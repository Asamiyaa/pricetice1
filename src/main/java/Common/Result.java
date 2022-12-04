package Common;

import lombok.Data;

@Data
public class Result<T> {
  private String code;
  private String msg;
  private T datas;

  public Result(String code, String msg, T data){
    this.code = code;
    this.msg = msg;
    this.datas = data;
  }

  public static Result success(Object data){
//    return new Result(StatusCode.SUCCESS.getCode(), "操作成功", data);
    return null;
  }
}
