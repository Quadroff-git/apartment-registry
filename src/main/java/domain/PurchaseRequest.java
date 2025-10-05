package domain;

public class PurchaseRequest {
    private int id;
    private int roomCount;
    private int minArea;
    private int maxArea;
    private int minPrice;
    private int maxPrice;
    private Client client;


    public PurchaseRequest(int roomCount, int minArea, int maxArea, int minPrice, int maxPrice, Client client) {
        this.roomCount = roomCount;
        this.minArea = minArea;
        this.maxArea = maxArea;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.client = client;
    }


    public PurchaseRequest(Client client, int maxPrice, int minPrice, int maxArea, int minArea, int roomCount, int id) {
        this.client = client;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.maxArea = maxArea;
        this.minArea = minArea;
        this.roomCount = roomCount;
        this.id = id;
    }

    public int getRoomCount() {
        return roomCount;
    }

    public int getMinArea() {
        return minArea;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public int getMaxArea() {
        return maxArea;
    }

    public Client getClient() {
        return client;
    }



    public void setClient(Client client) {
        this.client = client;
    }

    public void setMaxArea(int maxArea) {
        this.maxArea = maxArea;
    }

    public void setRoomCount(int roomCount) {
        this.roomCount = roomCount;
    }

    public void setMinArea(int minArea) {
        this.minArea = minArea;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }


    @Override
    public String toString() {
        return "Desired room count: " + roomCount +
                "\nArea range: " + minArea + " - " + maxArea +
                "\nPrice range: " + minPrice * 1.0 / 100 + " - " + maxPrice * 1.0 / 100 + " BYN";
    }
}
