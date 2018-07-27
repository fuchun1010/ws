package com.tank.common;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
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
    val number = socketContainer.onlineUsers();
    System.out.println("online users is:" + number);

  }

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

    for (WebSocketSession socket : socketContainer.allSockets()) {
      if (socket.isOpen()) {
        if (!socket.getId().equals(session.getId())) {

          socket.sendMessage(new TextMessage(message.getPayload()));
        }
      } else {
        socketContainer.removeSocket(socket);
      }

    }
  }


  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    socketContainer.removeSocket(session);
    val number = socketContainer.onlineUsers();
    System.out.println("session closed online users is:" + number);
  }

  @Autowired
  private SocketContainer socketContainer;
}
