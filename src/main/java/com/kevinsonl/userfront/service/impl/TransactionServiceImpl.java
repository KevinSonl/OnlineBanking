package com.kevinsonl.userfront.service.impl;

import com.kevinsonl.userfront.dao.PrimaryTransactionDao;
import com.kevinsonl.userfront.dao.SavingsTransactionDao;
import com.kevinsonl.userfront.domain.PrimaryTransaction;
import com.kevinsonl.userfront.domain.SavingsTransaction;
import com.kevinsonl.userfront.domain.User;
import com.kevinsonl.userfront.service.TransactionService;
import com.kevinsonl.userfront.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

  @Autowired
  private UserService userService;

  @Autowired
  private PrimaryTransactionDao primaryTransactionDao;

  @Autowired
  private SavingsTransactionDao savingsTransactionDao;


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
}
