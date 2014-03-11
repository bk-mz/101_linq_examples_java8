package linq;

import linq.domain.Customer;
import linq.domain.Objects;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

public class ProjectionOperators {
	public static void main(String[] args) {
		linq6();
		linq7();
		linq8();
		linq9();
		linq10();
		linq11();
		linq12();
		linq13();
		linq14();
		linq15();
		linq16();
		linq17();
		linq18();
		linq19();
	}

	/**
	 * Select Simple 1
	 */
	private static void linq6() {
		int[] numbers = {5, 4, 1, 3, 9, 8, 6, 7, 2, 0};
		System.out.println("Numbers + 1:");
		Arrays.stream(numbers).map(x -> x + 1).forEach(System.out::println);
	}

	/**
	 * Select Simple 2
	 */
	private static void linq7() {
		System.out.println("Product Names:");
		Objects.getProductList().stream().map(x -> x.productName).forEach(System.out::println);
	}

	/**
	 * Select - Transformation
	 */
	private static void linq8() {
		int[] numbers = {5, 4, 1, 3, 9, 8, 6, 7, 2, 0};
		String[] strings = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};

		System.out.println("Number strings:");
		Arrays.stream(numbers).mapToObj(x -> strings[x]).forEach(System.out::println);
	}

	/**
	 * Select - Anonymous Types 1
	 */
	private static void linq9() {
		String[] words = {"aPPLE", "BlUeBeRrY", "cHeRry"};

		Arrays.stream(words).map(s -> new HashMap<String, String>() {{
			put("Upper", s.toUpperCase());
			put("Lower", s.toLowerCase());
		}}).forEach(map -> System.out.println(
				String.format("Uppercase : %s, Lowercase : %s", map.get("Upper"), map.get("Lower"))));
	}

	/**
	 * Select - Anonymous Types 2
	 */
	private static void linq10() {
		int[] numbers = {5, 4, 1, 3, 9, 8, 6, 7, 2, 0};
		String[] strings = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};

		Arrays.stream(numbers).mapToObj(number -> new HashMap<String, Object>() {{
			put("Digit", strings[number]);
			put("Even", number % 2 == 0);
		}}).map(x -> String.format("The digit %s is %s.", x.get("Digit"), x.get("Even").equals(Boolean.FALSE) ? "odd" : "even"))
				.forEach(System.out::println);
	}

	/**
	 * Select - Anonymous Types 3
	 */
	private static void linq11() {

		System.out.println("Product Info:");

		Objects.getProductList().stream().map(x -> new HashMap<String, Object>() {{
			put("ProductName", x.productName);
			put("Category", x.category);
			put("Price", x.unitPrice);
		}}).map(x -> String.format("%s is in the category %s and costs %f per unit.",
				x.get("ProductName"), x.get("Category"), x.get("Price"))).forEach(System.out::println);
	}

	/**
	 * Select - Indexed
	 */
	private static void linq12() {
		int[] numbers = {5, 4, 1, 3, 9, 8, 6, 7, 2, 0};

		System.out.println("Number: In-place?");
		IntStream.range(0, numbers.length).mapToObj(i -> new HashMap<String, Object>() {{
			put("Num", numbers[i]);
			put("InPlace", numbers[i] == i);
		}}).map(map -> map.get("Num") + ": " + map.get("InPlace")).forEach(System.out::println);
	}

	/**
	 * Select - Filtered
	 */
	private static void linq13() {
		int[] numbers = {5, 4, 1, 3, 9, 8, 6, 7, 2, 0};
		String[] digits = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};

		System.out.println("Numbers < 5:");
		Arrays.stream(numbers).filter(x -> x < 5).mapToObj(x -> digits[x])
				.forEach(System.out::println);
	}

	/**
	 * SelectMany - Compound from 1
	 */
	private static void linq14() {
		int[] numbersA = {0, 2, 4, 5, 6, 8, 9};
		int[] numbersB = {1, 3, 5, 7, 8};

		System.out.println("Pairs where a < b:");
		Arrays.stream(numbersA)
				.mapToObj(a -> Arrays.stream(numbersB)
						.filter(b -> a < b)
						.mapToObj(b -> new HashMap<String, Object>() {{
							put("A", a);
							put("B", b);
						}}))
				.flatMap(x -> x)
				.map(x -> String.format("%d is less than %d", x.get("A"), x.get("B")))
				.forEach(System.out::println);
	}

	/**
	 * SelectMany - Compound from 2
	 */
	private static void linq15() {
		List<Customer> customerList = Objects.getCustomerList();

		customerList.stream().flatMap(c -> c.orders.stream()
				.filter(o -> o.total < 500.0)
				.map(o -> new HashMap<String, Object>() {{
					put("customerID", c.customerID);
					put("orderID", o.orderID);
					put("total", o.total);
				}})).forEach(ObjectDumper::dump);
	}

	/**
	 * SelectMany - Compound from 3
	 */
	private static void linq16() {
		List<Customer> customerList = Objects.getCustomerList();

		customerList.stream().flatMap(c -> c.orders.stream()
				.filter(o -> o.orderDate.isAfter(LocalDateTime.of(1998, 1, 1, 0, 0, 0)))
				.map(o -> new HashMap<String, Object>() {{
					put("customerID", c.customerID);
					put("orderID", o.orderID);
					put("orderDate", o.orderDate);
				}})).forEach(ObjectDumper::dump);
	}

	/**
	 * SelectMany - from Assignment
	 */
	private static void linq17() {
		List<Customer> customerList = Objects.getCustomerList();

		customerList.stream().flatMap(c -> c.orders.stream()
				.filter(o -> o.total >= 2000.0)
				.map(o -> new HashMap<String, Object>() {{
					put("customerID", c.customerID);
					put("orderID", o.orderID);
					put("total", o.total);
				}})).forEach(ObjectDumper::dump);
	}

	/**
	 * SelectMany - Multiple from
	 */
	private static void linq18() {
		List<Customer> customerList = Objects.getCustomerList();
		LocalDateTime cutoffDate = LocalDateTime.of(1997, 1, 1, 0, 0, 0);

		customerList.stream()
				.filter(c -> "WA".equals(c.region))
				.flatMap(c -> c.orders.stream()
						.filter(o -> o.orderDate.isAfter(cutoffDate))
						.map(o -> new HashMap<String, Object>() {{
							put("customerID", c.customerID);
							put("orderID", o.orderID);
						}})).forEach(ObjectDumper::dump);

	}

	/**
	 * SelectMany - Indexed
	 */
	private static void linq19() {
		List<Customer> customerList = Objects.getCustomerList();
		IntStream.range(0, customerList.size())
				.mapToObj(i -> customerList.get(i).orders.stream().map(o ->
						"Customer #" + (i + 1) + " has an order with OrderID " + o.orderID))
				.flatMap(x -> x).forEach(System.out::println);
	}
}
