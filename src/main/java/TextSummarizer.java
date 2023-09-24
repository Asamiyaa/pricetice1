/**
 * Created by yangwenjun on 2023/9/21 16:10
 */
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import com.hankcs.hanlp.summary.TextRankSentence;

public class TextSummarizer {
  public static void main(String[] args) throws Exception {
    String filePath = "D:\\test/summary.txt"; // 替换成你要读取的本地文件路径
    int summaryLength = 10; // 摘要长度，单位是句子数

    List<String> lines = Files.readAllLines(Paths.get(filePath));
    String content = String.join("\n", lines);

    List<String> sentences = TextRankSentence.getTopSentenceList(content, summaryLength);
    String summary = String.join("", sentences);

    System.out.println("Summary:\n" + summary);

    /**
     * Summary:
     * 框架的设计实现常常会用到设计模式由于封装和交互、实现细节的大量代码往往会将用于解决问题的核心代码框架的设计实现通常包括三层技术难点也是理解源码实现的一个主要障碍部分算法需要在子类实现阅读源码很容易理解为就是直接去阅读代码本身然后阅读框架原理文章阅读框架源码需要很大的耐心和意志阅读原理、架构及源码分析文章通常是理解某个模块的原理、设计或者为了解决实际问题
     */
  }
}
