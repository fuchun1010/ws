package com.tank.common;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
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
@Slf4j
@Component
public class SocketContainer {


  public synchronized void addSocket(@NonNull WebSocketSession socket) {
    val threadName = Thread.currentThread().getName();
    log.info("thread " + threadName + "start add socket");
    sessions.add(socket);
    sockets.putIfAbsent(socket.getId(), socket);
    log.info("thread " + threadName + "end add socket");
  }

  public synchronized void removeSocket(@NonNull WebSocketSession socket) {
    val threadName = Thread.currentThread().getName();
    log.info("thread " + threadName + "start remove socket");
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
    log.info("thread " + threadName + "end remove socket");
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
