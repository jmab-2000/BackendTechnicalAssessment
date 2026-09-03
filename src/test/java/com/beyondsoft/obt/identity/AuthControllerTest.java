package com.beyondsoft.obt.identity;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.beyondsoft.obt.store.InMemoryStore;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @Test
  void loginRejectsBadPassword() throws Exception {
    mockMvc
        .perform(
            post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(
                        Map.of("email", InMemoryStore.ADMIN_EMAIL, "password", "wrong"))))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void loginAndMe() throws Exception {
    String body =
        mockMvc
            .perform(
                post("/api/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        objectMapper.writeValueAsString(
                            Map.of(
                                "email",
                                InMemoryStore.SALES_ONE_EMAIL,
                                "password",
                                InMemoryStore.DEMO_PASSWORD))))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").isString())
            .andExpect(jsonPath("$.user.role").value("sales"))
            .andReturn()
            .getResponse()
            .getContentAsString();

    String token = objectMapper.readTree(body).get("token").asText();
    mockMvc
        .perform(get("/api/auth/me").header("Authorization", "Bearer " + token))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.user.email").value(InMemoryStore.SALES_ONE_EMAIL));
  }

  @Test
  void customersRequireAuth() throws Exception {
    mockMvc.perform(get("/api/master/customers")).andExpect(status().isUnauthorized());
  }

  @Test
  void customersAndContractsWithToken() throws Exception {
    String body =
        mockMvc
            .perform(
                post("/api/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        objectMapper.writeValueAsString(
                            Map.of(
                                "email",
                                InMemoryStore.SALES_ONE_EMAIL,
                                "password",
                                InMemoryStore.DEMO_PASSWORD))))
            .andReturn()
            .getResponse()
            .getContentAsString();
    String token = objectMapper.readTree(body).get("token").asText();
    mockMvc
        .perform(get("/api/master/customers").header("Authorization", "Bearer " + token))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].sapId").exists());
    mockMvc
        .perform(get("/api/master/contracts").header("Authorization", "Bearer " + token))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].contractRef").value("1200012345"))
        .andExpect(jsonPath("$.length()").value(1));
  }
}
