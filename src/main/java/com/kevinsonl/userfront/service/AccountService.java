package com.kevinsonl.userfront.service;

import com.kevinsonl.userfront.domain.PrimaryAccount;
import com.kevinsonl.userfront.domain.SavingsAccount;

import java.security.Principal;

public interface AccountService {
  PrimaryAccount createPrimaryAccount();

  SavingsAccount createSavingsAccount();

  void deposit(String accountType, double amount, Principal principal);

  void withdraw(String accountType, double parseDouble, Principal principal);
}
