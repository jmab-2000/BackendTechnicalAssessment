package com.beyondsoft.obt.identity;

public class User {

  private String id;
  private String email;
  private String passwordHash;
  private String fullName;
  private UserRole role;
  private String salesRepCode;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public UserRole getRole() {
    return role;
  }

  public void setRole(UserRole role) {
    this.role = role;
  }

  public String getSalesRepCode() {
    return salesRepCode;
  }

  public void setSalesRepCode(String salesRepCode) {
    this.salesRepCode = salesRepCode;
  }
}
