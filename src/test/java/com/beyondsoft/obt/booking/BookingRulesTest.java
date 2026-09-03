package com.beyondsoft.obt.booking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class BookingRulesTest {

  @Test
  void hasSapRar() {
    assertTrue(BookingRules.hasSapRar("RAR-ABC-001"));
    assertFalse(BookingRules.hasSapRar(null));
    assertFalse(BookingRules.hasSapRar("N/A"));
  }
}
