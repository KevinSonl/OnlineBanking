package com.kevinsonl.userfront.controller;

import com.kevinsonl.userfront.domain.PrimaryAccount;
import com.kevinsonl.userfront.domain.PrimaryTransaction;
import com.kevinsonl.userfront.domain.SavingsAccount;
import com.kevinsonl.userfront.domain.SavingsTransaction;
import com.kevinsonl.userfront.domain.User;

import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kevinsonl.userfront.service.AccountService;
import com.kevinsonl.userfront.service.TransactionService;
import com.kevinsonl.userfront.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {

  @Autowired
  private UserService userService;

  @Autowired
  private AccountService accountService;

  @Autowired
  private TransactionService transactionService;

  //account detail page,
  @RequestMapping("/primaryAccount")
  public String primaryAccount(Model model, Principal principal) {
    List<PrimaryTransaction> primaryTransactionList = transactionService.findPrimaryTransactionList(principal.getName());

    User user = userService.findByUsername(principal.getName());
    PrimaryAccount primaryAccount = user.getPrimaryAccount();

    model.addAttribute("primaryAccount", primaryAccount);
    model.addAttribute("primaryTransactionList", primaryTransactionList);
    return "primaryAccount";
  }

  @RequestMapping("/savingsAccount")
  public String savingsAccount(Model model, Principal principal) {
    List<SavingsTransaction> savingsTransactionList = transactionService.findSavingsTransactionList(principal.getName());
    User user = userService.findByUsername(principal.getName());
    SavingsAccount savingsAccount = user.getSavingsAccount();

    model.addAttribute("savingsAccount", savingsAccount);
    model.addAttribute("savingsTransactionList", savingsTransactionList);

    return "savingsAccount";
  }

  // just go the deposit page with initial value accountType and amount
  @RequestMapping(value = "/deposit", method = RequestMethod.GET)
  public String deposit(Model model) {
    //deposit page model has accountType and amount attribute need to be display, set as ""
    model.addAttribute("accountType", "");
    model.addAttribute("amount", "");

    return "deposit";
  }

  // get two data from html page and store into database
  @RequestMapping(value = "/deposit", method = RequestMethod.POST)
  public String deposit(@ModelAttribute("amount") String amount, @ModelAttribute("accountType") String accountType, Principal principal) {
    //post the data collected by html to the database
    accountService.deposit(accountType, Double.parseDouble(amount), principal);

    return "redirect:/userFront";
  }

  @RequestMapping(value = "/withdraw", method = RequestMethod.GET)
  public String withdraw(Model model) {
    model.addAttribute("accountType", "");
    model.addAttribute("amount", "");

    return "withdraw";
  }

  @RequestMapping(value = "/withdraw", method = RequestMethod.POST)
  public String withdraw(@ModelAttribute("amount") String amount, @ModelAttribute("accountType") String accountType, Principal principal) {
    accountService.withdraw(accountType, Double.parseDouble(amount), principal);

    return "redirect:/userFront";
  }

}
