package com.kevinsonl.userfront.dao;

import com.kevinsonl.userfront.domain.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserDao extends CrudRepository<User, Long> {

  //@Query("SELECT user from User user where user.username=:username")
  User findByUsername(String username);

  //@Query("SELECT a FROM User a WHERE a.email=:email")
  User findByEmail(String email);

}
