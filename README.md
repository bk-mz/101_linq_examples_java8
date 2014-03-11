101 LINQ Samples in Java 8 Streams and Lambdas
===========================

Port of [C#'s' 101 LINQ Samples](http://code.msdn.microsoft.com/101-LINQ-Samples-3fb9811b) translated into Java 8.
(Structure, text and etc copied from [mythz Clojure repository](https://github.com/mythz/java-linq-examples))

Execute and display the results of all the examples with:

    ./gradlew run

#### Contributions into more idiomatic and readable examples are welcome!

#### [LINQ - Restriction Operators](https://github.com/head-thrash/101_linq_examples_java8/blob/master/src/main/java/linq/RestrictionOperators.java) / [MSDN C#](http://code.msdn.microsoft.com/LINQ-to-DataSets-09787825)
#### [LINQ - Projection Operators](https://github.com/head-thrash/101_linq_examples_java8/blob/master/src/main/java/linq/ProjectionOperators.java) / [MSDN C#](http://code.msdn.microsoft.com/LINQ-Partitioning-Operators-c68aaccc)

##  Side-by-side - C# LINQ vs Java

For a side-by-side comparison, the original **C#** source code is displayed above the equivalent **Java8** translation.

  - The **Output** shows the console output of running the **Java** sample.
  - Outputs ending with `...` illustrates only a partial response is displayed.
  - The source-code for C# and Clojure utils used are included once under the first section they're used in.
  - Unfortunately, Java does not support anonymous record types, so HashMap is used. ObjectDumper substitute for HashMaps in java is located [here.](https://github.com/head-thrash/101_linq_examples_java8/blob/master/src/main/java/linq/ObjectDumper.java)

LINQ - Restriction Operators
----------------------------

### linq1: Where - Simple 1

```csharp
//c#
public void Linq1()
{
    int[] numbers = { 5, 4, 1, 3, 9, 8, 6, 7, 2, 0 };

    var lowNums =
        from n in numbers
        where n < 5
        select n;

    Console.WriteLine("Numbers < 5:");
    foreach (var x in lowNums)
    {
        Console.WriteLine(x);
    }
}
```
```java
//java
int[] numbers = {5, 4, 1, 3, 9, 8, 6, 7, 2, 0};
IntStream stream = Arrays.stream(numbers).filter(x -> x < 5);
System.out.println("Numbers < 5:");
stream.forEach(System.out::println);
```
#### Output

    Numbers < 5:
    4
    1
    3
    2
    0

### linq2: Where - Simple 2
```csharp
//c#
public void Linq2()
{
    List<Product> products = GetProductList();

    var soldOutProducts =
        from p in products
        where p.UnitsInStock == 0
        select p;

    Console.WriteLine("Sold out products:");
    foreach (var product in soldOutProducts)
    {
        Console.WriteLine("{0} is sold out!", product.ProductName);
    }
}
```
```java
//java
List<Product> products = Objects.getProductList();
Stream<Product> stream = products.stream().filter(x -> x.unitsInStock == 0);
System.out.println("Sold out products:");
stream.map(x -> x.productName).forEach(System.out::println);
```
#### Output

    Sold out products:
    Chef Anton's Gumbo Mix  is sold out
    Alice Mutton  is sold out
    Thüringer Rostbratwurst  is sold out
    Gorgonzola Telino  is sold out
    Perth Pasties  is sold out

### linq3: Where - Simple 3
```csharp
//c#
public void Linq3()
{
    List<Product> products = GetProductList();

    var expensiveInStockProducts =
        from p in products
        where p.UnitsInStock > 0 && p.UnitPrice > 3.00M
        select p;

    Console.WriteLine("In-stock products that cost more than 3.00:");
    foreach (var product in expensiveInStockProducts)
    {
        Console.WriteLine("{0} is in stock and costs more than 3.00.", product.ProductName);
    }
}
```
```java
//java
List<Product> products = Objects.getProductList();
Stream<Product> stream = products.stream().filter(x -> x.unitsInStock > 0 && x.unitPrice > 3.00);
System.out.println("In-stock products that cost more than 3.00:");
stream.map(x -> String.format("%s is in stock and costs more than 3.00.", x.productName))
        .forEach(System.out::println);
```
#### Output

    In-stock products that cost more than 3.00:
    Chai is in stock and costs more than 3.00
    Chang is in stock and costs more than 3.00
    Aniseed Syrup is in stock and costs more than 3.00
    Chef Anton's Cajun Seasoning is in stock and costs more than 3.00
    Grandma's Boysenberry Spread is in stock and costs more than 3.00

### linq4: Where - Drilldown
```csharp
//c#
public void Linq4()
{
    List<Customer> customers = GetCustomerList();

    var waCustomers =
        from c in customers
        where c.Region == "WA"
        select c;

    Console.WriteLine("Customers from Washington and their orders:");
    foreach (var customer in waCustomers)
    {
        Console.WriteLine("Customer {0}: {1}", customer.CustomerID, customer.CompanyName);
        foreach (var order in customer.Orders)
        {
            Console.WriteLine("  Order {0}: {1}", order.OrderID, order.OrderDate);
        }
    }
}
```
```java
//java
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
```
#### Output

    Customer LAZYK: Lazy K Kountry Store
       Order 10482: 1997-03-21T00:00
       Order 10545: 1997-05-22T00:00
    Customer TRAIH: Trail's Head Gourmet Provisioners
       Order 10574: 1997-06-19T00:00
       Order 10577: 1997-06-23T00:00
       Order 10822: 1998-01-08T00:00
    ...

### linq5: Where - Indexed
```csharp
//c#
public void Linq5()
{
    string[] digits = { "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine" };

    var shortDigits = digits.Where((digit, index) => digit.Length < index);

    Console.WriteLine("Short digits:");
    foreach (var d in shortDigits)
    {
        Console.WriteLine("The word {0} is shorter than its value.", d);
    }
}
```
```java
//java
String[] digits = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
System.out.println("Short digits:");
IntStream.range(0, digits.length)
        .filter(i -> digits[i].length() < i)
        .mapToObj(i -> String.format("The word %s is shorter than its value.", digits[i]))
        .forEach(System.out::println);
```
#### Output

    Short digits:
    The word five is shorter than its value
    The word six is shorter than its value
    The word seven is shorter than its value
    The word eight is shorter than its value
    The word nine is shorter than its value

LINQ - Projection Operators
---------------------------

### linq6: Select - Simple 1
```csharp
//c#
public void Linq6()
{
    int[] numbers = { 5, 4, 1, 3, 9, 8, 6, 7, 2, 0 };

    var numsPlusOne =
        from n in numbers
        select n + 1;

    Console.WriteLine("Numbers + 1:");
    foreach (var i in numsPlusOne)
    {
        Console.WriteLine(i);
    }
}
```
```java
//java
int[] numbers = {5, 4, 1, 3, 9, 8, 6, 7, 2, 0};
System.out.println("Numbers + 1:");
Arrays.stream(numbers).map(x -> x + 1).forEach(System.out::println);
```
#### Output

    Numbers + 1:
    6
    5
    2
    4
    10
    9
    7
    8
    3
    1

### linq7: Select - Simple 2
```csharp
//c#
public void Linq7()
{
    List<Product> products = GetProductList();

    var productNames =
        from p in products
        select p.ProductName;

    Console.WriteLine("Product Names:");
    foreach (var productName in productNames)
    {
        Console.WriteLine(productName);
    }
}
```
```java
//java
System.out.println("Product Names:");
Objects.getProductList().stream().map(x -> x.productName).forEach(System.out::println);
```
#### Output

    Product Names:
    Chai
    Chang
    Aniseed Syrup
    Chef Anton's Cajun Seasoning
    Chef Anton's Gumbo Mix
    ...

### linq8: Select - Transformation
```csharp
//c#
public void Linq8()
{
    int[] numbers = { 5, 4, 1, 3, 9, 8, 6, 7, 2, 0 };
    string[] strings = { "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine" };

    var textNums =
        from n in numbers
        select strings[n];

    Console.WriteLine("Number strings:");
    foreach (var s in textNums)
    {
        Console.WriteLine(s);
    }
}
```
```java
//java
int[] numbers = {5, 4, 1, 3, 9, 8, 6, 7, 2, 0};
String[] strings = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};

System.out.println("Number strings:");
Arrays.stream(numbers).mapToObj(x -> strings[x]).forEach(System.out::println);
```
#### Output

    Number strings:
    five
    four
    one
    three
    nine
    eight
    six
    seven
    two
    zero

### linq9: Select - Anonymous Types 1
```csharp
//c#
public void Linq9()
{
    string[] words = { "aPPLE", "BlUeBeRrY", "cHeRry" };

    var upperLowerWords =
        from w in words
        select new { Upper = w.ToUpper(), Lower = w.ToLower() };

    foreach (var ul in upperLowerWords)
    {
        Console.WriteLine("Uppercase: {0}, Lowercase: {1}", ul.Upper, ul.Lower);
    }
}
```
```java
//java
String[] words = {"aPPLE", "BlUeBeRrY", "cHeRry"};

Arrays.stream(words).map(s -> new HashMap<String, String>() {{
    put("Upper", s.toUpperCase());
    put("Lower", s.toLowerCase());
}}).forEach(map -> System.out.println(
        String.format("Uppercase : %s, Lowercase : %s", map.get("Upper"), map.get("Lower"))));
```
#### Output

    Uppercase: APPLE , Lowercase: apple
    Uppercase: BLUEBERRY , Lowercase: blueberry
    Uppercase: CHERRY , Lowercase: cherry

### linq10: Select - Anonymous Types 2
```csharp
//c#
public void Linq10()
{
    int[] numbers = { 5, 4, 1, 3, 9, 8, 6, 7, 2, 0 };
    string[] strings = { "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine" };

    var digitOddEvens =
        from n in numbers
        select new { Digit = strings[n], Even = (n % 2 == 0) };

    foreach (var d in digitOddEvens)
    {
        Console.WriteLine("The digit {0} is {1}.", d.Digit, d.Even ? "even" : "odd");
    }
}
```
```java
//java
int[] numbers = {5, 4, 1, 3, 9, 8, 6, 7, 2, 0};
String[] strings = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};

Arrays.stream(numbers).mapToObj(number -> new HashMap<String, Object>() {{
    put("Digit", strings[number]);
    put("Even", number % 2 == 0);
}}).map(x -> String.format("The digit %s is %s.", x.get("Digit"), x.get("Even").equals(Boolean.FALSE) ? "odd" : "even"))
        .forEach(System.out::println);
```
#### Output

    The digit five is odd
    The digit four is even
    The digit one is odd
    The digit three is odd
    The digit nine is odd
    The digit eight is even
    The digit six is even
    The digit seven is odd
    The digit two is even
    The digit zero is even

### linq11: Select - Anonymous Types 3
```csharp
//c#
public void Linq11()
{
    List<Product> products = GetProductList();

    var productInfos =
        from p in products
        select new { p.ProductName, p.Category, Price = p.UnitPrice };

    Console.WriteLine("Product Info:");
    foreach (var productInfo in productInfos)
    {
        Console.WriteLine("{0} is in the category {1} and costs {2} per unit.", productInfo.ProductName, productInfo.Category, productInfo.Price);
    }
}
```
```java
//java
System.out.println("Product Info:");

Objects.getProductList().stream().map(x -> new HashMap<String, Object>() {{
    put("ProductName", x.productName);
    put("Category", x.category);
    put("Price", x.unitPrice);
}}).map(x -> String.format("%s is in the category %s and costs %f per unit.",
        x.get("ProductName"), x.get("Category"), x.get("Price"))).forEach(System.out::println);
```
#### Output

    Product Info:
    Chai is in the category Beverages and costs 18.0
    Chang is in the category Beverages and costs 19.0
    Aniseed Syrup is in the category Condiments and costs 10.0
    ...

### linq12: Select - Indexed
```csharp
//c#
public void Linq12()
{
int[] numbers = {5, 4, 1, 3, 9, 8, 6, 7, 2, 0};

System.out.println("Number: In-place?");
IntStream.range(0, numbers.length).mapToObj(i -> new HashMap<String, Object>() {{
    put("Num", numbers[i]);
    put("InPlace", numbers[i] == i);
}}).map(map -> map.get("Num") + ": " + map.get("InPlace")).forEach(System.out::println);
}
```
```java
//java
(defn linq12 []
  (let [numbers [5 4 1 3 9 8 6 7 2 0]
        nums-in-place
        (map-indexed (fn [i num] {:num num, :in-place (= num i)}) numbers)]
    (println "Number: In-place?")
    (doseq [n nums-in-place]
      (println (:num n) ":" (:in-place n)))))
```
#### Output

    Number: In-place?
    5 : false
    4 : false
    1 : false
    3 : true
    9 : false
    8 : false
    6 : true
    7 : true
    2 : false
    0 : false

### linq13: Select - Filtered
```csharp
//c#
public void Linq13()
{
    int[] numbers = { 5, 4, 1, 3, 9, 8, 6, 7, 2, 0 };
    string[] digits = { "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine" };

    var lowNums =
        from n in numbers
        where n < 5
        select digits[n];

    Console.WriteLine("Numbers < 5:");
    foreach (var num in lowNums)
    {
        Console.WriteLine(num);
    }
}
```
```java
//java
int[] numbers = {5, 4, 1, 3, 9, 8, 6, 7, 2, 0};
String[] digits = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};

System.out.println("Numbers < 5:");
Arrays.stream(numbers).filter(x -> x < 5).mapToObj(x -> digits[x])
        .forEach(System.out::println);
```
#### Output

    Numbers < 5:
    four
    one
    three
    two
    zero

### linq14: SelectMany - Compound from 1
```csharp
//c#
public void Linq14()
{
    int[] numbersA = { 0, 2, 4, 5, 6, 8, 9 };
    int[] numbersB = { 1, 3, 5, 7, 8 };

    var pairs =
        from a in numbersA
        from b in numbersB
        where a < b
        select new { a, b };

    Console.WriteLine("Pairs where a < b:");
    foreach (var pair in pairs)
    {
        Console.WriteLine("{0} is less than {1}", pair.a, pair.b);
    }
}
```
```java
//java
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
```
#### Output

    Pairs where a < b:
    0 is less than 1
    0 is less than 3
    0 is less than 5
    0 is less than 7
    0 is less than 8
    2 is less than 3
    2 is less than 5
    2 is less than 7
    2 is less than 8
    4 is less than 5
    4 is less than 7
    4 is less than 8
    5 is less than 7
    5 is less than 8
    6 is less than 7
    6 is less than 8

### linq15: SelectMany - Compound from 2
```csharp
//c#
public void Linq15()
{
    List<Customer> customers = GetCustomerList();

    var orders =
        from c in customers
        from o in c.Orders
        where o.Total < 500.00M
        select new { c.CustomerID, o.OrderID, o.Total };

    ObjectDumper.Write(orders);
}
```
```java
//java
List<Customer> customerList = Objects.getCustomerList();

customerList.stream().flatMap(c -> c.orders.stream()
        .filter(o -> o.total < 500.0)
        .map(o -> new HashMap<String, Object>() {{
            put("customerID", c.customerID);
            put("orderID", o.orderID);
            put("total", o.total);
        }})).forEach(ObjectDumper::dump);
```
#### Output

    {:customer-id ALFKI, :order-id 10702, :total 330.00M}
    {:customer-id ALFKI, :order-id 10952, :total 471.20M}
    {:customer-id ANATR, :order-id 10308, :total 88.80M}
    {:customer-id ANATR, :order-id 10625, :total 479.75M}
    ...

### linq16: SelectMany - Compound from 3
```csharp
//c#
public void Linq16()
{
    List<Customer> customers = GetCustomerList();

    var orders =
        from c in customers
        from o in c.Orders
        where o.OrderDate >= new DateTime(1998, 1, 1)
        select new { c.CustomerID, o.OrderID, o.OrderDate };

    ObjectDumper.Write(orders);
}
```
```java
//java
List<Customer> customerList = Objects.getCustomerList();

customerList.stream().flatMap(c -> c.orders.stream()
        .filter(o -> o.orderDate.isAfter(LocalDateTime.of(1998, 1, 1, 0, 0, 0)))
        .map(o -> new HashMap<String, Object>() {{
            put("customerID", c.customerID);
            put("orderID", o.orderID);
            put("orderDate", o.orderDate);
        }})).forEach(ObjectDumper::dump);
```
#### Output

    {:customer-id ALFKI, :order-id 10835, :order-date #<DateTime 1998-01-15T00:00:00.000-05:00>}
    {:customer-id ALFKI, :order-id 10952, :order-date #<DateTime 1998-03-16T00:00:00.000-05:00>}
    {:customer-id ALFKI, :order-id 11011, :order-date #<DateTime 1998-04-09T00:00:00.000-04:00>}
    {:customer-id ANATR, :order-id 10926, :order-date #<DateTime 1998-03-04T00:00:00.000-05:00>}
    {:customer-id ANTON, :order-id 10856, :order-date #<DateTime 1998-01-28T00:00:00.000-05:00>}
    ...

### linq17: SelectMany - from Assignment
```csharp
//c#
public void Linq17()
{
    List<Customer> customers = GetCustomerList();

    var orders =
        from c in customers
        from o in c.Orders
        where o.Total >= 2000.0M
        select new { c.CustomerID, o.OrderID, o.Total };

    ObjectDumper.Write(orders);
}
```
```java
//java
List<Customer> customerList = Objects.getCustomerList();

customerList.stream().flatMap(c -> c.orders.stream()
        .filter(o -> o.total >= 2000.0)
        .map(o -> new HashMap<String, Object>() {{
            put("customerID", c.customerID);
            put("orderID", o.orderID);
            put("total", o.total);
        }})).forEach(ObjectDumper::dump);
```
#### Output

    {:customer-id ANTON, :order-id 10573, :total 2082.00M}
    {:customer-id AROUT, :order-id 10558, :total 2142.90M}
    {:customer-id AROUT, :order-id 10953, :total 4441.25M}
    {:customer-id BERGS, :order-id 10384, :total 2222.40M}
    {:customer-id BERGS, :order-id 10524, :total 3192.65M}
    ...

### linq18: SelectMany - Multiple from
```csharp
//c#
public void Linq18()
{
    List<Customer> customers = GetCustomerList();

    DateTime cutoffDate = new DateTime(1997, 1, 1);

    var orders =
        from c in customers
        where c.Region == "WA"
        from o in c.Orders
        where o.OrderDate >= cutoffDate
        select new { c.CustomerID, o.OrderID };

    ObjectDumper.Write(orders);
}
```
```java
//java
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
```
#### Output

    {:customer-id LAZYK, :order-id 10482}
    {:customer-id LAZYK, :order-id 10545}
    {:customer-id TRAIH, :order-id 10574}
    {:customer-id TRAIH, :order-id 10577}
    {:customer-id TRAIH, :order-id 10822}
    {:customer-id WHITC, :order-id 10469}
    {:customer-id WHITC, :order-id 10483}
    {:customer-id WHITC, :order-id 10504}
    {:customer-id WHITC, :order-id 10596}
    {:customer-id WHITC, :order-id 10693}
    {:customer-id WHITC, :order-id 10696}
    {:customer-id WHITC, :order-id 10723}
    {:customer-id WHITC, :order-id 10740}
    {:customer-id WHITC, :order-id 10861}
    {:customer-id WHITC, :order-id 10904}
    {:customer-id WHITC, :order-id 11032}
    {:customer-id WHITC, :order-id 11066}

### linq19: SelectMany - Indexed
```csharp
//c#
public void Linq19()
{
    List<Customer> customers = GetCustomerList();

    var customerOrders =
        customers.SelectMany(
            (cust, custIndex) =>
            cust.Orders.Select(o => "Customer #" + (custIndex + 1) +
                                    " has an order with OrderID " + o.OrderID));

    ObjectDumper.Write(customerOrders);
}
```
```java
//java
List<Customer> customerList = Objects.getCustomerList();
IntStream.range(0, customerList.size())
        .mapToObj(i -> customerList.get(i).orders.stream().map(o ->
                "Customer #" + (i + 1) + " has an order with OrderID " + o.orderID))
        .flatMap(x -> x).forEach(System.out::println);
```
#### Output

    Customer #1 has an order with OrderID 10643
    Customer #1 has an order with OrderID 10692
    Customer #1 has an order with OrderID 10702
    Customer #1 has an order with OrderID 10835
    Customer #1 has an order with OrderID 10952
    Customer #1 has an order with OrderID 11011
    Customer #2 has an order with OrderID 10308
    Customer #2 has an order with OrderID 10625
    Customer #2 has an order with OrderID 10759
    Customer #2 has an order with OrderID 10926
    ...

to be done :)
-----------------
