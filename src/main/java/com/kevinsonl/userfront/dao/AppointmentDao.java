package com.kevinsonl.userfront.dao;

import com.kevinsonl.userfront.domain.Appointment;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface AppointmentDao extends CrudRepository<Appointment, Long> {

    List<Appointment> findAll();

}
