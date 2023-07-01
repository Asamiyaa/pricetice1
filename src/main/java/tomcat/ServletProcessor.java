package tomcat;

import com.google.common.collect.Lists;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by yangwenjun on 2023/3/16 20:36
 */
public class ServletProcessor {

  public void service(Request request, Response response){

// 获取servlet名字
    String uri = request.getUri();
    String servletName = uri.substring(uri.lastIndexOf("/") + 1);

    // 初始化URLClassLoader
    URLClassLoader loader = null;
    try {
      // create a URLClassLoader

      //todo: 5. urlClassLoader
      URL[] urls = new URL[1];
      URLStreamHandler streamHandler = null;
      File classPath = new File("/source/pricetices1/tomcat");
      // the forming of repository is taken from the createClassLoader method in
      // org.apache.catalina.startup.ClassLoaderFactory
      String repository = (new URL("file", null, classPath.getCanonicalPath() + File.separator)).toString() ;
      // the code for forming the URL is taken from the addRepository method in
      // org.apache.catalina.loader.StandardClassLoader class.
      urls[0] = new URL(null, repository, streamHandler);
      loader = new URLClassLoader(urls);
    } catch (IOException e) {
      System.out.println(e.toString() );
    }

    // 用classLoader加载上面的servlet
    Class myClass = null;
    try {
//      myClass = loader.loadClass(servletName);

      //todo:6.类路径
      myClass = loader.loadClass("tomcat.PrimitServlet" );
    }
    catch (ClassNotFoundException e) {
      System.out.println(e.toString());
    }

    // 将加载到的class转成Servlet，并调用service方法处理
    Servlet servlet = null;
    try {

      servlet = (Servlet) myClass.newInstance();
      servlet.service((ServletRequest) request, (ServletResponse) response);
    } catch (Exception e) {
      System.out.println(e.toString());
    } catch (Throwable e) {
      System.out.println(e.toString());
    }

  }


  public static void main(String[] args) {

    List<Integer> collect = Stream.of(1, 2, 3).filter(x -> x > 1).collect(Collectors.toList());
    System.out.println(collect);

    ArrayList<Integer> integers = Lists.newArrayList(1, 2, 3);
    ArrayList<Integer> integers1 = Lists.newArrayList(2, 3);
    integers.removeAll(integers1);
    System.out.println(integers);

    String s = "099999";
    String  ss = StringUtils.isEmpty(s)?"":"1" + "2";
    System.out.println("==>"+ss);


  }

}
