package domain;

public class Apartment {
    private Integer id;
    private String street;
    private int building;
    private int number;
    private int roomCount;
    private int area;
    private int price; // Stored as integer and divided by 100 for displaying


    public Apartment(String street, int building, int number, int roomCount, int area, int price) {
        this.id = null;
        this.street = street;
        this.building = building;
        this.number = number;
        this.roomCount = roomCount;
        this.area = area;
        this.price = price;
    }

    public Apartment(int id, String street, int building, int number, int roomCount, int area, int price) {
        this(street, building, number, roomCount, area, price);
        this.id = id;
    }


    public Integer getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public int getBuilding() {
        return building;
    }

    public int getNumber() {
        return number;
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


    public void setId(Integer id) {
        this.id = id;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setBuilding(int building) {
        this.building = building;
    }

    public void setNumber(int number) {
        this.number = number;
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
        return "ID: " + id +
                "\nАдрес: " + street + " " + building + ", " + number +
                "\nКоличество комнат: " + roomCount +
                "\nПлощадь: " + area +
                "\nЦена " + price * 1.0 / 100 + "$";
    }
}
