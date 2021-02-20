package com.kevinsonl.userfront.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class PrimaryAccount {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private int accountNumber;
  private BigDecimal accountBalance;

  // list of transaction happens in primaryAccount
  // In PrimaryTransaction, there exist primaryAccount field, thus link together
  @OneToMany(mappedBy = "primaryAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonIgnore   //如果是two way reference，则要添加这个
  private List<PrimaryTransaction> primaryTransactions;

  public Long getId() {
    return id;
  }

  public int getAccountNumber() {
    return accountNumber;
  }

  public BigDecimal getAccountBalance() {
    return accountBalance;
  }

  public List<PrimaryTransaction> getPrimaryTransactions() {
    return primaryTransactions;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setAccountNumber(int accountNumber) {
    this.accountNumber = accountNumber;
  }

  public void setAccountBalance(BigDecimal accountBalance) {
    this.accountBalance = accountBalance;
  }

  public void setPrimaryTransactions(List<PrimaryTransaction> primaryTransactions) {
    this.primaryTransactions = primaryTransactions;
  }
}

