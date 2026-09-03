package com.beyondsoft.obt.booking;

import com.beyondsoft.obt.booking.dto.BookingDto;
import com.beyondsoft.obt.booking.dto.CreateBookingRequest;
import com.beyondsoft.obt.common.ApiException;
import com.beyondsoft.obt.security.AuthUserPrincipal;
import com.beyondsoft.obt.store.InMemoryStore;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Candidate task: implement these methods using {@link InMemoryStore#bookings} (ArrayList). Follow
 * the same controller → service → store pattern as {@link com.beyondsoft.obt.master.MasterService}.
 */
@Service
public class BookingService {

  private final InMemoryStore store;

  public BookingService(InMemoryStore store) {
    this.store = store;
  }

  public List<BookingDto> list(AuthUserPrincipal user) {
    throw ApiException.notImplemented(
        "Implement: list bookings. sales sees only bookings whose contract.salesUserId is self.");
  }

  public BookingDto create(AuthUserPrincipal user, CreateBookingRequest request) {
    throw ApiException.notImplemented(
        "Implement: create status=draft on store.bookings. Reject unknown contract. Sales may only use own contract.");
  }

  public BookingDto get(AuthUserPrincipal user, String id) {
    throw ApiException.notImplemented("Implement: get by id with the same sales isolation as list.");
  }

  public BookingDto send(AuthUserPrincipal user, String id) {
    throw ApiException.notImplemented("Implement: draft → sent only. 400 if not draft.");
  }
}
