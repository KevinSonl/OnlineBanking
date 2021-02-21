package com.kevinsonl.userfront.dao;

import com.kevinsonl.userfront.domain.security.Role;

import org.springframework.data.repository.CrudRepository;

public interface RoleDao extends CrudRepository<Role, Integer> {
  Role findByName(String name);
}
