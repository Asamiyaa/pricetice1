import com.google.common.collect.Lists;
import io.jsonwebtoken.lang.Assert;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * Created by yangwenjun on 2023/5/25 14:45
 */
public class 函数式编程演化过程 {

    //  @Override
//  public <T, R> R failModeFunc(FailMode failMode, Function<T, R> businessFunc /**入参**/ ) {
//    public <T,R> R failModeFunc(FailMode failMode, Callable<R> callable /**入参**/ , T param) throws Exception {
//  public <T,R> R failModeFunc(FailMode failMode, Function<T,R> businessFunc /**入参**/ , T param) throws Exception {
    public <T,R> R failModeFunc(/*FailMode failMode, */Function<T,R> businessFunc /**入参**/ , T param) throws Exception {
      //入参 循环  与 failMode有关
//    return businessFunc.apply(t)

      //对于没有、或者无法改造的通过这种方式，不用强转，类似反射的获取任一属性值
      BeanWrapper beanWrapper = new BeanWrapperImpl(param);
      String failMode = (String)beanWrapper.getPropertyValue("failMode");


      if (null == failMode || FailMode.BATCH.equals(failMode)) {

        //抛出busiException
//      return callable.call();
        return businessFunc.apply(param);
      }

      else if (FailMode.SINGLE.equals(failMode)) {
        if (param instanceof List) {
          ((List) param).forEach(single -> /*callable.call()*/ businessFunc.apply((T) single));
        }
      }


      return null;
    }

  /**
   * 演化过程   ***
   *     思考failMode失败模式如何将代码抽象成template .
   *
   */
//
//  package com.yonyou.einvoice.inputtax.component;
//
//import com.yonyou.cloud.apm.internal.com.google.common.collect.Lists;
//import com.yonyou.einvoice.bill.entity.BillCommonDTO;
//import com.yonyou.einvoice.common.util.StringUtils;
//import com.yonyou.einvoice.common.util.exception.BusinessRuntimeException;
//import com.yonyou.einvoice.dto.bill.RqParsedResult;
//import com.yonyou.einvoice.inputtax.collection.dto.api.CollectionApiSaveParam;
//import com.yonyou.einvoice.inputtax.collection.dto.api.CollectionBillErrorResult;
//import com.yonyou.einvoice.inputtax.collection.dto.api.FailMode;
//import io.jsonwebtoken.lang.Assert;
//import java.util.List;
//import java.util.function.Consumer;
//
//  /**
//   * Created by yangwenjun on 2023/8/23 13:47
//   */
//  public class FailModeTemplate {
//
//    //函数式编程先从接口编程入手。接口中只有一个方法的才叫函数。细分consumer\funciton\... --> TransactionCallback
//
//
//    //T --> 包装成指定模式  Consumer中<T> 指的是入参  变与不变，变中关联在哪里-隐含（TransactionCallback<T> action T就是返回值）
//    //所以这里consumer也是可以的
////    public static <T> T execute1(
//    public static  List<CollectionBillErrorResult> execute1(
//        Consumer<List<BillCommonDTO>> consumer, String failMode,List<BillCommonDTO> dtos,//(通过new构造template将与函数匹配的参数传入类似于PlatformTransactionManager)
//        CollectionApiSaveParam param){
//      Assert.isNull(consumer,"consumer is not null");
//      Assert.isNull(param,"param is not null");
//
//      List<CollectionBillErrorResult> resultRecord = Lists.newArrayList();
//      if (FailMode.SINGLE.hit(failMode)) {
//
//        for (RqParsedResult bill : param.getBills()) {
//
//          CollectionBillErrorResult result = new CollectionBillErrorResult();
//          param.setBills(Lists.newArrayList(bill));
//
//          try{
//            consumer.accept(dtos);
//
//          }catch(BusinessRuntimeException ex){
////          log.error("saveOnlyToBillCenter error {}", EinvoiceLogUtils.logFormat(param));
//
//            recordResult(bill, result, ex ,param);
//            resultRecord.add(result);
//            continue;
//          }
//
//          recordResult(bill,result,null, param);
//          resultRecord.add(result);
//        }
//
//      } else if (StringUtils.isEmpty(failMode) || FailMode.BATCH.hit(failMode)){
//        consumer.accept(dtos);
//      }
//
//      return resultRecord;
//    }
//
//
//
//    //----final====================
//
//    private Object param;  //new时候将这些值塞入进来
//    //String failMode,List<BillCommonDTO> dtos,//(通过new构造template将与函数匹配的参数传入类似于PlatformTransactionManagerCollectionApiSaveParam param
//
//    public   List<CollectionBillErrorResult> execute1(  //不是static的，这个类也不是spring下，因为有属性。。vs jdbcTemplate 也是通过configure配置获取对象注入。。只是因为datasource对于项目
//        //是固定的。通过配置实例化；这些变化的需要new来实例化
//        Consumer consumer) {
//
//
//
////    这些分支校验都从param中获取
//      return null;
//    }
//
//
//
//
//
//    //======================
//
//
//    /***
//     *   @Nullable                                                                               //0.TransactionCallback<T>泛型定义就是返回，自己的返回就是template返回
//     *   public <T> T execute(TransactionCallback<T> action) throws TransactionException {       //0.1调用的地方就是一个类，TransactionCallback实现，new inteface ... 查看调用的地方 起始就是在需要的地方将重复的封装到了template中，变化的到函数中。对比不同场景下不同函数操作  <-- 有公用才能抽象、这里返回、入参都有变化
//     *     Assert.state(this.transactionManager != null, "No PlatformTransactionManager set");
//     *     if (this.transactionManager instanceof CallbackPreferringPlatformTransactionManager) {
//     *       return ((CallbackPreferringPlatformTransactionManager)this.transactionManager).execute(this, action);
//     *     } else {
//     *       TransactionStatus status = this.transactionManager.getTransaction(this);             //1  PlatformTransactionManager 构造注入transactionManager 这个关联不同方法提供不同参数的池子
//     *
//     *       Object result;
//     *       try {
//     *         result = action.doInTransaction(status);                                           //2.只提供行为，不涉及要素，入参    返回结果可以不用，入参也可以不用
//     *       } catch (Error | RuntimeException var5) {
//     *         this.rollbackOnException(status, var5);
//     *         throw var5;
//     *       } catch (Throwable var6) {
//     *         this.rollbackOnException(status, var6);
//     *         throw new UndeclaredThrowableException(var6, "TransactionCallback threw undeclared checked exception");
//     *       }
//     *
//     *       this.transactionManager.commit(status);                                              //3.系统
//     *       return result;
//     *     }
//     *   }
//     *
//     *
//     */
//
//    /**
//     *
//     /**
//     * 失败模式模板  -- 抽不合理地方：1.需要参数 dtos  2.需要getBills遍历  3.返回值与泛型
//     */
//    public static List<CollectionBillErrorResult> execute(
//        Consumer<List<BillCommonDTO>> consumer, String failMode,List<BillCommonDTO> dtos,
//        CollectionApiSaveParam param){
//      Assert.isNull(consumer,"consumer is not null");
//      Assert.isNull(param,"param is not null");
//
//      List<CollectionBillErrorResult> resultRecord = Lists.newArrayList();
//      if (FailMode.SINGLE.hit(failMode)) {
//
//        for (RqParsedResult bill : param.getBills()) {
//
//          CollectionBillErrorResult result = new CollectionBillErrorResult();
//          param.setBills(Lists.newArrayList(bill));
//
//          try{
//            consumer.accept(dtos);
//
//          }catch(BusinessRuntimeException ex){
////          log.error("saveOnlyToBillCenter error {}", EinvoiceLogUtils.logFormat(param));
//
//            recordResult(bill, result, ex ,param);
//            resultRecord.add(result);
//            continue;
//          }
//
//          recordResult(bill,result,null, param);
//          resultRecord.add(result);
//        }
//
//      } else if (StringUtils.isEmpty(failMode) || FailMode.BATCH.hit(failMode)){
//        consumer.accept(dtos);
//      }
//
//      return resultRecord;
//    }
//
//
//    private static void recordResult(RqParsedResult bill, CollectionBillErrorResult errorResult,
//        BusinessRuntimeException ex, CollectionApiSaveParam singleParam) {
//      errorResult.setCode(null == ex ? "0000" : ex.getCode());
//      errorResult.setBillType(bill.getBillType());
//      errorResult.setInvoiceCode(singleParam.getFpDm());
//      errorResult.setInvoiceNum(singleParam.getFpHm());
//      errorResult.setErrmsg(null == ex ? "保存成功" : ex.getMessage());
//    }
//
//
//  }




  enum FailMode {

    BATCH("batch","批量成功/失败",""),
    SINGLE("single","逐条成功/失败","");

    private String failModeAsString;
    private String value;
    private String multiLang;


    FailMode(String failModeAsString, String value, String multiLang) {
      this.failModeAsString = failModeAsString;
      this.value = value;
      this.multiLang = multiLang;
    }

    public boolean hit(String mode){
      return null != mode && this.failModeAsString.equalsIgnoreCase(mode);
    }

  }
  }


