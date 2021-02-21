package com.kevinsonl.userfront.service.impl;

import com.kevinsonl.userfront.dao.RoleDao;
import com.kevinsonl.userfront.dao.UserDao;
import com.kevinsonl.userfront.domain.User;
import com.kevinsonl.userfront.domain.security.UserRole;
import com.kevinsonl.userfront.service.AccountService;
import com.kevinsonl.userfront.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Set;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

  private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

  @Autowired
  private UserDao userDao;

  @Autowired
  private RoleDao roleDao;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @Autowired
  private AccountService accountService;


  public void save(User user) {
    userDao.save(user);
  }

  /*
  * encrypt the user password and save it to the database
  * user input is user with data from front-end Register FORM
  * create new two accounts for each user
  * */
  @Override
  public User createUser(User user, Set<UserRole> userRoles) {
    User localUser = userDao.findByUsername(user.getUsername());

    if (localUser != null) {
      LOG.info("User with username {} already exist", user.getUsername());
    } else {
      String encryptedPassword = passwordEncoder.encode(user.getPassword());
      user.setPassword(encryptedPassword);

      for (UserRole ur : userRoles) {
        roleDao.save(ur.getRole());
      }

      user.getUserRoles().addAll(userRoles);

      user.setPrimaryAccount(accountService.createPrimaryAccount());
      user.setSavingsAccount(accountService.createSavingsAccount());

      localUser = userDao.save(user);
    }

    return localUser;
  }


  public User findByUsername(String username) {
    return userDao.findByUsername(username);
  }

  public User findByEmail(String email) {
    return userDao.findByEmail(email);
  }


  /*public User createUser(User user, Set<UserRole> userRoles) {
    User localUser = userDao.findByUsername(user.getUsername());

    if (localUser != null) {
      LOG.info("User with username {} already exist. Nothing will be done. ", user.getUsername());
    } else {
      String encryptedPassword = passwordEncoder.encode(user.getPassword());
      user.setPassword(encryptedPassword);

      for (UserRole ur : userRoles) {
        roleDao.save(ur.getRole());
      }

      user.getUserRoles().addAll(userRoles);

      user.setPrimaryAccount(accountService.createPrimaryAccount());
      user.setSavingsAccount(accountService.createSavingsAccount());

      localUser = userDao.save(user);
    }

    return localUser;
  }*/

  public boolean checkUserExists(String username, String email){
    if (checkUsernameExists(username) || checkEmailExists(email)) {
      return true;
    } else {
      return false;
    }
  }

  public boolean checkUsernameExists(String username) {
    if (null != findByUsername(username)) {
      System.err.println("Input email is: " + username);
      return true;
    }

    return false;
  }

  public boolean checkEmailExists(String email) {
    System.err.println("Input email is: " + email);

    if (null != findByEmail(email)) {
      System.err.println("Email 已经存在！！");
      return true;
    }

    return false;
  }

  public User saveUser (User user) {
    return userDao.save(user);
  }

/*  public List<User> findUserList() {
    return userDao.findAll();
  }*/

/*  public void enableUser (String username) {
    User user = findByUsername(username);
    user.setEnabled(true);
    userDao.save(user);
  }

  public void disableUser (String username) {
    User user = findByUsername(username);
    user.setEnabled(false);
    System.out.println(user.isEnabled());
    userDao.save(user);
    System.out.println(username + " is disabled.");
  }*/
}
