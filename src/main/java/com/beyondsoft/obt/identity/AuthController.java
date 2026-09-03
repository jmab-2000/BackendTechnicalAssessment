package com.beyondsoft.obt.identity;

import com.beyondsoft.obt.identity.dto.DemoUsersResponse;
import com.beyondsoft.obt.identity.dto.LoginRequest;
import com.beyondsoft.obt.identity.dto.LoginResponse;
import com.beyondsoft.obt.identity.dto.UserDto;
import com.beyondsoft.obt.security.AuthUserPrincipal;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth")
public class AuthController {

  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/login")
  public LoginResponse login(@Valid @RequestBody LoginRequest request) {
    return authService.login(request);
  }

  @GetMapping("/me")
  public Map<String, UserDto> me(@AuthenticationPrincipal AuthUserPrincipal principal) {
    return Map.of("user", authService.me(principal));
  }

  @GetMapping("/demo-users")
  public DemoUsersResponse demoUsers() {
    return authService.demoUsers();
  }
}
