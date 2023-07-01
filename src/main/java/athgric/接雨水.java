package athgric;

/**
 * Created by yangwenjun on 2023/5/9 16:37
 *
 *
 * 给定一个长度为 n 的整数数组 height 。有 n 条垂线，第 i 条线的两个端点是 (i, 0) 和 (i, height[i]) 。
 *
 * 找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
 *
 * 返回容器可以储存的最大水量。
 *
 * 说明：你不能倾斜容器。
 *
 *
 * [1,8,6,2,5,4,8,3,7]
 * 输出：49
 *
 */
public class 接雨水 {

  public static void main(String[] args) {

    int max = 0;
    int maxH = 0;
    int maxL = 0;
    int minOfTwo = 0;

    int[] ary = {1,8,6,2,5,4,8,3,7};

    //双层for

    //不算面积，如果宽、高都比锚小，没有比较意义，一个大或者都大才有意义
    for (int i = 0; i <= ary.length - 2; i++) {
      for (int j = i+1; j <= ary.length - 1; j++) {

        minOfTwo = ary[i] > ary[j]? ary[j] : ary[i];
//        max = max > curt ? max : curt;
        maxH = maxH > minOfTwo? maxH : minOfTwo;
        maxL = maxL > (j - i) ? maxL : (j - i);
        //有无计算意义
//        if ((maxH > minOfTwo && maxL > (j - i)) || (maxH > (j - i) && maxL > minOfTwo)) {
//          continue;
//        }

        int curt = (j - i) * minOfTwo;
        max = max > curt ? max : curt;
      }
    }

    System.out.println(max);
  }



}
