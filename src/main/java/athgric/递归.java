package athgric;

/**
 * Created by yangwenjun on 2023/4/13 15:50
 *
 *     递归
 *        1.
 */
public class 递归 {

  int i = 0;
  String msg = "";

  public static void main(String[] args) {

    System.out.println(new 递归().callSelf("begin-->"));

    System.out.println(new 递归().callSelf2(5));


  }


  //更像一个循环而不是递归
  String callSelf(String info){
    //变化部分
    i++;

    //完成什么事情
    msg += info;

    //终止条件
    if (i <= 10){
      callSelf(info);
    }

    return msg;
  }


  //递归 ,不知道如何解决，但知道一直分割到最小单位解决  fn = fn-1 + fn-2
  int callSelf2(int i){

    //这两个单元可以解决
    if (i == 1) return 2;

    //必须能正确终止
    if (i == 2) return 3;


    //正确拆分 <== 青蛙跳问题
    return callSelf2(i - 1) + callSelf2(i - 2);

  }

}
