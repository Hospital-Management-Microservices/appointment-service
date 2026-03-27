package com.hms.appointment.service;

import com.hms.appointment.client.ExternalServiceClient;
import com.hms.appointment.dto.AppointmentDTO;
import com.hms.appointment.model.Appointment;
import com.hms.appointment.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service layer = where all the business logic lives.
 * Controller calls Service → Service calls Repository (DB).
 */
@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ExternalServiceClient externalServiceClient;

    // =============================================
    // CREATE a new appointment
    // =============================================
    public AppointmentDTO createAppointment(AppointmentDTO dto) {
        Appointment appointment = new Appointment();

        appointment.setPatientId(dto.getPatientId());
        appointment.setDoctorId(dto.getDoctorId());
        appointment.setDepartmentId(dto.getDepartmentId());
        appointment.setAppointmentDate(dto.getAppointmentDate());
        appointment.setAppointmentTime(dto.getAppointmentTime());
        appointment.setReason(dto.getReason());
        appointment.setNotes(dto.getNotes());
        appointment.setStatus(dto.getStatus() != null ? dto.getStatus() : "SCHEDULED");

        // Try to fetch names from other services
        // If a service is down, we still save — just with null name
        if (dto.getPatientId() != null) {
            String patientName = externalServiceClient.getPatientName(dto.getPatientId());
            appointment.setPatientName(patientName != null ? patientName : dto.getPatientName());
        }

        if (dto.getDoctorId() != null) {
            String doctorName = externalServiceClient.getDoctorName(dto.getDoctorId());
            appointment.setDoctorName(doctorName != null ? doctorName : dto.getDoctorName());
        }

        if (dto.getDepartmentId() != null) {
            String departmentName = externalServiceClient.getDepartmentName(dto.getDepartmentId());
            appointment.setDepartmentName(departmentName != null ? departmentName : dto.getDepartmentName());
        }

        Appointment saved = appointmentRepository.save(appointment);
        return convertToDTO(saved);
    }

    // =============================================
    // GET ALL appointments
    // =============================================
    public List<AppointmentDTO> getAllAppointments() {
        return appointmentRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // =============================================
    // GET ONE appointment by ID
    // =============================================
    public AppointmentDTO getAppointmentById(Long id) {
        Optional<Appointment> optional = appointmentRepository.findById(id);
        if (optional.isEmpty()) {
            throw new RuntimeException("Appointment not found with ID: " + id);
        }
        return convertToDTO(optional.get());
    }

    // =============================================
    // UPDATE an appointment
    // =============================================
    public AppointmentDTO updateAppointment(Long id, AppointmentDTO dto) {
        Optional<Appointment> optional = appointmentRepository.findById(id);
        if (optional.isEmpty()) {
            throw new RuntimeException("Appointment not found with ID: " + id);
        }

        Appointment appointment = optional.get();

        // Update fields if provided
        if (dto.getPatientId() != null) {
            appointment.setPatientId(dto.getPatientId());
            String patientName = externalServiceClient.getPatientName(dto.getPatientId());
            appointment.setPatientName(patientName != null ? patientName : dto.getPatientName());
        }

        if (dto.getDoctorId() != null) {
            appointment.setDoctorId(dto.getDoctorId());
            String doctorName = externalServiceClient.getDoctorName(dto.getDoctorId());
            appointment.setDoctorName(doctorName != null ? doctorName : dto.getDoctorName());
        }

        if (dto.getDepartmentId() != null) {
            appointment.setDepartmentId(dto.getDepartmentId());
            String departmentName = externalServiceClient.getDepartmentName(dto.getDepartmentId());
            appointment.setDepartmentName(departmentName != null ? departmentName : dto.getDepartmentName());
        }

        if (dto.getAppointmentDate() != null)  appointment.setAppointmentDate(dto.getAppointmentDate());
        if (dto.getAppointmentTime() != null)  appointment.setAppointmentTime(dto.getAppointmentTime());
        if (dto.getStatus() != null)           appointment.setStatus(dto.getStatus());
        if (dto.getReason() != null)           appointment.setReason(dto.getReason());
        if (dto.getNotes() != null)            appointment.setNotes(dto.getNotes());

        Appointment updated = appointmentRepository.save(appointment);
        return convertToDTO(updated);
    }

    // =============================================
    // DELETE an appointment
    // =============================================
    public void deleteAppointment(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new RuntimeException("Appointment not found with ID: " + id);
        }
        appointmentRepository.deleteById(id);
    }

    // =============================================
    // GET appointments by Patient ID
    // =============================================
    public List<AppointmentDTO> getAppointmentsByPatient(Long patientId) {
        return appointmentRepository.findByPatientId(patientId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // =============================================
    // GET appointments by Doctor ID
    // =============================================
    public List<AppointmentDTO> getAppointmentsByDoctor(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // =============================================
    // GET appointments by Department ID
    // =============================================
    public List<AppointmentDTO> getAppointmentsByDepartment(Long departmentId) {
        return appointmentRepository.findByDepartmentId(departmentId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // =============================================
    // GET appointments by Status
    // =============================================
    public List<AppointmentDTO> getAppointmentsByStatus(String status) {
        return appointmentRepository.findByStatus(status.toUpperCase())
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // =============================================
    // GET appointments by Date
    // =============================================
    public List<AppointmentDTO> getAppointmentsByDate(LocalDate date) {
        return appointmentRepository.findByAppointmentDate(date)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // =============================================
    // HELPER: Convert Entity → DTO
    // =============================================
    private AppointmentDTO convertToDTO(Appointment a) {
        AppointmentDTO dto = new AppointmentDTO();
        dto.setId(a.getId());
        dto.setPatientId(a.getPatientId());
        dto.setPatientName(a.getPatientName());
        dto.setDoctorId(a.getDoctorId());
        dto.setDoctorName(a.getDoctorName());
        dto.setDepartmentId(a.getDepartmentId());
        dto.setDepartmentName(a.getDepartmentName());
        dto.setAppointmentDate(a.getAppointmentDate());
        dto.setAppointmentTime(a.getAppointmentTime());
        dto.setStatus(a.getStatus());
        dto.setReason(a.getReason());
        dto.setNotes(a.getNotes());
        dto.setCreatedAt(a.getCreatedAt());
        dto.setUpdatedAt(a.getUpdatedAt());
        return dto;
    }
}
