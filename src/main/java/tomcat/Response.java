package tomcat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Locale;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.WriteListener;

/**
 * Created by yangwenjun on 2023/3/3 23:13
 */
//todo:9 jar中jar不能这么写
public class Response /*extends org.apache.catalina.connector.Response*/ implements ServletResponse {

  private static final int BUFFER_SIZE = 1024;
  Request request;
  OutputStream output;


  public Request getRequest() {
    return request;
  }

  public OutputStream getOutput() {
    return output;
  }

  public void setOutput(OutputStream output) {
    this.output = output;
  }

  public Response(OutputStream output) {
    this.output = output;
  }

  // response中封装了request，以便获取request中的请求参数
  public void setRequest(Request request) {
    this.request = request;
  }

  public void sendStaticResource() throws IOException {
    byte[] bytes = new byte[BUFFER_SIZE];
    FileInputStream fis = null;
    try {

//      该网页无法正常运作

      //todo: 4. 默认的网络访问路径地址
      File file = new File(HttpServer.WEB_ROOT, request.getUri());
      if (file.exists()) {
        fis = new FileInputStream(file);
        int ch = fis.read(bytes, 0, BUFFER_SIZE);
        //todo: 1.对字节的处理
//        while (ch!=-1) {
//          output.write(bytes, 0, ch);
//          ch = fis.read(bytes, 0, BUFFER_SIZE);
//        }
        String successMessage = "HTTP/1.1 200 Successful tomcat first\r\n" +
            "Content-Type: text/html\r\n" +
            "Content-Length: 23\r\n" +
            "\r\n" +
            "<h1>FIRST TOMCAT</h1>";
        output.write(successMessage.getBytes());
      }
      else {
        // 文件不存在时，输出404信息
        String errorMessage = "HTTP/1.1 404 File Not Found\r\n" +
            "Content-Type: text/html\r\n" +
            "Content-Length: 23\r\n" +
            "\r\n" +
            "<h1>File Not Found</h1>";
        output.write(errorMessage.getBytes());
      }
    }
    catch (Exception e) {
      // thrown if cannot instantiate a File object
      System.out.println(e.toString() );
    }
    finally {
      if (fis!=null)
        fis.close();
    }
  }

  @Override
  public String getCharacterEncoding() {
    return null;
  }

  @Override
  public String getContentType() {
    return null;
  }

  //todo:10 搞不清楚如何输出内容到页面

  @Override
  public ServletOutputStream getOutputStream() throws IOException {
    return new ServletOutputStream() {
      @Override
      public boolean isReady() {
        return false;
      }

      @Override
      public void setWriteListener(WriteListener writeListener) {

      }

      @Override
      public void write(int b) throws IOException {

      }
    };
  }

  @Override
  public PrintWriter getWriter() throws IOException {
    //java.lang.NullPointerException
    PrintWriter writer = new org.apache.catalina.connector.Response().getWriter();
//    new ServletResponse().getWriter()
//    return writer;
//    return new PrintWriter("test");
    return null;
  }

  @Override
  public void setCharacterEncoding(String s) {

  }

  @Override
  public void setContentLength(int i) {

  }

  @Override
  public void setContentLengthLong(long l) {

  }

  @Override
  public void setContentType(String s) {

  }

  @Override
  public void setBufferSize(int i) {

  }

  @Override
  public int getBufferSize() {
    return 0;
  }

  @Override
  public void flushBuffer() throws IOException {

  }

  @Override
  public void resetBuffer() {

  }

  @Override
  public boolean isCommitted() {
    return false;
  }

  @Override
  public void reset() {

  }

  @Override
  public void setLocale(Locale locale) {

  }

  @Override
  public Locale getLocale() {
    return null;
  }
}
