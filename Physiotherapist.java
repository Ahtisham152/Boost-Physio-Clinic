
import java.util.*;

public class Physiotherapist extends Person {

    private Set<String> expertiseAreas;
    private List<Treatment> treatments;

    public Physiotherapist(String id, String fullName, String address, String telephone) {
        super(id, fullName, address, telephone);
        expertiseAreas = new HashSet<>();
        treatments = new ArrayList<>();
    }

    public void addExpertiseArea(String area) {
        expertiseAreas.add(area);
    }

    public Set<String> getExpertiseAreas() {
        return Collections.unmodifiableSet(expertiseAreas);
    }

    public void addTreatment(Treatment treatment) {
        treatments.add(treatment);
    }

    public List<Treatment> getTreatments() {
        return Collections.unmodifiableList(treatments);
    }

    public List<Treatment> getAvailableTreatments() {
        List<Treatment> availableTreatments = new ArrayList<>();
        for (Treatment treatment : treatments) {
            if (treatment.isAvailable()) {
                availableTreatments.add(treatment);
            }
        }
        return availableTreatments;
    }
}
