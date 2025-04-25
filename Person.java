
public abstract class Person {

    private String id;
    private String fullName;
    private String address;
    private String telephone;

    public Person(String id, String fullName, String address, String telephone) {
        this.id = id;
        this.fullName = fullName;
        this.address = address;
        this.telephone = telephone;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAddress() {
        return address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
