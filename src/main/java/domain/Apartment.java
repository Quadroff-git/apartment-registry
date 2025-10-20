package domain;

public class Apartment {
    private Integer id;
    private String street;
    private int building;
    private int number;
    private int roomCount;
    private int area;
    private int price; // Stored as integer and divided by 100 for displaying


    public static final int MIN_BUILDING = 1;
    public static final int MAX_BUILDING = 500;

    public static final int MIN_NUMBER = 1;
    public static final int MAX_NUMBER = 500;

    public static final int MIN_ROOM_COUNT = 1;
    public static final int MAX_ROOM_COUNT = 10;

    public static final int MIN_AREA = 20;
    public static final int MAX_AREA = 500;

    public static final int MIN_PRICE = 1000000;
    public static final int MAX_PRICE = 100000000;


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
        if (street == null || street.trim().isEmpty()) {
            throw new IllegalArgumentException("Street cannot be null or empty");
        }
        this.street = street.trim();
    }

    public void setBuilding(int building) {
        if (building < MIN_BUILDING || building > MAX_BUILDING) {
            throw new IllegalArgumentException(
                    String.format("Building number must be between %d and %d", MIN_BUILDING, MAX_BUILDING)
            );
        }
        this.building = building;
    }

    public void setNumber(int number) {
        if (number < MIN_NUMBER || number > MAX_NUMBER) {
            throw new IllegalArgumentException(
                    String.format("Apartment number must be between %d and %d", MIN_NUMBER, MAX_NUMBER)
            );
        }
        this.number = number;
    }

    public void setRoomCount(int roomCount) {
        if (roomCount < MIN_ROOM_COUNT || roomCount > MAX_ROOM_COUNT) {
            throw new IllegalArgumentException(
                    String.format("Room count must be between %d and %d", MIN_ROOM_COUNT, MAX_ROOM_COUNT)
            );
        }
        this.roomCount = roomCount;
    }

    public void setArea(int area) {
        if (area < MIN_AREA || area > MAX_AREA) {
            throw new IllegalArgumentException(
                    String.format("Area must be between %d and %d", MIN_AREA, MAX_AREA)
            );
        }
        this.area = area;
    }

    public void setPrice(int price) {
        if (price < MIN_PRICE || price > MAX_PRICE) {
            throw new IllegalArgumentException(
                    String.format("Price must be between %d and %d", MIN_PRICE, MAX_PRICE)
            );
        }
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
