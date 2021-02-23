package com.kevinsonl.userfront.controller;

import com.kevinsonl.userfront.domain.PrimaryAccount;
import com.kevinsonl.userfront.domain.SavingsAccount;
import com.kevinsonl.userfront.domain.User;
import com.kevinsonl.userfront.service.TransactionService;
import com.kevinsonl.userfront.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
@RequestMapping("/transfer")
public class TransferController {

  @Autowired
  private TransactionService transactionService;

  @Autowired
  private UserService userService;


  @RequestMapping(value = "betweenAccounts", method = RequestMethod.GET)
  public String betweenAccounts(Model model) {
    model.addAttribute("transferFrom", "");
    model.addAttribute("transferTo", "");
    model.addAttribute("amount", "");

    return "/betweenAccounts";
  }

  @RequestMapping(value = "betweenAccounts", method = RequestMethod.POST)
  public String betweenAccountsPost(
          @ModelAttribute("transferFrom") String transferFrom,
          @ModelAttribute("transferTo") String transferTo,
          @ModelAttribute("amount") String amount,
          Principal principal) {
    User user = userService.findByUsername(principal.getName());
    PrimaryAccount primaryAccount = user.getPrimaryAccount();
    SavingsAccount savingsAccount = user.getSavingsAccount();
    transactionService.betweenAccountsTransfer(transferFrom, transferTo, amount, primaryAccount, savingsAccount);

    return "redirect:/userFront";
  }


}
