package domain;

public class Client {
    private Integer id;
    private String fullName;
    private String phoneNumber;


    public Client(String fullName, String phoneNumber) {
        this.id = null;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
    }

    public Client(int id, String fullName, String phoneNumber) {
        this.id = id;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
    }


    public Integer getId() { return id; }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }


    public void setId(Integer id) { this.id = id; }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    @Override
    public String toString() {
        return "Name: " + fullName +
                "\nPhone number: " + phoneNumber;
    }
}
