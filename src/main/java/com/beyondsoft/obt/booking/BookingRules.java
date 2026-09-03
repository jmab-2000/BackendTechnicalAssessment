package com.beyondsoft.obt.booking;

public final class BookingRules {

  private BookingRules() {}

  public static boolean hasSapRar(String sapRarRef) {
    return sapRarRef != null && !sapRarRef.isBlank() && !"N/A".equals(sapRarRef);
  }
}
