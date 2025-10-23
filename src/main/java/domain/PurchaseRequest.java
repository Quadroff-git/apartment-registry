package domain;

public class PurchaseRequest {
    private Integer id;
    private int roomCount;
    private int minArea;
    private int maxArea;
    private int minPrice;
    private int maxPrice;
    private Client client;


    public static final int MIN_ROOM_COUNT = 1;
    public static final int MAX_ROOM_COUNT = 10;

    public static final int MIN_AREA = 20;
    public static final int MAX_AREA = 500;

    public static final int MIN_PRICE = 1000000;
    public static final int MAX_PRICE = 100000000;


    public PurchaseRequest(int roomCount, int minArea, int maxArea, int minPrice, int maxPrice, Client client) {
        this.id = null;
        this.roomCount = roomCount;
        this.minArea = minArea;
        this.maxArea = maxArea;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.client = client;
    }


    public PurchaseRequest(int id, int roomCount, int minArea, int maxArea, int minPrice, int maxPrice, Client client) {
        this.id = id;
        this.client = client;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.maxArea = maxArea;
        this.minArea = minArea;
        this.roomCount = roomCount;
    }


    public Integer getId() { return id; }

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


    public void setId(Integer id) {
        this.id = id;
    }

    public void setRoomCount(int roomCount) {
        if (roomCount < MIN_ROOM_COUNT || roomCount > MAX_ROOM_COUNT) {
            throw new IllegalArgumentException(
                    String.format("Room count must be between %d and %d", MIN_ROOM_COUNT, MAX_ROOM_COUNT)
            );
        }
        this.roomCount = roomCount;
    }

    public void setMinArea(int minArea) {
        if (minArea < MIN_AREA || minArea > MAX_AREA) {
            throw new IllegalArgumentException(
                    String.format("Minimum area must be between %d and %d", MIN_AREA, MAX_AREA)
            );
        }
        this.minArea = minArea;
        validateAreaRange();
    }

    public void setMaxArea(int maxArea) {
        if (maxArea < MIN_AREA || maxArea > MAX_AREA) {
            throw new IllegalArgumentException(
                    String.format("Maximum area must be between %d and %d", MIN_AREA, MAX_AREA)
            );
        }
        this.maxArea = maxArea;
        validateAreaRange();
    }

    public void setMinPrice(int minPrice) {
        if (minPrice < MIN_PRICE || minPrice > MAX_PRICE) {
            throw new IllegalArgumentException(
                    String.format("Minimum price must be between %d and %d", MIN_PRICE, MAX_PRICE)
            );
        }
        this.minPrice = minPrice;
        validatePriceRange();
    }

    public void setMaxPrice(int maxPrice) {
        if (maxPrice < MIN_PRICE || maxPrice > MAX_PRICE) {
            throw new IllegalArgumentException(
                    String.format("Maximum price must be between %d and %d", MIN_PRICE, MAX_PRICE)
            );
        }
        this.maxPrice = maxPrice;
        validatePriceRange();
    }

    public void setClient(Client client) {
        if (client == null) {
            throw new IllegalArgumentException("Client cannot be null");
        }
        this.client = client;
    }

    // Helper methods for range validation
    private void setAreaRange(int minArea, int maxArea) {
        setMinArea(minArea);
        setMaxArea(maxArea);
    }

    private void setPriceRange(int minPrice, int maxPrice) {
        setMinPrice(minPrice);
        setMaxPrice(maxPrice);
    }

    private void validateAreaRange() {
        if (minArea > maxArea) {
            throw new IllegalArgumentException(
                    String.format("Minimum area (%d) cannot be greater than maximum area (%d)", minArea, maxArea)
            );
        }
    }

    private void validatePriceRange() {
        if (minPrice > maxPrice) {
            throw new IllegalArgumentException(
                    String.format("Minimum price (%d) cannot be greater than maximum price (%d)", minPrice, maxPrice)
            );
        }
    }


    @Override
    public String toString() {
        return "ID: " + id +
                "\nЖелаемое количество комнат: " + roomCount +
                "\nДиапазон площади: " + minArea + " - " + maxArea +
                "\nЦеновой диапазон: " + minPrice * 1.0 / 100 + " - " + maxPrice * 1.0 / 100 + " $" +
                "\nКлиент:\n" + client;
    }
}
