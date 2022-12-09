package com.chatbot.us.chatbotapi.chatbot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ml/chats")
public class ChatController {

  @Autowired
  private ChatRepository chatRepository;
  private ObjectMapper mapper = new ObjectMapper();

  @GetMapping
  public ResponseEntity<List<ChatModel>> getAllChats() {
    List<Chat> chatList = chatRepository.findAll();
    return ResponseEntity.ok(chatList.stream().map(value -> {
          try {
            return ChatModel.builder()
                    .id(value.getId())
                    .messages(mapper.readValue(value.getMessages(), new TypeReference<List<String>>(){}))
                    .build();
          } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
          }
        })
        .collect(Collectors.toList()));
  }

  @PostMapping
  public ResponseEntity<Chat> saveChat(@RequestBody ChatModel chatModel)
      throws JsonProcessingException {
    Chat chat = chatRepository.save(
        Chat.builder().messages(mapper.writeValueAsString(chatModel.getMessages())).build());
    return ResponseEntity.ok(chat);
  }
}
