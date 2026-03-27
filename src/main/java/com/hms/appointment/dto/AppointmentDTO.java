package com.hms.appointment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * DTO = Data Transfer Object
 * This is what the API receives (in request body) and sends back (in response).
 * It's separate from the Entity (database model) for clean design.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDTO {

    private Long id;

    // Patient info
    private Long patientId;
    private String patientName;

    // Doctor info
    private Long doctorId;
    private String doctorName;

    // Department info
    private Long departmentId;
    private String departmentName;

    // Appointment details
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String status;
    private String reason;
    private String notes;

    // Timestamps (read-only, set automatically)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
