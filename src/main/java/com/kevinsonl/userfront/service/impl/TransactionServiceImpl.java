package com.kevinsonl.userfront.service.impl;

import com.kevinsonl.userfront.dao.PrimaryAccountDao;
import com.kevinsonl.userfront.dao.PrimaryTransactionDao;
import com.kevinsonl.userfront.dao.RecipientDao;
import com.kevinsonl.userfront.dao.SavingsAccountDao;
import com.kevinsonl.userfront.dao.SavingsTransactionDao;
import com.kevinsonl.userfront.domain.PrimaryAccount;
import com.kevinsonl.userfront.domain.PrimaryTransaction;
import com.kevinsonl.userfront.domain.Recipient;
import com.kevinsonl.userfront.domain.SavingsAccount;
import com.kevinsonl.userfront.domain.SavingsTransaction;
import com.kevinsonl.userfront.domain.User;
import com.kevinsonl.userfront.service.TransactionService;
import com.kevinsonl.userfront.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

  @Autowired
  private UserService userService;

  @Autowired
  private PrimaryTransactionDao primaryTransactionDao;

  @Autowired
  private SavingsTransactionDao savingsTransactionDao;

  @Autowired
  private PrimaryAccountDao primaryAccountDao;

  @Autowired
  private SavingsAccountDao savingsAccountDao;

  @Autowired
  private RecipientDao recipientDao;



  @Override
  public List<PrimaryTransaction> findPrimaryTransactionList(String username) {
    User user = userService.findByUsername(username);
    List<PrimaryTransaction> primaryTransactionList =  user.getPrimaryAccount().getPrimaryTransactions();

    return primaryTransactionList;
  }

  @Override
  public List<SavingsTransaction> findSavingsTransactionList(String username) {
    User user = userService.findByUsername(username);
    List<SavingsTransaction> savingsTransactionList =  user.getSavingsAccount().getSavingsTransactions();

    return savingsTransactionList;
  }

  @Override
  public void savePrimaryDepositTransaction(PrimaryTransaction primaryTransaction) {
    primaryTransactionDao.save(primaryTransaction);
  }

  @Override
  public void saveSavingsDepositTransaction(SavingsTransaction savingsTransaction) {
    savingsTransactionDao.save(savingsTransaction);
  }

  @Override
  public void savePrimaryWithdrawTransaction(PrimaryTransaction primaryTransaction) {
    primaryTransactionDao.save(primaryTransaction);
  }

  @Override
  public void saveSavingsWithdrawTransaction(SavingsTransaction savingsTransaction) {
    savingsTransactionDao.save(savingsTransaction);
  }

  @Override
  public void betweenAccountsTransfer(String transferFrom, String transferTo, String amount, PrimaryAccount primaryAccount, SavingsAccount savingsAccount) {
    if (transferFrom.equalsIgnoreCase("Primary") && transferTo.equalsIgnoreCase("Savings")) {
      primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
      savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().add(new BigDecimal(amount)));
      primaryAccountDao.save(primaryAccount);
      savingsAccountDao.save(savingsAccount);

      Date date = new Date();

      PrimaryTransaction primaryTransaction = new PrimaryTransaction(date, "Between account transfer from "+transferFrom+" to "+transferTo, "Account", "Finished", Double.parseDouble(amount), primaryAccount.getAccountBalance(), primaryAccount);
      primaryTransactionDao.save(primaryTransaction);
    } else if (transferFrom.equalsIgnoreCase("Savings") && transferTo.equalsIgnoreCase("Primary")) {
      primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().add(new BigDecimal(amount)));
      savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
      primaryAccountDao.save(primaryAccount);
      savingsAccountDao.save(savingsAccount);

      Date date = new Date();

      SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Between account transfer from "+transferFrom+" to "+transferTo, "Transfer", "Finished", Double.parseDouble(amount), savingsAccount.getAccountBalance(), savingsAccount);
      savingsTransactionDao.save(savingsTransaction);
    } else {
      try {
        throw new Exception("Invalid Transfer");
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public List<Recipient> findRecipientList(Principal principal) {
    String username = principal.getName();
    List<Recipient> recipientList = recipientDao.findAll().stream() 			//convert list to stream
            .filter(recipient -> username.equals(recipient.getUser().getUsername()))	//filters the line, equals to username
            .collect(Collectors.toList());

    return recipientList;
  }

  public Recipient saveRecipient(Recipient recipient) {
    return recipientDao.save(recipient);
  }

  public Recipient findRecipientByName(String recipientName) {
    return recipientDao.findByName(recipientName);
  }

  public void deleteRecipientByName(String recipientName) {
    recipientDao.deleteByName(recipientName);
  }

  @Override
  public void toSomeoneElseTransfer(Recipient recipient, String accountType, String amount, PrimaryAccount primaryAccount, SavingsAccount savingsAccount) {
    if (accountType.equalsIgnoreCase("Primary")) {
      primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
      primaryAccountDao.save(primaryAccount);

      Date date = new Date();

      PrimaryTransaction primaryTransaction = new PrimaryTransaction(date, "Transfer to recipient "+recipient.getName(), "Transfer", "Finished", Double.parseDouble(amount), primaryAccount.getAccountBalance(), primaryAccount);
      primaryTransactionDao.save(primaryTransaction);
    } else if (accountType.equalsIgnoreCase("Savings")) {
      savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
      savingsAccountDao.save(savingsAccount);

      Date date = new Date();

      SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Transfer to recipient "+recipient.getName(), "Transfer", "Finished", Double.parseDouble(amount), savingsAccount.getAccountBalance(), savingsAccount);
      savingsTransactionDao.save(savingsTransaction);
    }
  }
}
