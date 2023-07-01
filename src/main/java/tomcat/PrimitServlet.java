package tomcat;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.jasper.tagplugins.jstl.core.Out;

/**
 * Created by yangwenjun on 2023/3/16 20:42
 */
public class PrimitServlet implements Servlet {


  @Override
  public void init(ServletConfig servletConfig) throws ServletException {


  }

  @Override
  public ServletConfig getServletConfig() {
    return null;
  }

  @Override
  public void service(ServletRequest servletRequest, ServletResponse response)
      throws ServletException, IOException {

    System.out.println("from service");
    //todo:8 printWriter如何和前台交互,底层 api、细节
//    PrintWriter out = response.getWriter();
//    out.println("Hello. Roses are red.");
//    out.print("Violets are blue.");
//    response.getOutputStream().print("tomcat servlet hello");

    System.out.println("woshi sb ，不会输出内容到页面");
  }

  @Override
  public String getServletInfo() {
    return null;
  }

  @Override
  public void destroy() {

  }
}
