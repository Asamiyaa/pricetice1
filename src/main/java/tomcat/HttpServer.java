package tomcat;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by yangwenjun on 2023/3/3 23:12
 *
 *
 *    1.静态资源
 *    2.servlet
 */
public class HttpServer {

    // 存放静态资源的位置
    public static final String WEB_ROOT =
        System.getProperty("user.dir")+"\\src\\main\\webroot\\"/* + File.separator  + "webroot"*/;

    // 关闭Server的请求
    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";

    // 是否关闭Server
    private boolean shutdown = false;

    // 主入口
    public static void main(String[] args) {
      HttpServer server = new HttpServer();
      server.await();
    }

    public void await() {
      // 启动ServerSocket
      ServerSocket serverSocket = null;
      int port = 9999;
      try {
        serverSocket =  new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
      }
      catch (IOException e) {
        e.printStackTrace();
        System.exit(1);
      }

      // 循环等待一个Request请求
      while (!shutdown) {
        Socket socket = null;
        InputStream input = null;
        OutputStream output = null;
        try {
          // 创建socket
          //todo: 流处理
          socket = serverSocket.accept();
          input = socket.getInputStream();
          output = socket.getOutputStream();

          // 封装input至request, 并处理请求
          Request request = new Request(input);
          request.parse();

          // 封装output至response

          //第二版:加入静态资源 vs  servlet
          Response response = new Response(output);
          if(request.getUri().startsWith("/servlet")){
            ServletProcessor processor = new ServletProcessor();
            processor.service(request, response);
          }else{
            response.setRequest(request);
            response.sendStaticResource();
          }

          // 关闭socket
          socket.close();

          // 如果接受的是关闭请求，则设置关闭监听request的标志
          shutdown = request.getUri().equals(SHUTDOWN_COMMAND);
        }
        catch (Exception e) {
          e.printStackTrace();
          continue;
        }
      }
    }
  }
