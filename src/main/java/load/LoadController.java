package load;

import Common.Result;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/practice")
public class LoadController {

    @RequestMapping("/upload")
    public Result upload(MultipartFile fileTest, HttpServletRequest request) throws IOException {
      String realPath = request.getSession().getServletContext().getRealPath("/file");
      String fileName = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss").format(
                          LocalDateTime.now());
      fileTest.transferTo(new File(realPath,fileName+".txt"));
      return Result.success(fileName);
  }

    @RequestMapping("/download/{fileName}")
    public void download(@PathVariable String fileName, HttpServletRequest request, HttpServletResponse response) throws Exception {
      //获取文件的绝对路径
      String realPath = request.getSession().getServletContext().getRealPath("file");
      //获取输入流对象（用于读文件）
      FileInputStream fis = new FileInputStream(new File(realPath, fileName));
      //获取文件后缀（.txt）
      String extendFileName = fileName.substring(fileName.lastIndexOf('.'));
      //动态设置响应类型，根据前台传递文件类型设置响应类型
      response.setContentType(request.getSession().getServletContext().getMimeType(extendFileName));
      //设置响应头,attachment表示以附件的形式下载，inline表示在线打开
      response.setHeader("content-disposition","attachment;fileName="+ URLEncoder.encode(fileName,"UTF-8"));
      //获取输出流对象（用于写文件）
      ServletOutputStream os = response.getOutputStream();
      //下载文件,使用spring框架中的FileCopyUtils工具
      FileCopyUtils.copy(fis,os);
    }


}
