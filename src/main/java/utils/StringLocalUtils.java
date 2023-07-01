package utils;

/**
 * Created by yangwenjun on 2021/11/29 10:39
 */
public class StringLocalUtils {

    private StringLocalUtils(){}

    public static String parse(String input){
      return input + "parseEnd";
    }

  public static void main(String[] args) {
    System.out.println("中信建投证券股份有限公司北京昌平证券营业部".equals("中信建投证券股份有限公司北京昌平昌崔路证券营业部"));

    String a = "中信建投证券股份有限公司北京昌平证券营业部";
    String b = "中信建投证券股份有限公司北京昌平昌崔路证券营业部";


  }
}
