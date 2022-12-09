package com.chatbot.us.chatbotapi.chatbot;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatModel {
  long id;
  List<String> messages;
}
