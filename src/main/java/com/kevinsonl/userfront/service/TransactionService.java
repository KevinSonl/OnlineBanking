package com.kevinsonl.userfront.service;

import com.kevinsonl.userfront.domain.PrimaryTransaction;
import com.kevinsonl.userfront.domain.SavingsTransaction;

import java.util.List;

public interface TransactionService {

  List<PrimaryTransaction> findPrimaryTransactionList(String username);

  List<SavingsTransaction> findSavingsTransactionList(String username);

  void savePrimaryDepositTransaction(PrimaryTransaction primaryTransaction);

  void saveSavingsDepositTransaction(SavingsTransaction savingsTransaction);

  void savePrimaryWithdrawTransaction(PrimaryTransaction primaryTransaction);

  void saveSavingsWithdrawTransaction(SavingsTransaction savingsTransaction);

}
