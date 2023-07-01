import java.util.List;
import java.util.function.Function;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * Created by yangwenjun on 2023/5/25 14:45
 */
public class 函数式编程2 {

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


