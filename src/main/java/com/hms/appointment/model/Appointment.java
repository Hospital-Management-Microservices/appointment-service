package com.hms.appointment.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "appointments")
@Data                   // Lombok: generates getters, setters, toString, equals, hashCode
@NoArgsConstructor      // Lombok: generates empty constructor
@AllArgsConstructor     // Lombok: generates constructor with all fields
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ---- Patient Info (fetched from Patient Service) ----
    @Column(name = "patient_id", nullable = false)
    private Long patientId;

    @Column(name = "patient_name", length = 100)
    private String patientName;

    // ---- Doctor Info (fetched from Doctor Service) ----
    @Column(name = "doctor_id", nullable = false)
    private Long doctorId;

    @Column(name = "doctor_name", length = 100)
    private String doctorName;

    // ---- Department Info (fetched from Department Service) ----
    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "department_name", length = 100)
    private String departmentName;

    // ---- Appointment Details ----
    @Column(name = "appointment_date", nullable = false)
    private LocalDate appointmentDate;

    @Column(name = "appointment_time", nullable = false)
    private LocalTime appointmentTime;

    // Status values: SCHEDULED, COMPLETED, CANCELLED, NO_SHOW
    @Column(name = "status", length = 20)
    private String status = "SCHEDULED";

    @Column(name = "reason", columnDefinition = "TEXT")
    private String reason;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    // ---- Timestamps (auto-managed) ----
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
