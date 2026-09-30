# Order Booking Tool — 1-day Spring assessment

In-memory Spring Boot API (no database). Candidate handout: **[CANDIDATE.md](./CANDIDATE.md)**.

```bash
mvn spring-boot:run
mvn test
```

- API: http://localhost:8080  
- Swagger: http://localhost:8080/swagger-ui.html  
- Login: `POST /api/auth/login` with `alex.rivera@obt.demo` / `Password123!`

Skeleton already implements auth + master list. Candidate implements **booking** APIs on `InMemoryStore.bookings`.
