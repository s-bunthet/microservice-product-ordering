package io.github.sbunthet.events;

public class OrderPlacedEvent {
    private String orderNumber;
    public OrderPlacedEvent() {
    }
    public OrderPlacedEvent(String orderNumber) {
        this.orderNumber = orderNumber;
    }
    public String getOrderNumber() {
        return orderNumber;
    }
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Override
    public String toString() {
        return "OrderPlacedEvent{" +
                "orderNumber='" + orderNumber + '\'' +
                '}';
    }
}
