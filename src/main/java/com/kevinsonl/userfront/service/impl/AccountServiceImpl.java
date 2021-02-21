package com.kevinsonl.userfront.service.impl;

import com.kevinsonl.userfront.dao.PrimaryAccountDao;
import com.kevinsonl.userfront.dao.SavingsAccountDao;
import com.kevinsonl.userfront.domain.PrimaryAccount;
import com.kevinsonl.userfront.domain.SavingsAccount;
import com.kevinsonl.userfront.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.desktop.SystemEventListener;
import java.math.BigDecimal;

@Service
public class AccountServiceImpl implements AccountService {

  // as initial account number
  public static int nextAccountNumber = 8761494;

  @Autowired
  private PrimaryAccountDao primaryAccountDao;

  @Autowired
  private SavingsAccountDao savingsAccountDao;

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
}
