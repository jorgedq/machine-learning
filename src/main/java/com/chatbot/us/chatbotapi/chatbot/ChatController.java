package com.chatbot.us.chatbotapi.chatbot;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ml/chats")
@CrossOrigin("*")
public class ChatController {

  @Autowired
  private ChatRepository chatRepository;

  @GetMapping("/schema")
  public ResponseEntity<List<Chat>> getAllChatsSchema() {
    return ResponseEntity.ok(chatRepository.findAll());
  }

  @GetMapping("/data")
  public ResponseEntity<List<List<String>>> getAllChats() {
    List<Chat> chatList = chatRepository.findAll();
    List<List<String>> response = new ArrayList<>();
    chatList.forEach(chat -> {
      response.add(List.of(chat.getQuestion(), chat.getAnswer()));
    });
    return ResponseEntity.ok(response);
  }

  @PostMapping
  public ResponseEntity<Chat> saveChat(@RequestBody Chat chatModel) {
    return ResponseEntity.ok(chatRepository.save(chatModel));
  }
}
