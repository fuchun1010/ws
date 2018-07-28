package com.tank.message;

import lombok.Data;

/**
 * @author fuchun
 */
@Data
public class LoginRequest extends RequestMessage {

  private String username;

  private String password;


}
