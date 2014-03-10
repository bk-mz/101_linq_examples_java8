package linq.domain;

import java.time.LocalDateTime;

public class Order {
	public final int orderID;
	public final LocalDateTime orderDate;
	public final double total;

	public Order(int orderID, LocalDateTime orderDate, double total) {
		this.orderID = orderID;
		this.orderDate = orderDate;
		this.total = total;
	}
}
