package com.beyondsoft.obt.identity.dto;

import com.beyondsoft.obt.identity.UserRole;
import java.util.List;

public record DemoUsersResponse(String passwordHint, List<DemoUser> users) {

  public record DemoUser(String email, String fullName, UserRole role) {}
}
