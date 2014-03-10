package linq;

import linq.domain.Customer;
import linq.domain.Objects;
import linq.domain.Order;
import linq.domain.Product;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class RestrictionOperators {

	public static void main(String[] args) {
		linq1();
		linq2();
		linq3();
		linq4();
		linq5();
	}

	/**
	 * "This sample uses the where clause to find all elements of an array with a value less than 5."
	 */
	public static void linq1() {
		int[] numbers = {5, 4, 1, 3, 9, 8, 6, 7, 2, 0};
		IntStream stream = Arrays.stream(numbers).filter(x -> x < 5);
		System.out.println("Numbers < 5:");
		stream.forEach(System.out::println);
	}

	/**
	 * "This sample uses the where clause to find all products that are out of stock."
	 */
	public static void linq2() {
		List<Product> products = Objects.getProductList();
		Stream<Product> stream = products.stream().filter(x -> x.unitsInStock == 0);
		System.out.println("Sold out products:");
		stream.map(x -> x.productName).forEach(System.out::println);
	}

	/**
	 * "This sample uses the where clause to find all products that are in stock and " +
	 * "cost more than 3.00 per unit."
	 */
	public static void linq3() {
		List<Product> products = Objects.getProductList();
		Stream<Product> stream = products.stream().filter(x -> x.unitsInStock > 0 && x.unitPrice > 3.00);
		System.out.println("In-stock products that cost more than 3.00:");
		stream.map(x -> String.format("%s is in stock and costs more than 3.00.", x.productName))
				.forEach(System.out::println);
	}

	/*
	* This sample uses the where clause to find all customers in Washington
	* and then it uses a foreach loop to iterate over the orders collection that belongs to each customer.
	*/
	public static void linq4() {
		System.out.println("Customers from Washington and their orders:");
		Stream<Customer> stream = Objects.getCustomerList()
				.stream()
				.filter(x -> x.region != null && x.region.equals("WA"));

		stream.forEach(x -> {
			System.out.println(String.format("Customer %s: %s", x.customerID, x.companyName));
			for (Order y : x.orders) {
				System.out.println(String.format("   Order %s: %s", y.orderID, y.orderDate));
			}
		});
	}

	/**
	 * "This sample demonstrates an indexed where clause that returns digits whose name is " +
	 * "shorter than their value."
	 */
	public static void linq5() {
		String[] digits = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
		System.out.println("Short digits:");
		IntStream.range(0, digits.length)
				.filter(i -> digits[i].length() < i)
				.mapToObj(i -> String.format("The word %s is shorter than its value.", digits[i]))
				.forEach(System.out::println);
	}


}
