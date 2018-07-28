package com.tank.handler.login;

import com.tank.common.SocketContainer;
import com.tank.handler.JsonProtocolHandler;
import com.tank.message.LoginRequest;
import com.tank.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;

/**
 * @author fuchun
 */
@Slf4j
@Component
public class LoginHandler implements JsonProtocolHandler {

  @Override
  public void processProtocol(String jsonStr, WebSocketSession session) {
    try {
      val loginRequest = JsonUtil.jsonStr2Obj(jsonStr, LoginRequest.class);
      val username = loginRequest.getUsername();
      val password = loginRequest.getPassword();
      val isValidate = "lisi".equals(username) && "123456".equals(password);
      if (isValidate) {
        socketContainer.addSocket(session);
        //TODO session send business response
      } else {
        val map = new HashMap<>(16);
        map.putIfAbsent("code", 202);
        map.putIfAbsent("message", "username and password not matched");
        val error = JsonUtil.obj2JsonStr(map);
        log.error("username and password not matched");
        session.sendMessage(new TextMessage(error));
      }

    } catch (Exception e) {
      log.error(errorMsg("login handler", e.getMessage()));
      e.printStackTrace();
    }
  }

  @Autowired
  private SocketContainer socketContainer;
}
