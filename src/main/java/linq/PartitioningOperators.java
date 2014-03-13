package linq;

import linq.domain.Customer;
import linq.domain.Objects;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class PartitioningOperators {

	public static void main(String[] args) {
		linq20();
		linq21();
	}


	/**
	 * Take - Simple
	 */
	static void linq20() {
		int[] numbers = {5, 4, 1, 3, 9, 8, 6, 7, 2, 0};
		System.out.println("First 3 numbers:");
		Arrays.stream(numbers).limit(3).forEach(System.out::println);
	}

	/**
	 * Take - Nested
	 */
	static void linq21() {
		List<Customer> customerList = Objects.getCustomerList();
		System.out.println("First 3 orders in WA:");
		customerList.stream().filter(c -> "WA".equals(c.region))
				.flatMap(c -> c.orders.stream()
						.map(o -> new HashMap<String, Object>() {{
							put("customerID", c.customerID);
							put("orderID", o.orderID);
							put("orderDate", o.orderDate);
						}})).limit(3)
				.forEach(ObjectDumper::dump);
	}

	/**
	 * Skip - Simple
	 */
	static void linq22() {
		int[] numbers = {5, 4, 1, 3, 9, 8, 6, 7, 2, 0};
		System.out.println("All but first 4 numbers:");
		Arrays.stream(numbers).skip(4).forEach(System.out::println);
	}

	/**
	 * Skip - Nested
	 */
	static void linq23() {
		List<Customer> customerList = Objects.getCustomerList();
		System.out.println("All but first 2 orders in WA:");
		customerList.stream().filter(c -> "WA".equals(c.region))
				.flatMap(c -> c.orders.stream()
						.map(o -> new HashMap<String, Object>() {{
							put("customerID", c.customerID);
							put("orderID", o.orderID);
							put("orderDate", o.orderDate);
						}})).skip(2)
				.forEach(ObjectDumper::dump);
	}

	/**
	 * TakeWhile - Simple
	 */
	static void linq24() {
		int[] numbers = {5, 4, 1, 3, 9, 8, 6, 7, 2, 0};
		System.out.println("First numbers less than 6:");
	}
}
