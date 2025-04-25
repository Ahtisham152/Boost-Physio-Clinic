
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PhysiotherapyClinic {

    public static void main(String[] args) {
        ClinicManagementSystem clinic = new ClinicManagementSystem();

        initializeDemoData(clinic);

        runUserInterface(clinic);
    }

    private static void initializeDemoData(ClinicManagementSystem clinic) {
        Physiotherapist physio1 = new Physiotherapist("P001", "John Smith", "123 Main St", "555-1234");
        physio1.addExpertiseArea("Physiotherapy");
        physio1.addExpertiseArea("Rehabilitation");

        Physiotherapist physio2 = new Physiotherapist("P002", "Sarah Johnson", "456 Oak Ave", "555-5678");
        physio2.addExpertiseArea("Osteopathy");
        physio2.addExpertiseArea("Physiotherapy");

        Physiotherapist physio3 = new Physiotherapist("P003", "Michael Brown", "789 Pine Rd", "555-9012");
        physio3.addExpertiseArea("Rehabilitation");

        Physiotherapist physio4 = new Physiotherapist("P004", "Lisa Davis", "321 Elm St", "555-3456");
        physio4.addExpertiseArea("Physiotherapy");
        physio4.addExpertiseArea("Osteopathy");

        clinic.addPhysiotherapist(physio1);
        clinic.addPhysiotherapist(physio2);
        clinic.addPhysiotherapist(physio3);
        clinic.addPhysiotherapist(physio4);

        for (int i = 1; i <= 15; i++) {
            String id = String.format("PT%03d", i);
            String name = "Patient " + i;
            String address = i + " Patient Street";
            String phone = "555-" + String.format("%04d", i * 1000);

            clinic.addPatient(new Patient(id, name, address, phone));
        }

        LocalDate startDate = LocalDate.now().plusMonths(1).withDayOfMonth(1);

        addTreatmentsForPhysio(physio1, "Neural mobilisation", "Physiotherapy", startDate, 60);
        addTreatmentsForPhysio(physio1, "Pool rehabilitation", "Rehabilitation", startDate, 45);
        addTreatmentsForPhysio(physio2, "Massage", "Physiotherapy", startDate, 30);
        addTreatmentsForPhysio(physio2, "Joint manipulation", "Osteopathy", startDate, 45);

        addTreatmentsForPhysio(physio3, "Pool rehabilitation", "Rehabilitation", startDate, 60);
        addTreatmentsForPhysio(physio3, "Exercise therapy", "Rehabilitation", startDate, 45);

        addTreatmentsForPhysio(physio4, "Acupuncture", "Physiotherapy", startDate, 45);
        addTreatmentsForPhysio(physio4, "Mobilisation of the spine", "Osteopathy", startDate, 30);
    }

    private static void addTreatmentsForPhysio(Physiotherapist physio, String treatmentName,
            String expertiseArea, LocalDate startDate, int duration) {
        
        for (int week = 0; week < 4; week++) {
            for (int day = 0; day < 5; day++) { 
                LocalDate currentDate = startDate.plusDays(week * 7 + day);

                
                LocalDateTime morningTime = LocalDateTime.of(currentDate, LocalTime.of(10, 0));
                Treatment morningTreatment = new Treatment(treatmentName, expertiseArea, physio, morningTime, duration);
                physio.addTreatment(morningTreatment);

                
                LocalDateTime afternoonTime = LocalDateTime.of(currentDate, LocalTime.of(14, 0));
                Treatment afternoonTreatment = new Treatment(treatmentName, expertiseArea, physio, afternoonTime, duration);
                physio.addTreatment(afternoonTreatment);
            }
        }
    }

    private static void runUserInterface(ClinicManagementSystem clinic) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("Welcome to the Physiotherapy Clinic Management System");

        while (running) {
            System.out.println("\nPlease select an option:");
            System.out.println("1. Add a new patient");
            System.out.println("2. Remove a patient");
            System.out.println("3. Search treatments by expertise area");
            System.out.println("4. Search treatments by physiotherapist name");
            System.out.println("5. Book an appointment");
            System.out.println("6. Cancel an appointment");
            System.out.println("7. Check in a patient for appointment");
            System.out.println("8. Generate end-of-term report");
            System.out.println("9. Exit");

            System.out.print("\nEnter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  

            switch (choice) {
                case 1:
                    addPatient(scanner, clinic);
                    break;
                case 2:
                    removePatient(scanner, clinic);
                    break;
                case 3:
                    searchByExpertise(scanner, clinic);
                    break;
                case 4:
                    searchByPhysiotherapist(scanner, clinic);
                    break;
                case 5:
                    bookAppointment(scanner, clinic);
                    break;
                case 6:
                    cancelAppointment(scanner, clinic);
                    break;
                case 7:
                    checkInPatient(scanner, clinic);
                    break;
                case 8:
                    clinic.generateReport();
                    break;
                case 9:
                    running = false;
                    System.out.println("Thank you for using the system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }

    private static void addPatient(Scanner scanner, ClinicManagementSystem clinic) {
        System.out.println("\n=== Add New Patient ===");

        System.out.print("Enter ID: ");
        String id = scanner.nextLine();

        System.out.print("Enter full name: ");
        String fullName = scanner.nextLine();

        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        System.out.print("Enter telephone: ");
        String telephone = scanner.nextLine();

        Patient patient = new Patient(id, fullName, address, telephone);
        clinic.addPatient(patient);

        System.out.println("Patient added successfully.");
    }

    private static void removePatient(Scanner scanner, ClinicManagementSystem clinic) {
        System.out.println("\n=== Remove Patient ===");

        System.out.print("Enter patient ID: ");
        String id = scanner.nextLine();

        Patient patient = clinic.findPatientById(id);
        if (patient != null) {
            clinic.removePatient(patient);
            System.out.println("Patient removed successfully.");
        } else {
            System.out.println("Patient not found.");
        }
    }

    private static void searchByExpertise(Scanner scanner, ClinicManagementSystem clinic) {
        System.out.println("\n=== Search Treatments by Expertise ===");

        System.out.print("Enter expertise area (Physiotherapy, Osteopathy, Rehabilitation): ");
        String expertise = scanner.nextLine();

        List<Treatment> treatments = clinic.findTreatmentsByExpertise(expertise);
        displayTreatments(treatments);
    }

    private static void searchByPhysiotherapist(Scanner scanner, ClinicManagementSystem clinic) {
        System.out.println("\n=== Search Treatments by Physiotherapist ===");

        System.out.print("Enter physiotherapist name: ");
        String name = scanner.nextLine();

        List<Treatment> treatments = clinic.findTreatmentsByPhysiotherapist(name);
        displayTreatments(treatments);
    }

    private static void displayTreatments(List<Treatment> treatments) {
        if (treatments.isEmpty()) {
            System.out.println("No available treatments found.");
            return;
        }

        System.out.println("Available treatments:");
        for (int i = 0; i < treatments.size(); i++) {
            Treatment t = treatments.get(i);
            System.out.printf("%d. %s with %s\n", i + 1, t.getName(), t.getPhysiotherapist().getFullName());
            System.out.printf("   Time: %s (Duration: %d minutes)\n",
                    t.getDateTime().toString(), t.getDurationMinutes());
        }
    }

    private static void bookAppointment(Scanner scanner, ClinicManagementSystem clinic) {
        System.out.println("\n=== Book an Appointment ===");

        System.out.print("Enter patient ID: ");
        String patientId = scanner.nextLine();

        Patient patient = clinic.findPatientById(patientId);
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        System.out.print("Search by (1) Expertise or (2) Physiotherapist? ");
        int searchChoice = scanner.nextInt();
        scanner.nextLine();

        List<Treatment> treatments;
        if (searchChoice == 1) {
            System.out.print("Enter expertise area: ");
            String expertise = scanner.nextLine();
            treatments = clinic.findTreatmentsByExpertise(expertise);
        } else {
            System.out.print("Enter physiotherapist name: ");
            String name = scanner.nextLine();
            treatments = clinic.findTreatmentsByPhysiotherapist(name);
        }

        displayTreatments(treatments);

        if (treatments.isEmpty()) {
            return;
        }

        System.out.print("Enter treatment number to book: ");
        int treatmentIndex = scanner.nextInt() - 1;
        scanner.nextLine();

        if (treatmentIndex >= 0 && treatmentIndex < treatments.size()) {
            Treatment selectedTreatment = treatments.get(treatmentIndex);
            Appointment appointment = clinic.bookAppointment(patient, selectedTreatment);

            if (appointment != null) {
                System.out.println("Appointment booked successfully!");
            } else {
                System.out.println("Failed to book appointment. The treatment may no longer be available.");
            }
        } else {
            System.out.println("Invalid treatment number.");
        }
    }

    private static void cancelAppointment(Scanner scanner, ClinicManagementSystem clinic) {
        System.out.println("\n=== Cancel an Appointment ===");

        System.out.print("Enter patient ID: ");
        String patientId = scanner.nextLine();

        Patient patient = clinic.findPatientById(patientId);
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        List<Appointment> appointments = patient.getAppointments();
        if (appointments.isEmpty()) {
            System.out.println("No appointments found for this patient.");
            return;
        }

        System.out.println("Current appointments:");
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appt = appointments.get(i);
            if (appt.getStatus() != AppointmentStatus.CANCELLED) {
                Treatment t = appt.getTreatment();
                System.out.printf("%d. %s with %s on %s\n", i + 1,
                        t.getName(), t.getPhysiotherapist().getFullName(),
                        t.getDateTime().toString());
            }
        }

        System.out.print("Enter appointment number to cancel: ");
        int appointmentIndex = scanner.nextInt() - 1;
        scanner.nextLine();

        if (appointmentIndex >= 0 && appointmentIndex < appointments.size()) {
            patient.cancelAppointment(appointments.get(appointmentIndex));
            System.out.println("Appointment cancelled successfully.");
        } else {
            System.out.println("Invalid appointment number.");
        }
    }

    private static void checkInPatient(Scanner scanner, ClinicManagementSystem clinic) {
        System.out.println("\n=== Check In Patient ===");

        System.out.print("Enter patient ID: ");
        String patientId = scanner.nextLine();

        Patient patient = clinic.findPatientById(patientId);
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        List<Appointment> appointments = patient.getAppointments();
        List<Appointment> bookedAppointments = new ArrayList<>();

        for (Appointment appt : appointments) {
            if (appt.getStatus() == AppointmentStatus.BOOKED) {
                bookedAppointments.add(appt);
            }
        }

        if (bookedAppointments.isEmpty()) {
            System.out.println("No booked appointments found for this patient.");
            return;
        }

        System.out.println("Booked appointments:");
        for (int i = 0; i < bookedAppointments.size(); i++) {
            Appointment appt = bookedAppointments.get(i);
            Treatment t = appt.getTreatment();
            System.out.printf("%d. %s with %s on %s\n", i + 1,
                    t.getName(), t.getPhysiotherapist().getFullName(),
                    t.getDateTime().toString());
        }

        System.out.print("Enter appointment number to check in: ");
        int appointmentIndex = scanner.nextInt() - 1;
        scanner.nextLine();

        if (appointmentIndex >= 0 && appointmentIndex < bookedAppointments.size()) {
            bookedAppointments.get(appointmentIndex).checkIn();
            System.out.println("Patient checked in successfully.");
        } else {
            System.out.println("Invalid appointment number.");
        }
    }
}
