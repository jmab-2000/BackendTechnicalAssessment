package com.beyondsoft.obt.master;

import com.beyondsoft.obt.master.dto.ContractDto;
import com.beyondsoft.obt.master.dto.CustomerDto;
import com.beyondsoft.obt.security.AuthUserPrincipal;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/master")
@Tag(name = "Master")
public class MasterController {

  private final MasterService masterService;

  public MasterController(MasterService masterService) {
    this.masterService = masterService;
  }

  @GetMapping("/customers")
  public List<CustomerDto> customers() {
    return masterService.listCustomers();
  }

  @GetMapping("/contracts")
  public List<ContractDto> contracts(@AuthenticationPrincipal AuthUserPrincipal user) {
    return masterService.listContracts(user);
  }
}
