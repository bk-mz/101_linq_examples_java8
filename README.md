101 LINQ Samples in Java 8 Streams and Lambdas
===========================

Port of [C#'s' 101 LINQ Samples](http://code.msdn.microsoft.com/101-LINQ-Samples-3fb9811b) translated into Java 8.
(Structure, text and etc copied from [mythz Clojure repository](https://github.com/mythz/clojure-linq-examples))

Execute and display the results of all the examples with:

    ./gradlew run

#### Contributions into more idiomatic and readable examples are welcome!

#### [LINQ - Restriction Operators](https://github.com/head-thrash/101_linq_examples_java8/blob/master/src/main/java/linq/RestrictionOperators.java) / [MSDN C#](http://code.msdn.microsoft.com/LINQ-to-DataSets-09787825)

##  Side-by-side - C# LINQ vs Clojure

For a side-by-side comparison, the original **C#** source code is displayed above the equivalent **Java8** translation.

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

to be done :)
-----------------
