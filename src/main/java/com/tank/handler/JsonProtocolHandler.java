package com.tank.handler;

import org.springframework.web.socket.WebSocketSession;

/**
 * @author fuchun
 */
public interface JsonProtocolHandler {

  /**
   * json protocol handler
   * @param jsonStr
   * @param session
   */
  void processProtocol(final String jsonStr, final  WebSocketSession session);

  /**
   * handler exception handler
   * @param handlerName
   * @param errorMsg
   * @return
   */
  default String errorMsg(final String handlerName, final  String errorMsg) {
    StringBuilder sb = new StringBuilder("handl ");
    sb.append(handlerName);
    sb.append(" happen error: ");
    sb.append(errorMsg);
    return sb.toString();
  }
}
