package com.kevinsonl.userfront.service;

import com.kevinsonl.userfront.domain.Appointment;

import java.util.List;


public interface AppointmentService {
	Appointment createAppointment(Appointment appointment);

    List<Appointment> findAll();

    Appointment findAppointment(Long id);

    void confirmAppointment(Long id);
}
