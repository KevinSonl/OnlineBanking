package com.kevinsonl.userfront.service;

import com.kevinsonl.userfront.domain.PrimaryAccount;
import com.kevinsonl.userfront.domain.PrimaryTransaction;
import com.kevinsonl.userfront.domain.SavingsAccount;
import com.kevinsonl.userfront.domain.SavingsTransaction;

import java.util.List;

public interface TransactionService {

  List<PrimaryTransaction> findPrimaryTransactionList(String username);

  List<SavingsTransaction> findSavingsTransactionList(String username);

  void savePrimaryDepositTransaction(PrimaryTransaction primaryTransaction);

  void saveSavingsDepositTransaction(SavingsTransaction savingsTransaction);

  void savePrimaryWithdrawTransaction(PrimaryTransaction primaryTransaction);

  void saveSavingsWithdrawTransaction(SavingsTransaction savingsTransaction);

  void betweenAccountsTransfer(String transferFrom, String transferTo, String amount, PrimaryAccount primaryAccount, SavingsAccount savingsAccount);
}
