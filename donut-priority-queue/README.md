# Donut Priority Queue web services # 
- - - - - - - -

## Problem Statement ##

This project is a small REST application which manages the product stock in an E-Commerce-Shop. 

Donut Priority Queue：

Jim works in the production facility of a premium online donut retailer. He is responsible forbringing donuts to the pickup counter once they’re done. But the manager is not satisfied because either it takes too long before a delivery arrives, or only a few items arrive. Jim’s manager wants to fix this and asks Jim to write a web service that accepts the orders and  provides a list of items to deliver to the pickup counter. Desperate Jim remembers his friend who is working in a software company as a software engineer ... and your phonerings. Canyou help Jim? Problem Statement Jim explains to you that he has a bunch of requirements for the priority queue and the webservice.

The constraints he was given are: 

* The service should implement a RESTful interface
* All orders will be placed in a single queue
* Each order is comprised of the ID of the client and the requested quantity of donuts
* A client can only place one order and existing orders cannot be modified (only canceled)
* Client IDs are in the range of 1 to 20000 and customers with IDs less than 1000 are premium customers
* Orders are sorted by the number of seconds they are in the queue
* Orders from premium customers always have a higher priority than orders from regular customers

Jim is supposed to look at the queue every 5 minutes and bring as many orders to the pickup counter as possible. His cart holds up to 50 donuts and he should put as Coding Assignment many orders into his cart as possible without skipping, changing, or splitting any orders.  This leads to the following list of requirements for the endpoints: 
 
* An endpoint for adding items to the queue. This endpoint must take two parameters, the ID of the client and the quantity
* An endpoint for the client to check his queue position and approximate wait time. Counting starts at 1.
* An endpoint which allows his manager to see all entries in the queue with the approximate wait time  
* An endpoint to retrieve his next delivery which should be placed in the cart  
* An endpoint to cancel an order. This endpoint should accept only the client ID 

Endpoints should follow REST best practices. Parameters should be passed in the fashion that most closely aligns with REST principles. 

## GETTING STARTED ##
This application is a written in java using Spring Boot and built using Maven. 

### Prerequisites ###

* JDK 8 or later
* Maven 3.0 or later
* You can also import the code straight into your IDE: Spring Tool Suite (STS) or IntelliJ IDEA



### Quick Start ###
To test the application, just follow the two simple steps mentioned below: 

Run the jar file:

```
		java -jar donut-priority-queue-0.1.0.jar
```

### Build an executable JAR ###
You can run the application from the command line using
```
        mvn spring-boot:run
```
Or you can build a single executable JAR file that contains all the necessary dependencies, classes, and resources with
```
        mvn clean package
```
Then you can run the JAR file with
```
        java -jar target/*.jar
```
### Test ###


You can test these Services using any tool or language that allows you to make a HTTP request. For example, removing an order using CURL:

the following curl commands and result shows a complete example for the calling of defferent endpoints: 
- add an order for normal client(clientid > 1000)

```
        curl -X POST localhost:8080/addOrder -H "Content-Type: application/json" -d "{\"clientId\":3300,\"quantity\":20}"
		{"success":true,"message":"Order was added in the queue."}
```

- add an order for an premium client

```
		curl -X POST localhost:8080/addOrder -H "Content-Type: application/json" -d "{\"clientId\":800,\"quantity\":10}"
		{"success":true,"message":"Order was added in the queue."}
```

- add an order for another premium client

```
		curl -X POST localhost:8080/addOrder -H "Content-Type: application/json" -d "{\"clientId\":500,\"quantity\":35}"
		{"success":true,"message":"Order was added in the queue."}
```

- then if you call the getOrderList endpoint, we could get the priority queue of the orders

```
	curl -X GET  localhost:8080/getOrderList
	{"queue":[{"clientId":800,"quantity":10,"timeOfOrder":1620963209,"timeInQueue":296,"isPremium":true},{"clientId":500,"quantity":35,"timeOfOrder":1620963225,"timeInQueue":280,"isPremium":true},{"clientId":3300,"quantity":20,"timeOfOrder":1620963125,"timeInQueue":380,"isPremium":false}],"success":true}
```
- we could check then the queue position and approximate wait time with endpoint getPosition

```
	curl -X GET localhost:8080/getPosition/500
	{"position":2,"success":true,"timeInQueue":430}
```

- we could check the orders for next delivery with endpoint getNextDelivery

```
	curl -X GET localhost:8080/getNextDelivery
	{"queue":[{"clientId":800,"quantity":10,"timeOfOrder":1620963209,"timeInQueue":645,"isPremium":true},{"clientId":500,"quantity":35,"timeOfOrder":1620963225,"timeInQueue":629,"isPremium":true}],"success":true}
```
- we could also remove a order for a giving clientID

```
	curl -X DELETE  http://localhost:8080/remove/500
```

- Now wenn we check the order list again, could find, the order with clientID 500 is already removed 

```
	curl -X GET  localhost:8080/getOrderList
	{"queue":[{"clientId":800,"quantity":10,"timeOfOrder":1620963209,"timeInQueue":788,"isPremium":true},{"clientId":3300,"quantity":20,"timeOfOrder":1620963125,"timeInQueue":872,"isPremium":false}],"success":true}
```



### Object Model ###
```
PriorityQueue<OrderItem>[ 
    OrderItem{
        clientId, 
        quantity,
        timeOfOrder,
        timeInQueue,
		isPremium
    }, ...   
]
```


