package com.tank.common;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author fuchun
 */
@Component
public class SocketContainer {


  public void addSocket(@NonNull WebSocketSession socket) {
    sessions.add(socket);
    sockets.putIfAbsent(socket.getId(), socket);
  }

  public void removeSocket(@NonNull WebSocketSession socket) {
    sockets.remove(socket.getId());
    int index = -1;
    for (int i = 0; i < sessions.size(); i++) {
      if (sessions.get(i).getId().equals(socket.getId())) {
        index = i;
        break;
      }
    }
    if (index != -1) {
      sessions.remove(index);
    }
  }

  public int onlineUsers() {
    return sockets.size();
  }

  public Optional<WebSocketSession> get(@NonNull String id) {
    return Optional.of(sockets.get(id));
  }

  public List<WebSocketSession> allSockets() {
    return sessions;
  }


  private Map<String, WebSocketSession> sockets = new ConcurrentHashMap();

  private List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
}
