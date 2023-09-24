
import java.io.IOException;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ChatGPTUtil {
    /**
      * 自己chatGpt的ApiKey
      *///正确能用的
        private static String API_KEY = "sk-sqbw93nxpidIYuaGxXroo2paCaCfE1dAuDDgx07lYocHTEgH";
        //test能否用 - 不能使用 wrong api key
        //  private static String API_KEY = "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";
    /**
      * 对应的请求接口
      */
        private static String URL = "https://api.chatanywhere.com.cn/v1/chat/completions";

        /**
      * 创建一个ChatGptRequestParameter，用于携带请求参数
      */
        private static ChatGPTRequestParameter chatGptRequestParameter = new ChatGPTRequestParameter();

       /**
     * 相应超时时间，毫秒
      */
        private int responseTimeout = 60000;

      public static String getAnswer(String question) {
          // 创建一个HttpPost
          chatGptRequestParameter.addMessages(new ChatGPTMessage("user", question));

          String raw = JSON.toJSONString(chatGptRequestParameter);

          HttpRequest request = HttpRequest.post(URL)
              .body(raw)
              .header(Header.CONTENT_TYPE.getValue(), "application/json")
              .header(Header.AUTHORIZATION.getValue(), "Bearer " + API_KEY);

          HttpResponse response = request.execute();
          try {
                String body = response.body();

//            System.out.println("body is {}" + body);

            ObjectMapper objectMapper = new ObjectMapper();
                // 转换为对象
                ChatGPTResponseParameter responseParameter = objectMapper.readValue(body, ChatGPTResponseParameter.class);
                String ans = "";
                // 遍历所有的Choices（一般都只有一个）
              for (Choices choice : responseParameter.getChoices()) {
                    ChatGPTMessage message = choice.getMessage();
                    chatGptRequestParameter.addMessages(new ChatGPTMessage(message.getRole(), message.getContent()));
                    String s = message.getContent().replaceAll("\n+", "\n");
                    ans += s;
                }
              // 返回信息
              return ans;
          } catch (IOException e) {
              e.printStackTrace();
          }
        // 发生异常，移除刚刚添加的ChatGptMessage
        chatGptRequestParameter.getMessages().remove(chatGptRequestParameter.getMessages().size()-1);
        return "您当前的网络无法访问";
    }
}
@Data
 @NoArgsConstructor
 @AllArgsConstructor
 class ChatGPTMessage {
    private String role;
    private String content;
}

@Data
  class ChatGPTRequestParameter {

  private String model = "gpt-3.5-turbo-0301";
  private List<ChatGPTMessage> messages = new ArrayList<>();

  public void addMessages(ChatGPTMessage message) {
    this.messages.add(message);
  }
}

@Data
 class ChatGPTResponseParameter {
    private String id;
    private String object;
    private String created;
    private String model;
    private Usage usage;
    private List<Choices> choices;
}

@Data
  class Usage {
     String prompt_tokens;
     String completion_tokens;
     String total_tokens;
}


@Data
  class Choices {
     ChatGPTMessage message;
     String finish_reason;
     Integer index;
}