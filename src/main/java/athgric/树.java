package athgric;

import com.google.common.collect.Lists;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by yangwenjun on 2023/4/13 15:42
 *
 *   组合模式
 *        1.用代码展示结构 - 属性
 *        2.用代码展示结构 - 递归
 *
 *
 *        1.树的搜索
 *        2.
 */
public class 树 {

  public static void main(String[] args) {

    MyNode myNode = new MyNode(new MyNode(new MyNode(null, "w", 0), "r", 1), "b", 2);

//    System.out.println(myNode);


    //树结构构建
    TreeNode leafNode1 = new TreeNode(null, "g1", 2);
    TreeNode leafNode2 = new TreeNode(null, "g2", 2);

    TreeNode treelevel1 = new TreeNode(Lists.newArrayList(leafNode1,leafNode2), "b1", 1);
    TreeNode leafNode3 = new TreeNode(null,"b1",1);

    TreeNode root = new TreeNode(Lists.newArrayList(treelevel1, leafNode3), "w", 0);
//    System.out.println(root);

    //树的搜索  - 从根开始，查看颜色为 "g2" 的node
    TreeNode enoughNode = null;
    Boolean breakFlag = false;
    for (TreeNode treeNode : root.getTreeNode()) {
      if ("g2".equals(treeNode.getColor())) {
        enoughNode = treeNode;
        break;
      }

      for (TreeNode node : treeNode.getTreeNode()) {
          if ("g2".equals(node.getColor())) {
            enoughNode = node;
            breakFlag = true;
            break;
          }
      }

      if(breakFlag) {
        break;
      }
    }

    System.out.println(enoughNode);


  }




  @AllArgsConstructor
  @NoArgsConstructor
  @Getter
  @Setter
  @ToString
  static class MyNode{
    private MyNode myNode;

    private String color;
    private Integer deepth;
  }


  @AllArgsConstructor
  @NoArgsConstructor
  @Getter
  @Setter
  @ToString
  static class TreeNode{
    private List<TreeNode> treeNode;

    private String color;
    private Integer deepth;

  }


}
