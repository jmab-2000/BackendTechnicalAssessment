package com.beyondsoft.obt.booking;

import com.beyondsoft.obt.booking.dto.BookingDto;
import com.beyondsoft.obt.booking.dto.CreateBookingRequest;
import com.beyondsoft.obt.security.AuthUserPrincipal;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookings")
@Tag(name = "Bookings")
public class BookingController {

  private final BookingService bookingService;

  public BookingController(BookingService bookingService) {
    this.bookingService = bookingService;
  }

  @GetMapping
  public List<BookingDto> list(@AuthenticationPrincipal AuthUserPrincipal user) {
    return bookingService.list(user);
  }

  @PostMapping
  public BookingDto create(
      @AuthenticationPrincipal AuthUserPrincipal user, @Valid @RequestBody CreateBookingRequest body) {
    return bookingService.create(user, body);
  }

  @GetMapping("/{id}")
  public BookingDto get(@AuthenticationPrincipal AuthUserPrincipal user, @PathVariable String id) {
    return bookingService.get(user, id);
  }

  @PostMapping("/{id}/send")
  public BookingDto send(@AuthenticationPrincipal AuthUserPrincipal user, @PathVariable String id) {
    return bookingService.send(user, id);
  }
}
