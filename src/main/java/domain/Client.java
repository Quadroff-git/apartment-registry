package domain;

public class Client {
    private Integer id;
    private String fullName;
    private String phoneNumber;


    public static final String PHONE_NUMBER_REGEX = "^\\+375\\s?(17|25|29|33|44)\\s?\\d{3}\\s?\\d{2}\\s?\\d{2}$";


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
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Full name cannot be null or empty");
        }
        this.fullName = fullName.trim();
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be null or empty");
        }

        String trimmedPhone = phoneNumber.trim();
        if (!trimmedPhone.matches(PHONE_NUMBER_REGEX)) {
            throw new IllegalArgumentException(
                    "Phone number must be in format: +375 (operator) XXX XX XX. " +
                            "Valid operators: 17, 25, 29, 33, 44"
            );
        }
        this.phoneNumber = trimmedPhone;
    }


    @Override
    public String toString() {
        return "ID: " + id +
                "\nИмя: " + fullName + " Номер телефона: " + phoneNumber;
    }
}
