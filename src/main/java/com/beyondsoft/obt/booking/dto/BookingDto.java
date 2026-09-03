package com.beyondsoft.obt.booking.dto;

public record BookingDto(
    String id,
    String contractRef,
    String createdBy,
    String status,
    String poNumber,
    int treatmentQty) {}
