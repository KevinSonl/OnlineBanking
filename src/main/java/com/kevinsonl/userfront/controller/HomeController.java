package com.kevinsonl.userfront.controller;

import com.kevinsonl.userfront.dao.RoleDao;
import com.kevinsonl.userfront.domain.PrimaryAccount;
import com.kevinsonl.userfront.domain.SavingsAccount;
import com.kevinsonl.userfront.domain.User;
import com.kevinsonl.userfront.domain.security.UserRole;
import com.kevinsonl.userfront.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;


@Controller
public class HomeController {

  @Autowired
  private UserService userService;

  // very simple in roleDao so that we can use dao directly
  @Autowired
  private RoleDao roleDao;

  @RequestMapping("/index")
  public String home() {
    //refer to index.html file in template
    return "index";
  }

  @RequestMapping("/")
  public String index() {
    return "redirect:/index";
  }

  @RequestMapping(value = "/getUser", method = RequestMethod.GET)
  public String getUser() {
    User user = userService.findByEmail("huangwq123@outlook.com");
    System.err.println(user.getUsername().toString());

    return "index";
  }

  @RequestMapping(value = "/signup", method = RequestMethod.GET)
  public String signup(Model model) {
    //create new user and bind it to the front end with variable name user
    User user = new User();
    model.addAttribute("user", user);
    //System.err.println("User from FORM: "+ user.toString());

    return "signup";
  }

  @RequestMapping(value = "/signup", method = RequestMethod.POST)
  public String signupPost(@ModelAttribute("user")User user, Model model) {
    // retrive user info we filled in form and make it as a object
    System.err.println("User from FORM: "+ user.toString());

    if (userService.checkUserExists(user.getUsername(), user.getEmail())) {
      // user service to check if username or email already exist!!
      if (userService.checkEmailExists(user.getEmail())) {
        model.addAttribute("emailExists", true);
        System.err.println("Email 已经存在222");
      }

      if (userService.checkUsernameExists(user.getUsername())) {
        model.addAttribute("usernameExists", true);
        System.err.println("用户名 已经存在222");
      }

      //dont save and display error message by conditional rendering ny thymeleaf
      return "signup";

    } else {
      // use service to save user into database
      Set<UserRole> userRoles = new HashSet<>();
      userService.createUser(user, userRoles);
      return "redirect:/index";
    }

  }

  //principal: the person who logged in
  //model is to HTML file
  @RequestMapping("/userFront")
  public String userFront(Principal principal, Model model) {
    User user = userService.findByUsername(principal.getName());
    PrimaryAccount primaryAccount = user.getPrimaryAccount();
    SavingsAccount savingsAccount = user.getSavingsAccount();

    model.addAttribute("primaryAccount", primaryAccount);
    model.addAttribute("savingsAccount", savingsAccount);

    return "userFront";
  }

}
