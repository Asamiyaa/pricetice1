import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 *  remote client chatgpt Created by yangwenjun on 2023/9/21 16:21
 */
public class ChatgptClient {

    public static void main(String[] args) {
               while (true) {
                    System.out.print("\n请输入问题(q退出)：");
                     String question = new Scanner(System.in).nextLine();
                     if ("q".equals(question)) {break;}
                    long start = System.currentTimeMillis();
                     String answer = ChatGPTUtil.getAnswer(question);
                     long end = System.currentTimeMillis();
                     System.out.println("该回答花费时间为：" + (end - start) / 1000.0 + "秒");
                     System.out.println(answer);
                 }

//      try {
//        String apiKey = "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";   //todo :need pay money
////        String endpoint = "https://api.openai.com/v1/engines/davinci-codex/completions";
//        String endpoint = "https://chat.t-nn.com/v1/chat/completions";
//        URL url = new URL(endpoint);
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//
//        connection.setRequestMethod("POST");
//        connection.setRequestProperty("Authorization", "Bearer " + apiKey);
//        connection.setRequestProperty("Content-Type", "application/json");
//
//        String postData = "{\"prompt\": \"Translate the following English text to French: 'Hello, how are you?'\"}";
//
//        connection.setDoOutput(true);
//        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
//        outputStream.writeBytes(postData);
//        outputStream.flush();
//        outputStream.close();
//
//        int responseCode = connection.getResponseCode();
//        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//        String inputLine;
//        StringBuilder response = new StringBuilder();
//
//        while ((inputLine = in.readLine()) != null) {
//          response.append(inputLine);
//        }
//        in.close();
//
//        System.out.println("Response Code: " + responseCode);
//        System.out.println("Response Data: " + response.toString());
//      } catch (Exception e) {
//        e.printStackTrace();
//      }
    }
  }

