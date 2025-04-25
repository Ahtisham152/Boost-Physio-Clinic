
public class Appointment {

    private Patient patient;
    private Treatment treatment;
    private AppointmentStatus status;

    public Appointment(Patient patient, Treatment treatment) {
        this.patient = patient;
        this.treatment = treatment;
        this.status = AppointmentStatus.BOOKED;
    }

    public void cancel() {
        this.status = AppointmentStatus.CANCELLED;
    }

    public void checkIn() {
        this.status = AppointmentStatus.ATTENDED;
    }

    // Getters
    public Patient getPatient() {
        return patient;
    }

    public Treatment getTreatment() {
        return treatment;
    }

    public AppointmentStatus getStatus() {
        return status;
    }
}
