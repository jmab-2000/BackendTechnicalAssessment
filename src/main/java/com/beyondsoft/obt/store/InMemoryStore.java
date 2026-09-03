package com.beyondsoft.obt.store;

import com.beyondsoft.obt.booking.Booking;
import com.beyondsoft.obt.identity.User;
import com.beyondsoft.obt.identity.UserRole;
import com.beyondsoft.obt.master.Contract;
import com.beyondsoft.obt.master.Customer;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * In-memory data. No database. Candidate: read/write these lists from services (do not put lists in
 * controllers).
 */
@Component
public class InMemoryStore {

  public static final String SALES_ONE_ID = "11111111-1111-1111-1111-111111111111";
  public static final String SALES_TWO_ID = "22222222-2222-2222-2222-222222222222";
  public static final String SCM_ID = "33333333-3333-3333-3333-333333333333";
  public static final String ADMIN_ID = "44444444-4444-4444-4444-444444444444";
  public static final String DEMO_PASSWORD = "Password123!";
  public static final String SALES_ONE_EMAIL = "alex.rivera@obt.demo";
  public static final String SALES_TWO_EMAIL = "jordan.lee@obt.demo";
  public static final String SCM_EMAIL = "scm.ops@obt.demo";
  public static final String ADMIN_EMAIL = "admin@obt.demo";

  public final List<User> users = new ArrayList<>();
  public final List<Customer> customers = new ArrayList<>();
  public final List<Contract> contracts = new ArrayList<>();
  public final List<Booking> bookings = new ArrayList<>();

  private final PasswordEncoder passwordEncoder;

  public InMemoryStore(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @PostConstruct
  void seed() {
    String hash = passwordEncoder.encode(DEMO_PASSWORD);
    users.add(user(SALES_ONE_ID, SALES_ONE_EMAIL, "Alex Rivera", UserRole.sales, "SALES-01", hash));
    users.add(user(SALES_TWO_ID, SALES_TWO_EMAIL, "Jordan Lee", UserRole.sales, "SALES-02", hash));
    users.add(user(SCM_ID, SCM_EMAIL, "SCM Operations", UserRole.scm, null, hash));
    users.add(user(ADMIN_ID, ADMIN_EMAIL, "System Admin", UserRole.admin, null, hash));

    customers.add(new Customer("21002100", "Hospital ABC"));
    customers.add(new Customer("21008800", "Hospital XYZ"));

    Contract rar = new Contract();
    rar.setContractRef("1200012345");
    rar.setSoldTo("21002100");
    rar.setSalesUserId(SALES_ONE_ID);
    rar.setSapRarRef("RAR-ABC-001");
    contracts.add(rar);

    Contract standard = new Contract();
    standard.setContractRef("N/A-XYZ");
    standard.setSoldTo("21008800");
    standard.setSalesUserId(SALES_TWO_ID);
    standard.setSapRarRef(null);
    contracts.add(standard);
  }

  private static User user(
      String id, String email, String name, UserRole role, String salesCode, String hash) {
    User u = new User();
    u.setId(id);
    u.setEmail(email);
    u.setFullName(name);
    u.setRole(role);
    u.setSalesRepCode(salesCode);
    u.setPasswordHash(hash);
    return u;
  }
}
