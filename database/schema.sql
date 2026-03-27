-- =============================================
-- HMS Appointment Service - Database Schema
-- Run this on your Neon.tech SQL Editor
-- =============================================

-- NOTE: You do NOT need to run this manually.
-- Spring Boot with "spring.jpa.hibernate.ddl-auto=update"
-- will automatically create the table when the app starts.
-- This file is just for your reference / documentation.

-- =============================================
-- appointments table
-- =============================================
CREATE TABLE IF NOT EXISTS appointments (
    id               BIGSERIAL PRIMARY KEY,

    -- Patient info (from Patient Service)
    patient_id       BIGINT NOT NULL,
    patient_name     VARCHAR(100),

    -- Doctor info (from Doctor Service)
    doctor_id        BIGINT NOT NULL,
    doctor_name      VARCHAR(100),

    -- Department info (from Department Service)
    department_id    BIGINT,
    department_name  VARCHAR(100),

    -- Appointment details
    appointment_date DATE NOT NULL,
    appointment_time TIME NOT NULL,
    status           VARCHAR(20) DEFAULT 'SCHEDULED',  -- SCHEDULED | COMPLETED | CANCELLED | NO_SHOW
    reason           TEXT,
    notes            TEXT,

    -- Timestamps
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =============================================
-- Sample data (optional - for testing)
-- =============================================
INSERT INTO appointments (patient_id, patient_name, doctor_id, doctor_name, department_id, department_name, appointment_date, appointment_time, status, reason)
VALUES
    (1, 'John Silva', 1, 'Dr. Nimal Perera', 1, 'Cardiology', '2025-04-10', '09:00:00', 'SCHEDULED', 'Regular checkup'),
    (2, 'Kamal Fernando', 2, 'Dr. Saman Jayawardena', 2, 'Neurology', '2025-04-11', '10:30:00', 'SCHEDULED', 'Headache consultation'),
    (3, 'Sunil Wickrama', 1, 'Dr. Nimal Perera', 1, 'Cardiology', '2025-04-05', '14:00:00', 'COMPLETED', 'Follow-up visit'),
    (1, 'John Silva', 3, 'Dr. Priya Dissanayake', 3, 'Pediatrics', '2025-04-08', '11:00:00', 'CANCELLED', 'Vaccination');

-- =============================================
-- Useful queries for testing
-- =============================================

-- Get all appointments
-- SELECT * FROM appointments;

-- Get appointments for a patient
-- SELECT * FROM appointments WHERE patient_id = 1;

-- Get appointments for a doctor
-- SELECT * FROM appointments WHERE doctor_id = 1;

-- Get scheduled appointments
-- SELECT * FROM appointments WHERE status = 'SCHEDULED';

-- Get appointments on a date
-- SELECT * FROM appointments WHERE appointment_date = '2025-04-10';

-- Update appointment status
-- UPDATE appointments SET status = 'COMPLETED' WHERE id = 1;

-- Delete appointment
-- DELETE FROM appointments WHERE id = 4;
