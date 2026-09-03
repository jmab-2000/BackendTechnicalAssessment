# Candidate brief — 1-day Spring check

**Time:** 1 working day.  
**Goal:** Show you can work with **Spring Boot structure** (controller → service → in-memory list) and ship **working JSON APIs**. No database, no Excel, no Azure.

---

## Stack (already set up)

- Java 21, Spring Boot 3.4, Spring Security JWT, validation, springdoc
- Data: `InMemoryStore` — `ArrayList`s for users, customers, contracts, bookings
- **Do not** add JPA, Flyway, Redis, or extra Maven modules

Run:

```bash
cd backend-spring
mvn spring-boot:run
```

Swagger: http://localhost:8080/swagger-ui.html  
Password for all demo users: `Password123!`

| Email | Role |
|-------|------|
| alex.rivera@obt.demo | sales (contract `1200012345`) |
| jordan.lee@obt.demo | sales (contract `N/A-XYZ`) |
| scm.ops@obt.demo | scm |
| admin@obt.demo | admin |

---

## Already working (copy this pattern)

| Method | Path | Notes |
|--------|------|--------|
| POST | `/api/auth/login` | `{ "email", "password" }` → `{ token, user }` |
| GET | `/api/auth/me` | `Authorization: Bearer …` |
| GET | `/api/master/customers` | from `store.customers` |
| GET | `/api/master/contracts` | **sales** only sees own `salesUserId` |

Look at `MasterController` → `MasterService` → `InMemoryStore`.

---

## Your task — make booking APIs work

Implement `BookingService` (controllers are already wired). Persist in `store.bookings` (`ArrayList`).

| Method | Path | Behaviour |
|--------|------|-----------|
| GET | `/api/bookings` | List. Role `sales` only sees bookings whose **contract** belongs to them. Admin/SCM see all. |
| POST | `/api/bookings` | Body: `{ "contractRef", "poNumber", "treatmentQty" }`. Create `status=draft`. 404 unknown contract. Sales cannot use another sales user’s contract. |
| GET | `/api/bookings/{id}` | Same isolation as list. 404 if missing / not allowed. |
| POST | `/api/bookings/{id}/send` | `draft` → `sent`. 400 if not draft. Same isolation. |

JSON fields: `id`, `contractRef`, `createdBy`, `status`, `poNumber`, `treatmentQty`.

Use `ApiException` for 400/403/404. Use `SalesScope.isSalesScoped`. Generate ids with `UUID.randomUUID()`.

**Out of scope for today:** 462 XLSX, PO files, Submitted/Error, Flyway, Angular.

---

## Tests to add

At least one MockMvc (or service) test:

1. Login as Alex, create a booking on `1200012345`, GET list size 1.
2. Login as Jordan, GET list does **not** include Alex’s booking.
3. Send: status becomes `sent`; sending again is 400.

Existing tests (`AuthControllerTest`, context load) must still pass: `mvn test`.