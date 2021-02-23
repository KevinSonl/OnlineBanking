package com.kevinsonl.userfront.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class SavingsTransaction {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  private Date date;
  private String description;
  private String type;
  private String status;
  private double amount;
  // use bigDecimal not double
  private BigDecimal availableBalance;

  @ManyToOne
  @JoinColumn(name = "saving_account_id")
  private SavingsAccount savingsAccount;

  public SavingsTransaction() { }

  public Date getDate() {
    return date;
  }

  public SavingsTransaction(Date date, String description, String type, String status, double amount, BigDecimal availableBalance, SavingsAccount savingsAccount) {
    this.date = date;
    this.description = description;
    this.type = type;
    this.status = status;
    this.amount = amount;
    this.availableBalance = availableBalance;
    this.savingsAccount = savingsAccount;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public BigDecimal getAvailableBalance() {
    return availableBalance;
  }

  public void setAvailableBalance(BigDecimal availableBalance) {
    this.availableBalance = availableBalance;
  }

  public SavingsAccount getSavingsAccount() {
    return savingsAccount;
  }

  public void setSavingsAccount(SavingsAccount savingsAccount) {
    this.savingsAccount = savingsAccount;
  }
}
