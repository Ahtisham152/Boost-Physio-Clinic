
import java.time.LocalDateTime;

public class Treatment {

    private String name;
    private String expertiseArea;
    private Physiotherapist physiotherapist;
    private LocalDateTime dateTime;
    private int durationMinutes;
    private Appointment appointment;

    public Treatment(String name, String expertiseArea, Physiotherapist physiotherapist,
            LocalDateTime dateTime, int durationMinutes) {
        this.name = name;
        this.expertiseArea = expertiseArea;
        this.physiotherapist = physiotherapist;
        this.dateTime = dateTime;
        this.durationMinutes = durationMinutes;
        this.appointment = null;
    }

    public boolean isAvailable() {
        return appointment == null || appointment.getStatus() == AppointmentStatus.CANCELLED;
    }

    public void bookAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getExpertiseArea() {
        return expertiseArea;
    }

    public Physiotherapist getPhysiotherapist() {
        return physiotherapist;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public Appointment getAppointment() {
        return appointment;
    }
}
