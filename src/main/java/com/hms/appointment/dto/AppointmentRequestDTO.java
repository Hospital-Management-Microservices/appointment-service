package com.hms.appointment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * AppointmentRequestDTO — What the CLIENT sends when creating/updating an appointment.
 *
 * ✅ User only provides IDs — names are auto-fetched from Patient/Doctor services.
 * ✅ LocalDate and LocalTime appear as simple strings in Swagger and API responses.
 * ✅ No id, no timestamps — those are auto-managed by the server.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request body for creating or updating an appointment")
public class AppointmentRequestDTO {

    @Schema(description = "ID of the patient (from Patient Service)", example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private Long patientId;

    @Schema(description = "ID of the doctor (from Doctor Service)", example = "2",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private Long doctorId;

    @Schema(description = "ID of the department (optional)", example = "3")
    private Long departmentId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Schema(description = "Appointment date in yyyy-MM-dd format", example = "2025-06-15",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate appointmentDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    @Schema(description = "Appointment time in HH:mm:ss format", example = "10:30:00",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalTime appointmentTime;

    @Schema(description = "Reason for the appointment", example = "Routine checkup")
    private String reason;

    @Schema(description = "Additional notes (optional)", example = "Patient has a known allergy to penicillin")
    private String notes;

    @Schema(description = "Appointment status. Defaults to SCHEDULED if not provided.",
            example = "SCHEDULED",
            allowableValues = {"SCHEDULED", "COMPLETED", "CANCELLED", "NO_SHOW"})
    private String status;
}
