package com.beyondsoft.obt.identity.dto;

import com.beyondsoft.obt.identity.UserRole;

public record UserDto(
    String id,
    String email,
    String fullName,
    UserRole role,
    String salesRepCode,
    String customerSapId) {}
