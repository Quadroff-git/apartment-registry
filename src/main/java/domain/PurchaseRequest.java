package domain;

public class PurchaseRequest {
    private int roomCount;
    private int minArea;
    private int maxArea;
    private int minPrice;
    private int maxPrice;


    public PurchaseRequest(int roomCount, int minArea, int maxArea, int minPrice, int maxPrice) {
        this.roomCount = roomCount;
        this.minArea = minArea;
        this.maxArea = maxArea;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
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
