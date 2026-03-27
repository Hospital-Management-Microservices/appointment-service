package com.hms.appointment.repository;

import com.hms.appointment.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository = the layer that talks to the database.
 * By extending JpaRepository, Spring automatically gives us:
 * - save(), findById(), findAll(), deleteById(), etc.
 * We just add our custom queries below.
 */
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Find all appointments for a specific patient
    List<Appointment> findByPatientId(Long patientId);

    // Find all appointments for a specific doctor
    List<Appointment> findByDoctorId(Long doctorId);

    // Find all appointments for a specific department
    List<Appointment> findByDepartmentId(Long departmentId);

    // Find appointments by status (e.g. "SCHEDULED", "COMPLETED")
    List<Appointment> findByStatus(String status);

    // Find appointments on a specific date
    List<Appointment> findByAppointmentDate(LocalDate date);

    // Find appointments for a doctor on a specific date (to check availability)
    List<Appointment> findByDoctorIdAndAppointmentDate(Long doctorId, LocalDate date);
}
