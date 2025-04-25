
import java.time.LocalDateTime;
import java.util.List;

public class SimpleTestRunner {

    private static int passedTests = 0;
    private static int failedTests = 0;

    public static void main(String[] args) {
        System.out.println("Running BPC System Tests...");
        testPatientManagement();

        testPhysiotherapistManagement();

        testTreatmentSearch();

        testAppointmentBooking();

        testAppointmentStatusChanges();

        System.out.println("\n=== Test Summary ===");
        System.out.println("Passed: " + passedTests);
        System.out.println("Failed: " + failedTests);
        System.out.println("Total: " + (passedTests + failedTests));
    }

    private static void testPatientManagement() {
        System.out.println("\nTesting Patient Management:");

        ClinicManagementSystem clinic = new ClinicManagementSystem();
        Patient patient = new Patient("PT001", "Test Patient", "123 Test St", "555-1111");

        clinic.addPatient(patient);
        assertEqual(patient, clinic.findPatientById("PT001"), "Should find patient after adding");

        clinic.removePatient(patient);
        assertNull(clinic.findPatientById("PT001"), "Should return null after removing patient");
    }

    private static void testPhysiotherapistManagement() {
        System.out.println("\nTesting Physiotherapist Management:");

        Physiotherapist physio = new Physiotherapist("P001", "Test Physio", "123 Physio St", "555-2222");

        physio.addExpertiseArea("Physiotherapy");
        assertTrue(physio.getExpertiseAreas().contains("Physiotherapy"),
                "Should contain added expertise area");

        LocalDateTime now = LocalDateTime.now();
        Treatment treatment = new Treatment("Test Treatment", "Physiotherapy", physio, now, 60);
        physio.addTreatment(treatment);
        assertTrue(physio.getTreatments().contains(treatment),
                "Should contain added treatment");
    }

    private static void testTreatmentSearch() {
        System.out.println("\nTesting Treatment Search:");

        ClinicManagementSystem clinic = new ClinicManagementSystem();

        Physiotherapist physio1 = new Physiotherapist("P001", "John Smith", "123 Main St", "555-1234");
        physio1.addExpertiseArea("Physiotherapy");

        Physiotherapist physio2 = new Physiotherapist("P002", "Sarah Johnson", "456 Oak Ave", "555-5678");
        physio2.addExpertiseArea("Osteopathy");
        physio2.addExpertiseArea("Physiotherapy");

        clinic.addPhysiotherapist(physio1);
        clinic.addPhysiotherapist(physio2);

        LocalDateTime dateTime = LocalDateTime.now();
        Treatment treatment1 = new Treatment("Neural mobilisation", "Physiotherapy", physio1, dateTime, 60);
        physio1.addTreatment(treatment1);

        Treatment treatment2 = new Treatment("Massage", "Physiotherapy", physio2, dateTime, 30);
        physio2.addTreatment(treatment2);

        Treatment treatment3 = new Treatment("Joint manipulation", "Osteopathy", physio2,
                dateTime.plusHours(2), 45);
        physio2.addTreatment(treatment3);

        List<Treatment> physioTreatments = clinic.findTreatmentsByExpertise("Physiotherapy");
        assertEqual(2, physioTreatments.size(), "Should find 2 Physiotherapy treatments");

        List<Treatment> physioTreatments2 = clinic.findTreatmentsByPhysiotherapist("Sarah Johnson");
        assertEqual(2, physioTreatments2.size(), "Should find 2 treatments for Sarah Johnson");
    }

    private static void testAppointmentBooking() {
        System.out.println("\nTesting Appointment Booking:");

        ClinicManagementSystem clinic = new ClinicManagementSystem();
        Patient patient = new Patient("PT001", "Test Patient", "123 Test St", "555-1111");
        clinic.addPatient(patient);

        Physiotherapist physio = new Physiotherapist("P001", "John Smith", "123 Main St", "555-1234");
        physio.addExpertiseArea("Physiotherapy");
        clinic.addPhysiotherapist(physio);

        Treatment treatment = new Treatment("Neural mobilisation", "Physiotherapy", physio,
                LocalDateTime.now(), 60);
        physio.addTreatment(treatment);

        Appointment appointment = clinic.bookAppointment(patient, treatment);
        assertNotNull(appointment, "Should create appointment");
        assertEqual(patient, appointment.getPatient(), "Appointment should have correct patient");
        assertEqual(treatment, appointment.getTreatment(), "Appointment should have correct treatment");
        assertEqual(AppointmentStatus.BOOKED, appointment.getStatus(), "Appointment should be BOOKED");

        Patient patient2 = new Patient("PT002", "Another Patient", "456 Test St", "555-2222");
        clinic.addPatient(patient2);
        Appointment appointment2 = clinic.bookAppointment(patient2, treatment);
        assertNull(appointment2, "Should not allow double booking");
    }

    private static void testAppointmentStatusChanges() {
        System.out.println("\nTesting Appointment Status Changes:");

        Patient patient = new Patient("PT001", "Test Patient", "123 Test St", "555-1111");
        Physiotherapist physio = new Physiotherapist("P001", "John Smith", "123 Main St", "555-1234");
        Treatment treatment = new Treatment("Test Treatment", "Physiotherapy", physio,
                LocalDateTime.now(), 60);
        Appointment appointment = new Appointment(patient, treatment);

        assertEqual(AppointmentStatus.BOOKED, appointment.getStatus(), "Initial status should be BOOKED");

        appointment.cancel();
        assertEqual(AppointmentStatus.CANCELLED, appointment.getStatus(), "Status should be CANCELLED after cancel");

        Appointment appointment2 = new Appointment(patient, treatment);
        appointment2.checkIn();
        assertEqual(AppointmentStatus.ATTENDED, appointment2.getStatus(), "Status should be ATTENDED after check-in");
    }

    private static <T> void assertEqual(T expected, T actual, String message) {
        if ((expected == null && actual == null)
                || (expected != null && expected.equals(actual))) {
            System.out.println("✓ PASS: " + message);
            passedTests++;
        } else {
            System.out.println("✗ FAIL: " + message);
            System.out.println("  Expected: " + expected);
            System.out.println("  Actual: " + actual);
            failedTests++;
        }
    }

    private static void assertTrue(boolean condition, String message) {
        if (condition) {
            System.out.println("✓ PASS: " + message);
            passedTests++;
        } else {
            System.out.println("✗ FAIL: " + message);
            failedTests++;
        }
    }

    private static void assertFalse(boolean condition, String message) {
        if (!condition) {
            System.out.println("✓ PASS: " + message);
            passedTests++;
        } else {
            System.out.println("✗ FAIL: " + message);
            failedTests++;
        }
    }

    private static void assertNull(Object obj, String message) {
        if (obj == null) {
            System.out.println("✓ PASS: " + message);
            passedTests++;
        } else {
            System.out.println("✗ FAIL: " + message);
            System.out.println("  Expected null but got: " + obj);
            failedTests++;
        }
    }

    private static void assertNotNull(Object obj, String message) {
        if (obj != null) {
            System.out.println("✓ PASS: " + message);
            passedTests++;
        } else {
            System.out.println("✗ FAIL: " + message);
            System.out.println("  Expected non-null value");
            failedTests++;
        }
    }
}
