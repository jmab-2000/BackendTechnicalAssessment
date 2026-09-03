package com.beyondsoft.obt.booking;

public class Booking {

  private String id;
  private String contractRef;
  private String createdBy;
  /** draft | sent */
  private String status;
  private String poNumber;
  private int treatmentQty;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getContractRef() {
    return contractRef;
  }

  public void setContractRef(String contractRef) {
    this.contractRef = contractRef;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getPoNumber() {
    return poNumber;
  }

  public void setPoNumber(String poNumber) {
    this.poNumber = poNumber;
  }

  public int getTreatmentQty() {
    return treatmentQty;
  }

  public void setTreatmentQty(int treatmentQty) {
    this.treatmentQty = treatmentQty;
  }
}
