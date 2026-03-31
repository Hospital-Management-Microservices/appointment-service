package com.hms.appointment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * AppointmentDTO — What the SERVER sends back to the client (API response).
 *
 * ✅ Contains full data including auto-fetched names.
 * ✅ LocalDate formatted as "yyyy-MM-dd"
 * ✅ LocalTime formatted as "HH:mm:ss"
 * ✅ LocalDateTime formatted as "yyyy-MM-dd HH:mm:ss"
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Appointment response with full details including resolved names")
public class AppointmentDTO {

    @Schema(description = "Auto-generated appointment ID", example = "101")
    private Long id;

    // Patient info
    @Schema(description = "Patient ID", example = "1")
    private Long patientId;

    @Schema(description = "Patient full name (auto-fetched from Patient Service)", example = "John Doe")
    private String patientName;

    // Doctor info
    @Schema(description = "Doctor ID", example = "2")
    private Long doctorId;

    @Schema(description = "Doctor full name (auto-fetched from Doctor Service)", example = "Dr. Jane Smith")
    private String doctorName;

    // Department info
    @Schema(description = "Department ID", example = "3")
    private Long departmentId;

    @Schema(description = "Department name", example = "Cardiology")
    private String departmentName;

    // Appointment details
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Schema(description = "Appointment date", example = "2025-06-15")
    private LocalDate appointmentDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    @Schema(description = "Appointment time", example = "10:30:00")
    private LocalTime appointmentTime;

    @Schema(description = "Appointment status", example = "SCHEDULED",
            allowableValues = {"SCHEDULED", "COMPLETED", "CANCELLED", "NO_SHOW"})
    private String status;

    @Schema(description = "Reason for the appointment", example = "Routine checkup")
    private String reason;

    @Schema(description = "Additional notes", example = "Patient has a known allergy to penicillin")
    private String notes;

    // Timestamps (read-only, set automatically by the database)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Record creation timestamp", example = "2025-06-10 09:00:00")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Record last update timestamp", example = "2025-06-10 09:00:00")
    private LocalDateTime updatedAt;
}
