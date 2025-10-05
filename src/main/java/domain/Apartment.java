package domain;

public class Apartment {
    private int id;
    private String address;
    private int roomCount;
    private int area;
    private int price; // Stored as integer and divided by 100 for displaying
    private Client client;

    public Apartment(String address, int roomCount, int area, int price, Client client) {
        this.address = address;
        this.roomCount = roomCount;
        this.area = area;
        this.price = price;
        this.client = client;
    }

    public Apartment(int price, int id, String address, int area, int roomCount, Client client) {
        this.price = price;
        this.id = id;
        this.address = address;
        this.area = area;
        this.roomCount = roomCount;
        this.client = client;
    }

    public String getAddress() {
        return address;
    }

    public int getRoomCount() {
        return roomCount;
    }

    public int getArea() {
        return area;
    }

    public int getPrice() {
        return price;
    }

    public Client getClient() {
        return client;
    }


    public void setClient(Client client) {
        this.client = client;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRoomCount(int roomCount) {
        this.roomCount = roomCount;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Address: " + address +
                "\nRoom count: " + roomCount +
                "\nArea: " + area +
                "\nPrice " + price * 1.0 / 100 + "BYN";
    }
}
