package com.tank.common;

import com.tank.handler.JsonProtocolHandler;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import static com.tank.util.JsonUtil.jsonStr2Obj;
import static com.tank.util.JsonUtil.obj2TextMessage;

/**
 * @author fuchun
 */
@Slf4j
@Component
@Scope("prototype")
public class SocketHandler extends TextWebSocketHandler {

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    val map = new HashMap<String, String>(16);
    map.putIfAbsent("code", String.valueOf(200));
    map.putIfAbsent("message", "connect success");
    val jsonStr = obj2TextMessage(map);
    session.sendMessage(jsonStr);
  }

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

    val jsonStr = message.getPayload();
    val errorResponse = new HashMap<String, Object>(16);
    errorResponse.putIfAbsent("code", 500);
    if (Objects.isNull(jsonStr) || jsonStr.trim().length() == 0) {
      errorResponse.putIfAbsent("error", "jsonStr is empty");
      val errorJson = obj2TextMessage(errorResponse);
      session.sendMessage(errorJson);
    } else {
      val request = jsonStr2Obj(jsonStr, LinkedHashMap.class);
      val code = (Integer) request.get("code");
      val handler = protocolHandlers.get(code);
      if (Objects.isNull(handler)) {
        errorResponse.putIfAbsent("error", "code " + code + " json handler is not support");
        val errorJson = obj2TextMessage(errorResponse);
        session.sendMessage(errorJson);
      } else {
        handler.processProtocol(jsonStr, session);
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

  @Autowired
  private Map<Integer, JsonProtocolHandler> protocolHandlers;
}
