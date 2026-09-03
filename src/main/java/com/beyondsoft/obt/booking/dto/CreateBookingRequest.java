package com.beyondsoft.obt.booking.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CreateBookingRequest(
    @NotBlank String contractRef, @NotBlank String poNumber, @Min(1) int treatmentQty) {}
