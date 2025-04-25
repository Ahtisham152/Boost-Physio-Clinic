
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ClinicManagementSystem {

    private List<Physiotherapist> physiotherapists;
    private List<Patient> patients;
    private Map<String, Physiotherapist> physiotherapistsByName;
    private Map<String, List<Physiotherapist>> physiotherapistsByExpertise;

    public ClinicManagementSystem() {
        physiotherapists = new ArrayList<>();
        patients = new ArrayList<>();
        physiotherapistsByName = new HashMap<>();
        physiotherapistsByExpertise = new HashMap<>();
    }

    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    public void removePatient(Patient patient) {
        patients.remove(patient);
    }

    public Patient findPatientById(String id) {
        for (Patient patient : patients) {
            if (patient.getId().equals(id)) {
                return patient;
            }
        }
        return null;
    }

    public void addPhysiotherapist(Physiotherapist physiotherapist) {
        physiotherapists.add(physiotherapist);
        physiotherapistsByName.put(physiotherapist.getFullName().toLowerCase(), physiotherapist);

        for (String expertise : physiotherapist.getExpertiseAreas()) {
            List<Physiotherapist> physiosWithExpertise = physiotherapistsByExpertise.getOrDefault(
                    expertise.toLowerCase(), new ArrayList<>());
            physiosWithExpertise.add(physiotherapist);
            physiotherapistsByExpertise.put(expertise.toLowerCase(), physiosWithExpertise);
        }
    }

    public List<Treatment> findTreatmentsByExpertise(String expertiseArea) {
        List<Treatment> availableTreatments = new ArrayList<>();
        List<Physiotherapist> physioList = physiotherapistsByExpertise.getOrDefault(
                expertiseArea.toLowerCase(), new ArrayList<>());

        for (Physiotherapist physio : physioList) {
            for (Treatment treatment : physio.getAvailableTreatments()) {
                if (treatment.getExpertiseArea().equalsIgnoreCase(expertiseArea)) {
                    availableTreatments.add(treatment);
                }
            }
        }

        return availableTreatments;
    }

    public List<Treatment> findTreatmentsByPhysiotherapist(String physiotherapistName) {
        Physiotherapist physio = physiotherapistsByName.get(physiotherapistName.toLowerCase());
        if (physio != null) {
            return physio.getAvailableTreatments();
        }
        return new ArrayList<>();
    }

    public Appointment bookAppointment(Patient patient, Treatment treatment) {
        if (!treatment.isAvailable()) {
            return null;
        }

        Appointment appointment = new Appointment(patient, treatment);
        patient.addAppointment(appointment);
        treatment.bookAppointment(appointment);
        return appointment;
    }

    public void generateReport() {
        System.out.println("=== CLINIC END-OF-TERM REPORT ===");
        System.out.println("Appointment details for each physiotherapist:");

        Map<Physiotherapist, Integer> attendedCountMap = new HashMap<>();

        for (Physiotherapist physio : physiotherapists) {
            System.out.println("\nPhysiotherapist: " + physio.getFullName());
            int attended = 0;

            for (Treatment treatment : physio.getTreatments()) {
                Appointment appointment = treatment.getAppointment();
                if (appointment != null) {
                    System.out.println("  - Treatment: " + treatment.getName());
                    System.out.println("    Time: " + formatDateTime(treatment.getDateTime()));
                    System.out.println("    Patient: " + appointment.getPatient().getFullName());
                    System.out.println("    Status: " + appointment.getStatus());

                    if (appointment.getStatus() == AppointmentStatus.ATTENDED) {
                        attended++;
                    }
                }
            }

            attendedCountMap.put(physio, attended);
        }

        System.out.println("\nPhysiotherapists by number of attended appointments (descending):");
        attendedCountMap.entrySet().stream()
                .sorted(Map.Entry.<Physiotherapist, Integer>comparingByValue().reversed())
                .forEach(entry -> {
                    System.out.println(entry.getKey().getFullName() + ": " + entry.getValue() + " attended appointments");
                });
    }

    private String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE d MMMM yyyy, HH:mm");
        return dateTime.format(formatter);
    }
}
