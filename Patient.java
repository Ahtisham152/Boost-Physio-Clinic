
import java.util.*;

public class Patient extends Person {

    private List<Appointment> appointments;

    public Patient(String id, String fullName, String address, String telephone) {
        super(id, fullName, address, telephone);
        appointments = new ArrayList<>();
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    public List<Appointment> getAppointments() {
        return Collections.unmodifiableList(appointments);
    }

    public void cancelAppointment(Appointment appointment) {
        appointment.cancel();
    }

    public void changeAppointment(Appointment oldAppointment, Treatment newTreatment) {
        oldAppointment.cancel();
        Appointment newAppointment = new Appointment(this, newTreatment);
        appointments.add(newAppointment);
        newTreatment.bookAppointment(newAppointment);
    }
}
