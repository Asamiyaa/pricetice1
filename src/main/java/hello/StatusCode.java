package hello;

import lombok.Getter;

@Getter
public enum StatusCode {

  SUCCESS("0000"),
  VALID("1001"),
  NO_EXSIT("1002"),
  NOT_KNOWN("9999");

  private String code;
  StatusCode(String code){this.code = code;}
}
