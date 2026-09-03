package com.beyondsoft.obt.security;

import com.beyondsoft.obt.identity.UserRole;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/** Same payload shape as the Node POC JWT (camelCase claims). */
public class AuthUserPrincipal implements UserDetails {

  private final String id;
  private final String email;
  private final String fullName;
  private final UserRole role;
  private final String salesRepCode;
  private final String customerSapId;

  public AuthUserPrincipal(
      String id,
      String email,
      String fullName,
      UserRole role,
      String salesRepCode,
      String customerSapId) {
    this.id = id;
    this.email = email;
    this.fullName = fullName;
    this.role = role;
    this.salesRepCode = salesRepCode;
    this.customerSapId = customerSapId;
  }

  public String getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public String getFullName() {
    return fullName;
  }

  public UserRole getRole() {
    return role;
  }

  public String getSalesRepCode() {
    return salesRepCode;
  }

  public String getCustomerSapId() {
    return customerSapId;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
  }

  @Override
  public String getPassword() {
    return "";
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
