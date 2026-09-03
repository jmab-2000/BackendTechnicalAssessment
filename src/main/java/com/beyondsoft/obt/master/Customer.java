package com.beyondsoft.obt.master;

public class Customer {

  private String sapId;
  private String name;

  public Customer() {}

  public Customer(String sapId, String name) {
    this.sapId = sapId;
    this.name = name;
  }

  public String getSapId() {
    return sapId;
  }

  public void setSapId(String sapId) {
    this.sapId = sapId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
