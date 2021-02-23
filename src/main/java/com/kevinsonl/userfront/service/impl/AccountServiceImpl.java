package com.kevinsonl.userfront.service.impl;

import com.kevinsonl.userfront.dao.PrimaryAccountDao;
import com.kevinsonl.userfront.dao.PrimaryTransactionDao;
import com.kevinsonl.userfront.dao.SavingsAccountDao;
import com.kevinsonl.userfront.domain.PrimaryAccount;
import com.kevinsonl.userfront.domain.PrimaryTransaction;
import com.kevinsonl.userfront.domain.SavingsAccount;
import com.kevinsonl.userfront.domain.SavingsTransaction;
import com.kevinsonl.userfront.domain.User;
import com.kevinsonl.userfront.service.AccountService;
import com.kevinsonl.userfront.service.TransactionService;
import com.kevinsonl.userfront.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.desktop.SystemEventListener;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;

@Service
public class AccountServiceImpl implements AccountService {

  // as initial account number
  public static int nextAccountNumber = 8761494;

  @Autowired
  private PrimaryAccountDao primaryAccountDao;

  @Autowired
  private SavingsAccountDao savingsAccountDao;

  @Autowired
  private UserService userService;

  @Autowired
  private TransactionService transactionService;

  /*
  * Init a primary account and save in database
  * return the account number
  * */
  public PrimaryAccount createPrimaryAccount() {
    PrimaryAccount primaryAccount = new PrimaryAccount();
    primaryAccount.setAccountBalance(new BigDecimal(0.0));
    primaryAccount.setAccountNumber(accountGen());

    // save the account in the database
    primaryAccountDao.save(primaryAccount);

    //retrieve the account from database because have to save it to AUTO generate the AccountID
    return primaryAccountDao.findByAccountNumber(primaryAccount.getAccountNumber());
  }

  private int accountGen() {
    System.out.println(++nextAccountNumber);
    return ++nextAccountNumber;
  }

  public SavingsAccount createSavingsAccount() {
    SavingsAccount savingsAccount = new SavingsAccount();
    savingsAccount.setAccountBalance(new BigDecimal(0.0));
    savingsAccount.setAccountNumber(accountGen());

    savingsAccountDao.save(savingsAccount);

    return savingsAccountDao.findByAccountNumber(savingsAccount.getAccountNumber());
  }

  @Override
  public void deposit(String accountType, double amount, Principal principal) {
    User user = userService.findByUsername(principal.getName());

    if (accountType.equalsIgnoreCase("Primary")) {
      PrimaryAccount primaryAccount = user.getPrimaryAccount();
      primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().add(new BigDecimal(amount)));
      //same id will update
      primaryAccountDao.save(primaryAccount);

      //create a transaction record into database
      Date date = new Date();
      PrimaryTransaction primaryTransaction = new PrimaryTransaction(date, "Deposit to Primary Account",
              "Account", "Finished", amount, primaryAccount.getAccountBalance(), primaryAccount);

      transactionService.savePrimaryDepositTransaction(primaryTransaction);

    } else if (accountType.equalsIgnoreCase("Savings")) {
      SavingsAccount savingsAccount = user.getSavingsAccount();
      savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().add(new BigDecimal(amount)));
      //same id will update
      savingsAccountDao.save(savingsAccount);

      //create a transaction record into database
      Date date = new Date();
      SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Deposit to Savings Account",
              "Account", "Finished", amount, savingsAccount.getAccountBalance(), savingsAccount);

      transactionService.saveSavingsDepositTransaction(savingsTransaction);
    }


  }

  @Override
  public void withdraw(String accountType, double amount, Principal principal) {
    User user = userService.findByUsername(principal.getName());

    if (accountType.equalsIgnoreCase("Primary")) {
      PrimaryAccount primaryAccount = user.getPrimaryAccount();
      primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
      //same id will update
      primaryAccountDao.save(primaryAccount);

      //create a transaction record into database
      Date date = new Date();
      PrimaryTransaction primaryTransaction = new PrimaryTransaction(date, "Withdraw from Primary Account",
              "Account", "Finished", amount, primaryAccount.getAccountBalance(), primaryAccount);

      transactionService.savePrimaryWithdrawTransaction(primaryTransaction);

    } else if (accountType.equalsIgnoreCase("Savings")) {
      SavingsAccount savingsAccount = user.getSavingsAccount();
      savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
      //same id will update
      savingsAccountDao.save(savingsAccount);

      //create a transaction record into database
      Date date = new Date();
      SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Withdraw from Savings Account",
              "Account", "Finished", amount, savingsAccount.getAccountBalance(), savingsAccount);

      transactionService.saveSavingsWithdrawTransaction(savingsTransaction);
    }
  }

}
