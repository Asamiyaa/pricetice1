package boot.spring;

import org.apache.commons.lang.ObjectUtils;

/**
 *  同时成功、同时失败
 *
 *        1.让人头痛的大事务问题到底要如何解决：https://mp.weixin.qq.com/s?__biz=MzkwNjMwMTgzMQ==&mid=2247490259&idx=1&sn=1dd11c5f49103ca303a61fc82ce406e0&source=41#wechat_redirect
 *        2.分布式事务最经典的七种解决方案：https://segmentfault.com/a/1190000040321750
 *        3.Spring事务失效的12个场景：https://z.itpub.net/article/detail/18A4D9564A61EC7AF8EAA66FCA251444
 *        4.Spring 事务事件控制 解决业务异步操作解耦 @TransactionalEventListener 和 TransactionSynchronizationManager TransactionSynchronizationAdapter
 *                  https://www.jyoryo.com/archives/155.html\
 *                  https://segmentfault.com/a/1190000004235193
 *        5.如何将一个操作“绑定到数据库事务上” ：https://blog.51cto.com/winters1224/1967234
 *        6.TCC:https://blog.csdn.net/en_joker/article/details/86983771 \ https://www.cnblogs.com/jajian/p/10014145.html
 *              https://www.zhihu.com/question/48627764
 *          SAGA:https://www.cnblogs.com/tianyamoon/p/11969089.html
 *
 *        7.分类
 *        柔性事务（如分布式事务）为了满足可用性、性能与降级服务的需要，降低一致性（Consistency）与隔离性（Isolation）的要求，遵循 BASE 理论：
 * 基本业务可用性（Basic Availability）
 * 柔性状态（Soft state）
 * 最终一致性（Eventual consistency）
 *            柔性事务的分类
 * 柔性事务分为：两阶段型、补偿型、异步确保型、最大努力通知型。
 * 两阶段型 分布式事务二阶段提交，对应技术上的 XA、JTA/JTS，这是分布式环境下事务处理的典型模式。
 * 补偿型 TCC 型事务（Try-Confirm-Cancel）可以归为补偿型。在 Try 成功的情况下，如果事务要回滚，Cancel 将作为一个补偿机制，回滚 Try 操作；TCC 各操作事务本地化，且尽早提交（没有两阶段约束）；当全局事务要求回滚时，通过另一个本地事务实现“补偿”行为。 TCC 是将资源层的二阶段提交协议转换到业务层，成为业务模型中的一部分。
 * 异步确保型 将一些有同步冲突的事务操作变为异步操作，避免对数据库事务的争用，如消息事务机制。
 * 最大努力通知型 通过通知服务器（消息通知）进行，允许失败，有补充机制。
 *        8.
 */
public class ATransaction {

  public static void main(String[] args) {

    String s = ObjectUtils.identityToString(new Object());
    System.out.println(s);

    try{
      commit1();
      commit2();
    }catch(Exception e){
      rollback1();
      rollback2();
    }finally {
      releaseConnect();
    }

  }

  /**
   * 释放资源
   */
  private static void releaseConnect() {


  }

  private static void rollback2() {

  }

  private static void rollback1() {

  }

  private static void commit2() {

  }

  private static void commit1() {

  }


}
