package com.tank.common;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * @author fuchun
 */
@Component
@Scope("prototype")
public class SocketHandler extends TextWebSocketHandler {

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    socketContainer.addSocket(session);
  }

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    val number = socketContainer.onlineUsers();
    System.out.println("online users is:" + number);
    for (WebSocketSession socket : socketContainer.allSockets()) {
      socket.sendMessage(new TextMessage("hello client"));
    }
  }

  @Autowired
  private SocketContainer socketContainer;
}
