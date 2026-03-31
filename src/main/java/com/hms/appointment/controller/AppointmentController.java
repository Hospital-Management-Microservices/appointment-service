package com.hms.appointment.controller;

import com.hms.appointment.dto.AppointmentDTO;
import com.hms.appointment.dto.AppointmentRequestDTO;
import com.hms.appointment.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller = the entry point for all HTTP requests.
 * Every method here is mapped to a URL endpoint.
 *
 * Base URL: /appointments
 * Full URL (via gateway): http://localhost:8080/appointments
 * Direct URL: http://localhost:8083/appointments
 */
@RestController
@RequestMapping("/appointments")
@Tag(name = "Appointment Service", description = "APIs for managing hospital appointments")
@CrossOrigin(origins = "*") // Allow all origins (needed for gateway + Swagger)
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    // =============================================
    // POST /appointments
    // Create a new appointment
    // =============================================
    @PostMapping
    @Operation(summary = "Create a new appointment",
            description = "Books a new appointment. Provide patientId, doctorId, appointmentDate (yyyy-MM-dd), and appointmentTime (HH:mm:ss). Names are auto-fetched from other services.")
    public ResponseEntity<AppointmentDTO> createAppointment(@RequestBody AppointmentRequestDTO request) {
        AppointmentDTO created = appointmentService.createAppointment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // =============================================
    // GET /appointments
    // Get all appointments
    // =============================================
    @GetMapping
    @Operation(summary = "Get all appointments", description = "Returns a list of all appointments")
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }

    // =============================================
    // GET /appointments/{id}
    // Get one appointment by ID
    // =============================================
    @GetMapping("/{id}")
    @Operation(summary = "Get appointment by ID")
    public ResponseEntity<?> getAppointmentById(
            @Parameter(description = "Appointment ID") @PathVariable Long id) {
        try {
            return ResponseEntity.ok(appointmentService.getAppointmentById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(errorResponse(e.getMessage()));
        }
    }

    // =============================================
    // PUT /appointments/{id}
    // Update an existing appointment
    // =============================================
    @PutMapping("/{id}")
    @Operation(summary = "Update an appointment", description = "Update appointment fields. Only fields provided will be updated. Names are re-fetched automatically if IDs change.")
    public ResponseEntity<?> updateAppointment(
            @PathVariable Long id,
            @RequestBody AppointmentRequestDTO request) {
        try {
            return ResponseEntity.ok(appointmentService.updateAppointment(id, request));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(errorResponse(e.getMessage()));
        }
    }

    // =============================================
    // DELETE /appointments/{id}
    // Delete an appointment
    // =============================================
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an appointment")
    public ResponseEntity<?> deleteAppointment(@PathVariable Long id) {
        try {
            appointmentService.deleteAppointment(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Appointment with ID " + id + " deleted successfully.");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(errorResponse(e.getMessage()));
        }
    }

    // =============================================
    // GET /appointments/patient/{patientId}
    // Get all appointments for a specific patient
    // =============================================
    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get appointments by patient ID", description = "Returns all appointments for a given patient")
    public ResponseEntity<List<AppointmentDTO>> getByPatient(
            @Parameter(description = "Patient ID from Patient Service") @PathVariable Long patientId) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByPatient(patientId));
    }

    // =============================================
    // GET /appointments/doctor/{doctorId}
    // Get all appointments for a specific doctor
    // =============================================
    @GetMapping("/doctor/{doctorId}")
    @Operation(summary = "Get appointments by doctor ID", description = "Returns all appointments assigned to a given doctor")
    public ResponseEntity<List<AppointmentDTO>> getByDoctor(
            @Parameter(description = "Doctor ID from Doctor Service") @PathVariable Long doctorId) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByDoctor(doctorId));
    }

    // =============================================
    // GET /appointments/department/{departmentId}
    // Get appointments for a department
    // =============================================
    @GetMapping("/department/{departmentId}")
    @Operation(summary = "Get appointments by department ID")
    public ResponseEntity<List<AppointmentDTO>> getByDepartment(@PathVariable Long departmentId) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByDepartment(departmentId));
    }

    // =============================================
    // GET /appointments/status/{status}
    // Get appointments by status
    // =============================================
    @GetMapping("/status/{status}")
    @Operation(summary = "Get appointments by status", description = "Status values: SCHEDULED | COMPLETED | CANCELLED | NO_SHOW")
    public ResponseEntity<List<AppointmentDTO>> getByStatus(
            @Parameter(description = "Appointment status") @PathVariable String status) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByStatus(status));
    }

    // =============================================
    // GET /appointments/date/{date}
    // Get appointments on a specific date
    // =============================================
    @GetMapping("/date/{date}")
    @Operation(summary = "Get appointments by date", description = "Date format: YYYY-MM-DD  (e.g. 2025-04-15)")
    public ResponseEntity<List<AppointmentDTO>> getByDate(
            @Parameter(description = "Date in YYYY-MM-DD format") @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByDate(date));
    }

    // =============================================
    // Helper: build an error response map
    // =============================================
    private Map<String, String> errorResponse(String message) {
        Map<String, String> error = new HashMap<>();
        error.put("error", message);
        return error;
    }
}
