package com.kevinsonl.userfront.dao;

import com.kevinsonl.userfront.domain.PrimaryTransaction;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PrimaryTransactionDao extends CrudRepository<PrimaryTransaction, Long> {

  List<PrimaryTransaction> findAll();

}
