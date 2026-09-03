package com.beyondsoft.obt.identity;

import com.beyondsoft.obt.common.ApiException;
import com.beyondsoft.obt.identity.dto.DemoUsersResponse;
import com.beyondsoft.obt.identity.dto.LoginRequest;
import com.beyondsoft.obt.identity.dto.LoginResponse;
import com.beyondsoft.obt.identity.dto.UserDto;
import com.beyondsoft.obt.security.AuthUserPrincipal;
import com.beyondsoft.obt.security.JwtService;
import com.beyondsoft.obt.store.InMemoryStore;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  private final InMemoryStore store;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;

  public AuthService(InMemoryStore store, PasswordEncoder passwordEncoder, JwtService jwtService) {
    this.store = store;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
  }

  public LoginResponse login(LoginRequest request) {
    User user =
        store.users.stream()
            .filter(u -> u.getEmail().equalsIgnoreCase(request.email().trim()))
            .findFirst()
            .orElseThrow(() -> ApiException.unauthorized("Invalid email or password"));
    if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
      throw ApiException.unauthorized("Invalid email or password");
    }
    AuthUserPrincipal principal = toPrincipal(user);
    return new LoginResponse(jwtService.createToken(principal), toDto(principal));
  }

  public UserDto me(AuthUserPrincipal principal) {
    return toDto(principal);
  }

  public DemoUsersResponse demoUsers() {
    List<DemoUsersResponse.DemoUser> list =
        store.users.stream()
            .map(u -> new DemoUsersResponse.DemoUser(u.getEmail(), u.getFullName(), u.getRole()))
            .toList();
    return new DemoUsersResponse(InMemoryStore.DEMO_PASSWORD, list);
  }

  private static AuthUserPrincipal toPrincipal(User user) {
    return new AuthUserPrincipal(
        user.getId(),
        user.getEmail(),
        user.getFullName(),
        user.getRole(),
        user.getSalesRepCode(),
        null);
  }

  private static UserDto toDto(AuthUserPrincipal p) {
    return new UserDto(
        p.getId(), p.getEmail(), p.getFullName(), p.getRole(), p.getSalesRepCode(), p.getCustomerSapId());
  }
}
