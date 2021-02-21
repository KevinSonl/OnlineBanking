package com.kevinsonl.userfront.service;

import com.kevinsonl.userfront.domain.PrimaryAccount;
import com.kevinsonl.userfront.domain.SavingsAccount;

public interface AccountService {
  PrimaryAccount createPrimaryAccount();

  SavingsAccount createSavingsAccount();
}
