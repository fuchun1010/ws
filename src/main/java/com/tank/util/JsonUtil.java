package com.tank.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @author fuchun
 */
public class JsonUtil {

  public static <T> T jsonStr2Obj(final String str, Class<T> classType) throws IOException {
    final ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(str, classType);
  }

  public static <T> String obj2JsonStr(final T t) throws JsonProcessingException {
    final ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(t);
  }
}
