package com.beyondsoft.obt.master;

import com.beyondsoft.obt.booking.BookingRules;
import com.beyondsoft.obt.booking.SalesScope;
import com.beyondsoft.obt.master.dto.ContractDto;
import com.beyondsoft.obt.master.dto.CustomerDto;
import com.beyondsoft.obt.security.AuthUserPrincipal;
import com.beyondsoft.obt.store.InMemoryStore;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MasterService {

  private final InMemoryStore store;

  public MasterService(InMemoryStore store) {
    this.store = store;
  }

  public List<CustomerDto> listCustomers() {
    return store.customers.stream().map(c -> new CustomerDto(c.getSapId(), c.getName())).toList();
  }

  public List<ContractDto> listContracts(AuthUserPrincipal user) {
    return store.contracts.stream()
        .filter(
            c ->
                !SalesScope.isSalesScoped(user)
                    || user.getId().equals(c.getSalesUserId()))
        .map(
            c ->
                new ContractDto(
                    c.getContractRef(),
                    c.getSoldTo(),
                    c.getSalesUserId(),
                    c.getSapRarRef(),
                    BookingRules.hasSapRar(c.getSapRarRef())))
        .toList();
  }
}
