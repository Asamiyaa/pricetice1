package boot.spring;

/**
 * 与 orm 等结合
 *
 *      1.Spring里的模板方法设计模式，JdbcTemplate、RedisTemplate、RestTemplate源码分析 : https://blog.csdn.net/zhangweiocp/article/details/115486257
 *      2.RestTemplate的使用  https://cloud.tencent.com/developer/article/1508723
 *
 *  与 http 等结合
 *      1.httpClient : https://blog.csdn.net/u012230055/article/details/43567361
 *
 *      2.Httpclient  Okhttp  Httpurlconnection | RestTemplate | feign : https://www.cnblogs.com/EasonJim/p/8321355.html
 *
 *      3.OpenFeign 如何做到 "隔空取物" ？https://mp.weixin.qq.com/s/mrbxveSTNtsDrOu-IxOGsA
 *
 *      4.mybatis plugin 与 interceptor ： https://blog.csdn.net/Liu_York/article/details/88053053
 *
 */
public class ATemplate {

  public static void main(String[] args) {

    template();

  }

  private static void template() {
    System.out.println("第一步");
    System.out.println("第二步");
    customerStep();
    System.out.println("第四步");
  }

  private static void customerStep() {
    System.out.println("customer 第三步");
  }


}
