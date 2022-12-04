package boot;

/**
 * Created by yangwenjun on 2022/7/20 15:20
 */
//package com.yonyou.einvoice.bill.enums;

/**
 * 合规校验场景  Created by yangwenjun on 2022/7/13 14:31
 */
public enum ValidateRuleScene {
  COLLECTION("Collection", "台账","P_YS_PF_GZTSYS_0000012755"),
  BILLCENTER("Billcenter", "票据中心","P_YS_PF_GZTSYS_0000012755");

  private final String code;
  private final String value;
  /**
   * value的多语码值
   */
  private String valueLang;

  ValidateRuleScene(String code, String value ,String valueLang) {
    this.value = value;
    this.code = code;
    this.valueLang = valueLang;
  }


  public static boolean contain(ValidateRuleScene scene){
    return COLLECTION.code.equals(scene) || BILLCENTER.code.equals(scene);
  }

  public static void main(String[] args) {
    System.out.println(contain(ValidateRuleScene.COLLECTION));
  }

}

