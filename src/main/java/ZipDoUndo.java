import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


/**
 * Created by yangwenjun on 2023/9/21 13:36
 */
public class ZipDoUndo {


  public static void main(String[] args) {

    String zipFilePath = "D:\\test\\azip.zip"; // ZIP文件的路径
    String extractFolderPath = "D:\\test\\"; // 解压后文件的存储路径

    try {
      unzip(zipFilePath, extractFolderPath);
      System.out.println("解压完成");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
      public static void unzip(String zipFilePath, String extractFolderPath) throws IOException {
        byte[] buffer = new byte[1024];

        // 创建解压缩文件夹
        File folder = new File(extractFolderPath);
        if (!folder.exists()) {
          folder.mkdir();
        }

        // 创建ZipInputStream来读取ZIP文件
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath));
        ZipEntry zipEntry = zipInputStream.getNextEntry();

        // 遍历ZIP文件中的所有条目并解压缩它们
        while (zipEntry != null) {
          String entryName = zipEntry.getName();
          File entryFile = new File(extractFolderPath + File.separator + entryName);

          // 如果条目是文件夹，则创建文件夹
          if (zipEntry.isDirectory()) {
            entryFile.mkdirs();
          } else {
            // 如果条目是文件，则创建文件并将数据从ZIP文件写入文件
            FileOutputStream fos = new FileOutputStream(entryFile);
            int len;
            while ((len = zipInputStream.read(buffer)) > 0) {
              fos.write(buffer, 0, len);
              System.out.println("本地描述输出:"+new String(buffer));
            }
            fos.close();
          }

          // 继续处理下一个ZIP条目
          zipEntry = zipInputStream.getNextEntry();
        }

        // 关闭ZipInputStream
        zipInputStream.closeEntry();
        zipInputStream.close();
      }
    }
