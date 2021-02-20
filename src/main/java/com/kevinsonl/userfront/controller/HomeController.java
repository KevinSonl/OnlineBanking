package com.kevinsonl.userfront.controller;

import com.kevinsonl.userfront.domain.User;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashSet;

@Controller
public class HomeController {

  @RequestMapping("/index")
  public String home() {
    //refer to index.html file in template
    return "index";
  }

  @RequestMapping("/")
  public String index() {
    return "redirect:/index";
  }

  @RequestMapping(value = "/signup", method = RequestMethod.GET)
  public String signup(Model model) {
    //create new user and bind it to the front end with variable name user
    User user = new User();

    model.addAttribute("user", user);
    return "signup";
  }

  @RequestMapping(value = "/signup", method = RequestMethod.POST)
  public void signupPost(@ModelAttribute("ser")User user, Model model) {
    // retrive user info we filled in form and make it as a object
/*    if (userService.checkUserExists(user.getUsername(), user.getEmail())) {
      // user service to check if username or email already exist!!
      if (userService.checkEmailExists(user.getEmail())) {
        model.addAttribute("emailExists", true);
      }

      if (userService.checkUsernameExists(user.getUsername())) {
        model.addAttribute("usernameExists", true);
      }

      return "signup";
    } else {
      Set<UserRole> userRoles = new HashSet<>();
      userRoles.add(new UserRole(user, roleDao.findByName("USER")));
      userService.createUser(user, userRoles);

      return "redireact:/";
    }*/

  }

}
