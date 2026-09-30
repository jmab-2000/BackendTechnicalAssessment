package com.beyondsoft.obt.booking;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;

import com.beyondsoft.obt.store.InMemoryStore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class BookingControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;


	@Test
	void alexCreatesBookingJordanNoBookingsTwiceSendReturns400() throws Exception {

		String alexToken = login(InMemoryStore.SALES_ONE_EMAIL);

	    String bookingBody =
	            mockMvc
	                .perform(
	                    post("/api/bookings")
	                        .header("Authorization", "Bearer " + alexToken)
	                        .contentType(MediaType.APPLICATION_JSON)
	                        .content(
	                            objectMapper.writeValueAsString(
	                                Map.of(
	                                    "contractRef", "1200012345",
	                                    "poNumber", "PO-TEST-001",
	                                    "treatmentQty", 10))))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$.contractRef").value("1200012345"))
	                .andExpect(jsonPath("$.status").value("draft"))
	                .andReturn()
	                .getResponse()
	                .getContentAsString();

	    String bookingId = objectMapper.readTree(bookingBody).get("id").asText();
		
	    mockMvc
			.perform(
					get("/api/bookings")
					.header("Authorization", "Bearer " + alexToken))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(1))
				.andExpect(jsonPath("$[0].id").value(bookingId));

	    String jordanToken = login(InMemoryStore.SALES_TWO_EMAIL);

	    mockMvc
	        .perform(
	            get("/api/bookings")
	                .header("Authorization", "Bearer " + jordanToken))
	        .andExpect(status().isOk())
	        .andExpect(jsonPath("$.length()").value(0));

		mockMvc.perform(
				post("/api/bookings/" + bookingId + "/send")
				.header("Authorization", "Bearer " + alexToken))
			.andExpect(status().isOk()).andExpect(jsonPath("$.id").value(bookingId))
			.andExpect(jsonPath("$.status").value("Sent"));

		mockMvc.perform(
				post("/api/bookings/" + bookingId + "/send")
				.header("Authorization", "Bearer " + alexToken))
				.andExpect(status().isBadRequest());

	}

	private String login(String email) throws Exception {
		
		String body = mockMvc
				.perform(
						post("/api/auth/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper
								.writeValueAsString(
										Map.of(
											"email", email, 
											"password", InMemoryStore.DEMO_PASSWORD)
										)
								)
						)
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		JsonNode response = objectMapper.readTree(body);

		return response.get("token").asText();
	}
}
