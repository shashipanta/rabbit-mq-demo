# RabbitMQ
In RabbitMQ, the producer-consumer pattern is a fundamental concept used for messaging. It involves one or more producers sending messages to a message broker (RabbitMQ), and one or more consumers receiving and processing those messages. Here's how it works:

    Producer:
        A producer is responsible for creating and sending messages to RabbitMQ.
        Producers can be any application or service that needs to send data or trigger actions asynchronously.
        Producers publish messages to named exchanges in RabbitMQ.
        Messages can be any data format (e.g., JSON, XML, plain text) and can represent tasks, events, notifications, etc.

    Exchange:
        An exchange is a routing component in RabbitMQ that receives messages from producers and routes them to one or more queues.
        Exchanges use routing rules (bindings) to determine which queues receive specific messages.

    Queue:
        A queue is a storage mechanism in RabbitMQ where messages are temporarily held until they are consumed by consumers.
        Consumers subscribe to queues to receive messages.
        Each message is typically delivered to exactly one queue, based on the routing rules defined by the exchange.

    Consumer:
        A consumer is an application or service that receives messages from RabbitMQ and processes them.
        Consumers subscribe to queues and receive messages as they become available.
        Consumers can perform various tasks such as data processing, executing business logic, or triggering actions based on received messages.

Here's a basic overview of how the producer-consumer pattern works in RabbitMQ:

    Producer sends messages: The producer publishes messages to a specific exchange in RabbitMQ, specifying a routing key if necessary.

    Messages routed to queues: The exchange routes messages to one or more queues based on the routing rules defined by the exchange.

    Consumers receive messages: Consumers subscribe to queues and receive messages as they become available.

    Messages processed by consumers: Consumers process messages according to their business logic, acknowledging the receipt of each message to RabbitMQ once processing is complete.

    Message acknowledgment: After processing a message, the consumer sends an acknowledgment (ack) to RabbitMQ to confirm successful processing. RabbitMQ removes acknowledged messages from the queue.

    Error handling: If a consumer encounters an error while processing a message, it can choose to reject the message or requeue it for later processing.

This pattern enables asynchronous communication between different parts of a distributed system, allowing for scalability, fault tolerance, and decoupling between producers and consumers.

# Product Queue
```java
    @Bean
    public Queue productQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
        args.put("x-dead-letter-routing-key", DEAD_LETTER_ROUTING_KEY);
        args.put("x-message-ttl", 4000);
        return new Queue(PRODUCT_QUEUE, true, false, false, args);
    }

    @Bean
    public Exchange productExchange() {
        return new TopicExchange(PRODUCT_EXCHANGE);
    }

    // Main Queue
    @Bean
    public Binding productBinding() {
        return BindingBuilder
                .bind(productQueue())
                .to(productExchange())
                .with(PRODUCT_ROUTING_KEY).noargs();
    }
```
# Publisher (Product publisher)
Product publisher publishes message to Product_Queue with the help of RabbitTemplate
```java
public void updateProduct(Product product) {
        rabbitTemplate.convertAndSend(PRODUCT_EXCHANGE, PRODUCT_ROUTING_KEY, product);
    }
```

# Listener (Product Listener)
Product listener is the consumer that looks into product_queue and consumes the message.
```java
 @RabbitListener(queues = {PRODUCT_QUEUE})
    public void onProductCreation(Product product, Channel channel, Message message) throws IOException {
        log.info("Product registered : {}", product);
        if (product == null)
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        productViewService.create(product);
    }

    @RabbitListener(queues = {PRODUCT_QUEUE})
    public void onProductUpdate(Product product, Channel channel, Message message) throws IOException {
        log.info("Product updated : {}", product);
        try {
            productViewService.update(product);
        } catch (Exception e) {
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        }
    }
```
* `channel.basicNack()` is used to negatively acknowledge message by client.
# Using Queue
* Create new Product
    ```http request
    POST http://localhost:8070/api/v1/products
    Content-Type: application/json
    
    {
    "id": null,
    "manufacturerName": "new product",
    "name": "Product1",
    "nameNp": "Nepali Product1",
    "price": 12000,
    "productSerialNo": "SER-12344-123"
    }
    ```
* Update Product
    ```http request
    ### update product
    PUT http://localhost:8070/api/v1/products/200
    Content-Type: application/json
    
    {
    "id": null,
    "manufacturerName": "Samsung Updated",
    "name": "Samsung Galaxy ---",
    "nameNp": "Samsung Galaxy",
    "price": 100050,
    "productSerialNo": "SER-GAL-123"
    }
    ```
* Dead letter Queue:
To pass message to dead letter queue, we pass empty `Product` that
creates hibernate exception and thus timeout occurs.
    ```http request
    ### invalid
    GET http://localhost:8070/api/v1/products/invalid
    ```