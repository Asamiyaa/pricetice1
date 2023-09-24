import java.io.FileOutputStream;

/**
 * Created by yangwenjun on 2023/9/21 15:30
 */
import java.io.*;
import java.net.*;
import com.itextpdf.html2pdf.HtmlConverter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BlogDownload {

    public static void main(String[] args) throws Exception {
      String urlPrefix = "https://www.cnblogs.com/lovesqcc/p/";
      String[] blogIds = {"14403497"/*, "blog2", "blog3"*/}; // 替换成你需要下载的博客ID
      String savePath = "D:/blogs/"; // 替换成你想要保存博客的本地路径
      String urlSuffix = ".html";

      for (String blogId : blogIds) {
        String url = urlPrefix + blogId + urlSuffix;
        String fileName  = savePath + blogId + ".html";

        URLConnection conn = new URL(url).openConnection();
        InputStream in = conn.getInputStream();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = in.read(buffer)) != -1) {
          out.write(buffer, 0, length);
        }
        in.close();

        byte[] response = out.toByteArray();
        FileOutputStream fos = new FileOutputStream(fileName);
        fos.write(response);
        fos.close();

//        String pdfPath = savePath + blogId + ".pdf";
//        FileInputStream htmlFile = new FileInputStream(new File(fileName));
//        Document doc = Jsoup.parse(htmlFile, "UTF-8", "");
////        htmlFile.close();
//
//        // 去掉HTML文件中的CSS和JavaScript内容
//        Elements links = doc.select("link[href]");
//        for (Element link : links) {
//          if (link.attr("rel").equals("stylesheet")) {
//            link.remove();
//          }
//        }
//        Elements scripts = doc.select("script[src]");
//        for (Element script : scripts) {
//          script.remove();
//        }
//
//        FileOutputStream fos1 = new FileOutputStream(fileName);
//        fos1.write(doc.outerHtml().getBytes());
//        fos1.close();
//
//        FileInputStream htmlFile1 = new FileInputStream(new File(fileName));
//        FileOutputStream pdfFile = new FileOutputStream(new File(pdfPath));
//        HtmlConverter.convertToPdf(htmlFile1, pdfFile);

        System.out.println("Downloaded " + fileName);
      }


    }
}
