package com.kevinsonl.userfront.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.context.annotation.Primary;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "user_id", nullable = false, updatable = false) //add more constraint to one colume
  private Long userId;
  private String username;
  private String password;
  private String firstName;
  private String lastName;
  private String email;
  private String phone;

  private boolean enable=true;

  @OneToOne
  private PrimaryAccount primaryAccount;

  @OneToOne
  private SavingAccount savingAccount;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonIgnore
  private List<Appointment> appointmentList;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Recipient> recipientList;

  public Long getUserId() {
    return userId;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getEmail() {
    return email;
  }

  public String getPhone() {
    return phone;
  }

  public boolean isEnable() {
    return enable;
  }

  public PrimaryAccount getPrimaryAccount() {
    return primaryAccount;
  }

  public SavingAccount getSavingAccount() {
    return savingAccount;
  }

  public List<Appointment> getAppointmentList() {
    return appointmentList;
  }

  public List<Recipient> getRecipientList() {
    return recipientList;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public void setEnable(boolean enable) {
    this.enable = enable;
  }

  public void setPrimaryAccount(PrimaryAccount primaryAccount) {
    this.primaryAccount = primaryAccount;
  }

  public void setSavingAccount(SavingAccount savingAccount) {
    this.savingAccount = savingAccount;
  }

  public void setAppointmentList(List<Appointment> appointmentList) {
    this.appointmentList = appointmentList;
  }

  public void setRecipientList(List<Recipient> recipientList) {
    this.recipientList = recipientList;
  }

  @Override
  public String toString() {
    return "User{" +
            "userId=" + userId +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", phone='" + phone + '\'' +
            ", enable=" + enable +
            ", primaryAccount=" + primaryAccount +
            ", savingAccount=" + savingAccount +
            ", appointmentList=" + appointmentList +
            ", recipientList=" + recipientList +
            '}';
  }
}
