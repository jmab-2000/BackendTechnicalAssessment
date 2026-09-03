package com.beyondsoft.obt.booking;

import com.beyondsoft.obt.identity.UserRole;
import com.beyondsoft.obt.security.AuthUserPrincipal;

/** Port of {@code backend/src/services/salesScope.ts}. Enforce on every contract/booking query. */
public final class SalesScope {

  private SalesScope() {}

  public static boolean isSalesScoped(AuthUserPrincipal user) {
    return user != null && user.getRole() == UserRole.sales;
  }
}
