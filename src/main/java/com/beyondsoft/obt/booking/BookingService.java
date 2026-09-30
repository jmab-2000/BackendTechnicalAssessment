package com.beyondsoft.obt.booking;

import com.beyondsoft.obt.booking.dto.BookingDto;
import com.beyondsoft.obt.booking.dto.CreateBookingRequest;
import com.beyondsoft.obt.common.ApiException;
import com.beyondsoft.obt.identity.UserRole;
import com.beyondsoft.obt.master.Contract;
import com.beyondsoft.obt.master.dto.ContractDto;
import com.beyondsoft.obt.security.AuthUserPrincipal;
import com.beyondsoft.obt.store.InMemoryStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

/**
 * Candidate task: implement these methods using {@link InMemoryStore#bookings}
 * (ArrayList). Follow the same controller → service → store pattern as
 * {@link com.beyondsoft.obt.master.MasterService}.
 */
@Service
public class BookingService {

	private final InMemoryStore store;

	public BookingService(InMemoryStore store) {
		this.store = store;
	}

	public List<BookingDto> list(AuthUserPrincipal user) {

		if (!(UserRole.admin == user.getRole()) && !(UserRole.scm == user.getRole())
				&& !(UserRole.sales == user.getRole())) {

			throw ApiException.forbidden("User not allowed");

		}

		return store.bookings.stream()
				.filter(booking -> UserRole.admin == user.getRole() || UserRole.scm == user.getRole()
						|| (SalesScope.isSalesScoped(user) && store.contracts.stream()
								.anyMatch(contract -> contract.getContractRef().equals(booking.getContractRef())
										&& contract.getSalesUserId().equals(user.getId()))))
				.map(booking -> new BookingDto(booking.getId(), booking.getContractRef(), booking.getCreatedBy(),
						booking.getStatus(), booking.getPoNumber(), booking.getTreatmentQty()))
				.toList();

		// throw ApiException.notImplemented("Implement: list bookings. sales sees only
		// bookings whose contract.salesUserId is self.");

	}

	public BookingDto create(AuthUserPrincipal user, CreateBookingRequest request) {

		if (!(UserRole.admin == user.getRole()) && !(UserRole.scm == user.getRole())
				&& !(UserRole.sales == user.getRole())) {

			throw ApiException.forbidden("User not allowed");

		}

		Optional<Contract> contract = store.contracts.stream()
				.filter(c -> request.contractRef().equals(c.getContractRef())).findFirst();

		if (contract.isEmpty()) {
			throw ApiException.notFound("Unknown Contract");
		}

		if (SalesScope.isSalesScoped(user) // if sales
				&& !user.getId().equals(contract.get().getSalesUserId())) { // and not user ID sales of contract
			throw ApiException.forbidden("User cannot create booking with this contract");
		}

		Booking booking = new Booking();
		booking.setId(UUID.randomUUID().toString());
		booking.setContractRef(request.contractRef());
		booking.setCreatedBy(user.getFullName());
		booking.setStatus("draft");
		booking.setPoNumber(request.poNumber());
		booking.setTreatmentQty(request.treatmentQty());

		store.bookings.add(booking);

		return new BookingDto(booking.getId(), booking.getContractRef(), booking.getCreatedBy(), booking.getStatus(),
				booking.getPoNumber(), booking.getTreatmentQty());

		// throw ApiException.notImplemented("Implement: create status=draft on
		// store.bookings. Reject unknown contract. Sales may only use own contract.");
	}

	public BookingDto get(AuthUserPrincipal user, String id) {
		
		if (!(UserRole.admin == user.getRole()) && !(UserRole.scm == user.getRole())
				&& !(UserRole.sales == user.getRole())) {

			throw ApiException.forbidden("User not allowed");

		}
		
		Optional<BookingDto> bookingDto = store.bookings.stream()
				.filter(booking -> booking.getId().equals(id)
						
						&& (!SalesScope.isSalesScoped(user) 
								|| store.contracts.stream()
									.anyMatch( contract -> contract.getContractRef().equals(booking.getContractRef())
									&& contract.getSalesUserId().equals(user.getId())
									)
							)
						
						)
				.findFirst()
				.map(booking -> new BookingDto(booking.getId(), booking.getContractRef(), booking.getCreatedBy(), booking.getStatus(),
						booking.getPoNumber(), booking.getTreatmentQty()));
		
		if(bookingDto.isEmpty()) {
			throw ApiException.notFound("Booking not found");
		}
		
		return bookingDto.get();
		
		//throw ApiException.notImplemented("Implement: get by id with the "
		//	+ "same sales isolation as list.");
	}

	public BookingDto send(AuthUserPrincipal user, String id) {
		

		if (!(UserRole.admin == user.getRole()) && !(UserRole.scm == user.getRole())
				&& !(UserRole.sales == user.getRole())) {

			throw ApiException.forbidden("User not allowed");

		}
		
		Optional<BookingDto> bookingDto = store.bookings.stream()
				.filter(booking -> booking.getId().equals(id)
						&& (!SalesScope.isSalesScoped(user) 
								|| store.contracts.stream()
									.anyMatch( contract -> contract.getContractRef().equals(booking.getContractRef())
									&& contract.getSalesUserId().equals(user.getId())
									)
							)
						
						)
				.findFirst()
				.map(booking -> {
					
					if ("Sent".equalsIgnoreCase(booking.getStatus())) {
			            throw ApiException.badRequest("Booking has already been sent");
			        }

			        booking.setStatus("Sent");
					
					return new BookingDto(booking.getId(), 
							booking.getContractRef(), 
							booking.getCreatedBy(), 
							booking.getStatus(),
							booking.getPoNumber(), 
							booking.getTreatmentQty());
					}
				);
		
		if(bookingDto.isEmpty()) {
			throw ApiException.notFound("Booking not found");
		}
		
		return bookingDto.get();
		

		//throw ApiException.notImplemented("Implement: draft → sent only. 400 if not draft.");
	}
}
