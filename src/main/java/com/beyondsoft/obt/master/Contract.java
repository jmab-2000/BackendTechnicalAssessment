package com.beyondsoft.obt.master;

public class Contract {

  private String contractRef;
  private String soldTo;
  private String salesUserId;
  /** Non-empty = SAP RAR; null/blank = Standard contract. */
  private String sapRarRef;

  public String getContractRef() {
    return contractRef;
  }

  public void setContractRef(String contractRef) {
    this.contractRef = contractRef;
  }

  public String getSoldTo() {
    return soldTo;
  }

  public void setSoldTo(String soldTo) {
    this.soldTo = soldTo;
  }

  public String getSalesUserId() {
    return salesUserId;
  }

  public void setSalesUserId(String salesUserId) {
    this.salesUserId = salesUserId;
  }

  public String getSapRarRef() {
    return sapRarRef;
  }

  public void setSapRarRef(String sapRarRef) {
    this.sapRarRef = sapRarRef;
  }
}
