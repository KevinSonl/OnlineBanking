package com.kevinsonl.userfront.service;

import com.kevinsonl.userfront.domain.PrimaryAccount;
import com.kevinsonl.userfront.domain.PrimaryTransaction;
import com.kevinsonl.userfront.domain.Recipient;
import com.kevinsonl.userfront.domain.SavingsAccount;
import com.kevinsonl.userfront.domain.SavingsTransaction;

import java.security.Principal;
import java.util.List;

public interface TransactionService {

  List<PrimaryTransaction> findPrimaryTransactionList(String username);

  List<SavingsTransaction> findSavingsTransactionList(String username);

  void savePrimaryDepositTransaction(PrimaryTransaction primaryTransaction);

  void saveSavingsDepositTransaction(SavingsTransaction savingsTransaction);

  void savePrimaryWithdrawTransaction(PrimaryTransaction primaryTransaction);

  void saveSavingsWithdrawTransaction(SavingsTransaction savingsTransaction);

  void betweenAccountsTransfer(String transferFrom, String transferTo, String amount, PrimaryAccount primaryAccount, SavingsAccount savingsAccount);

  List<Recipient> findRecipientList(Principal principal);

  Recipient findRecipientByName(String recipientName);

  Recipient saveRecipient(Recipient recipient);

  void deleteRecipientByName(String recipientName);

  void toSomeoneElseTransfer(Recipient recipient, String accountType, String amount, PrimaryAccount primaryAccount, SavingsAccount savingsAccount);
}
