package com.tank;

import com.tank.handler.JsonProtocolHandler;
import com.tank.handler.login.LoginHandler;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.tank.util.JsonCodes.LOGIN_REQUEST;

/**
 * web socket try
 *
 * @author fuchun
 */
@SpringBootApplication
@Slf4j
public class App {
  public static void main(final String[] args) {
    SpringApplication.run(App.class, args);
  }


  @PostConstruct
  @Bean("protocolHandlers")
  public Map<Integer, JsonProtocolHandler> initProtocolHandler() {
    log.info("....init protocol handler....");
    val map = new ConcurrentHashMap<Integer, JsonProtocolHandler>();
    map.putIfAbsent(LOGIN_REQUEST, loginHandler);
    return map;
  }

  @Autowired
  private LoginHandler loginHandler;
}
