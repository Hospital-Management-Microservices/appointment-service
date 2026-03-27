package com.hms.appointment.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * This class is responsible for calling other services:
 * - Patient Service  → to verify patient exists and get their name
 * - Doctor Service   → to verify doctor exists and get their name
 * - Department Service → to verify department exists and get its name
 *
 * If any service is down, we handle the error gracefully.
 */
@Component
public class ExternalServiceClient {

    @Autowired
    private RestTemplate restTemplate;

    // These URLs come from application.properties
    @Value("${patient.service.url}")
    private String patientServiceUrl;

    @Value("${doctor.service.url}")
    private String doctorServiceUrl;

    @Value("${department.service.url}")
    private String departmentServiceUrl;

    /**
     * Fetches patient name from Patient Service.
     * Returns null if patient not found or service is down.
     */
    public String getPatientName(Long patientId) {
        try {
            String url = patientServiceUrl + "/" + patientId;
            Map<?, ?> response = restTemplate.getForObject(url, Map.class);
            if (response != null) {
                // Patient service returns firstName and lastName
                String firstName = (String) response.get("firstName");
                String lastName  = (String) response.get("lastName");
                if (firstName != null && lastName != null) {
                    return firstName + " " + lastName;
                }
                // Fallback: try "name" field
                return (String) response.getOrDefault("name", null);
            }
        } catch (HttpClientErrorException.NotFound e) {
            System.out.println("⚠️  Patient with ID " + patientId + " not found in Patient Service.");
        } catch (Exception e) {
            System.out.println("⚠️  Could not reach Patient Service: " + e.getMessage());
        }
        return null;
    }

    /**
     * Fetches doctor name from Doctor Service.
     */
    public String getDoctorName(Long doctorId) {
        try {
            String url = doctorServiceUrl + "/" + doctorId;
            Map<?, ?> response = restTemplate.getForObject(url, Map.class);
            if (response != null) {
                String firstName = (String) response.get("firstName");
                String lastName  = (String) response.get("lastName");
                if (firstName != null && lastName != null) {
                    return "Dr. " + firstName + " " + lastName;
                }
                return (String) response.getOrDefault("name", null);
            }
        } catch (HttpClientErrorException.NotFound e) {
            System.out.println("⚠️  Doctor with ID " + doctorId + " not found in Doctor Service.");
        } catch (Exception e) {
            System.out.println("⚠️  Could not reach Doctor Service: " + e.getMessage());
        }
        return null;
    }

    /**
     * Fetches department name from Department Service.
     */
    public String getDepartmentName(Long departmentId) {
        try {
            String url = departmentServiceUrl + "/" + departmentId;
            Map<?, ?> response = restTemplate.getForObject(url, Map.class);
            if (response != null) {
                return (String) response.getOrDefault("name", null);
            }
        } catch (HttpClientErrorException.NotFound e) {
            System.out.println("⚠️  Department with ID " + departmentId + " not found.");
        } catch (Exception e) {
            System.out.println("⚠️  Could not reach Department Service: " + e.getMessage());
        }
        return null;
    }
}
