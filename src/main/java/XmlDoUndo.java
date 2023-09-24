import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Created by yangwenjun on 2023/9/21 13:48
 *
 *    1.默认实现
 *    2.java中有几种xml解析，映射方案
 *    3.方案实现  对比
 */
public class XmlDoUndo {

    public static void main(String[] args) {
      try {
        File xmlFile = new File("D:\\test\\xmltest.xml"); // XML文件的路径

        // 创建一个DocumentBuilderFactory并指定命名空间支持
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setNamespaceAware(true);

        // 创建一个DocumentBuilder
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

        // 使用DocumentBuilder解析XML文件，得到一个Document对象
        Document doc = dBuilder.parse(xmlFile);

        // 可选：如果XML文件中有DTD声明，可以禁用DTD验证
        // doc.getDocumentElement().normalize();

        // 获取XML文档的根元素
        Element rootElement = doc.getDocumentElement();
        System.out.println("根元素：" + rootElement.getNodeName());

        // 获取根元素的子元素
        NodeList nodeList = rootElement.getChildNodes();

        // 遍历子元素并提取数据
        for (int i = 0; i < nodeList.getLength(); i++) {
          Node node = nodeList.item(i);
          if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;

            // 提取元素的数据
            String nodeName = element.getNodeName();
            String nodeValue = element.getTextContent();

            System.out.println("元素名称：" + nodeName);
            System.out.println("元素值：" + nodeValue);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }


  /**
   * 在Java中，有三种主要的XML解析和映射方案，它们分别是DOM、SAX和StAX。此外，还有一些XML数据绑定框架，如 JAXB，用于将XML数据映射到Java对象。下面是它们的简要概述：
   *
   * 1. **DOM (Document Object Model) 解析**：
   *    - **概述**：DOM解析将整个XML文档加载到内存中，形成一个树状结构的文档对象模型（DOM）。开发人员可以通过操作DOM树来访问和修改XML文档中的数据。
   *    - **优点**：容易使用，可以随机访问XML文档的任何部分。
   *    - **缺点**：对于大型XML文件，内存开销较大，可能会导致性能问题。
   *
   * 2. **SAX (Simple API for XML) 解析**：
   *    - **概述**：SAX解析是一种基于事件驱动的解析方法。它逐行读取XML文档并触发事件（如元素开始、元素结束、文本等），开发人员可以编写处理器来处理这些事件。
   *    - **优点**：内存效率高，适用于处理大型XML文件，因为它不需要加载整个文档到内存中。
   *    - **缺点**：不适合需要随机访问XML数据的情况，因为它是顺序读取的。
   *
   * 3. **StAX (Streaming API for XML) 解析**：
   *    - **概述**：StAX解析是一种混合解析方法，它允许开发人员使用基于事件的处理方式，同时也提供了基于游标的处理方式。开发人员可以根据需要切换这两种模式。
   *    - **优点**：具有高度的灵活性，既支持事件驱动的处理方式，也支持游标方式，适用于各种情况。
   *    - **缺点**：相对于SAX，StAX的API可能更复杂一些。
   *
   * 4. **JAXB (Java Architecture for XML Binding)**：
   *    - **概述**：JAXB是一种XML数据绑定框架，它允许将XML文档映射到Java对象，以便在Java中轻松处理XML数据。它提供了注解和API来指定XML到Java对象的映射规则。
   *    - **优点**：简化了XML数据的处理，使得开发人员可以更容易地在Java中使用XML数据。
   *    - **缺点**：不适用于所有XML文档，特别是对于非常复杂或具有高度动态结构的文档。
   *
   * 选择合适的XML解析和映射方案取决于你的项目需求和性能要求。如果你需要处理大型XML文件并且内存敏感，SAX或StAX可能是更好的选择。如果需要将XML数据映射到Java对象，那么JAXB是一个方便的选项。 DOM适用于小型文档或需要随机访问整个文档的情况。
   *
   */


  //方案实现

