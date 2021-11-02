package spring.cloud.kafka.BI.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import spring.cloud.kafka.BI.models.OrderEvent;
import spring.cloud.kafka.BI.models.ProductEvent;
import spring.cloud.kafka.BI.models.QuantitySummary;
import spring.cloud.kafka.BI.services.InteractiveQuery;

@RestController
public class BIController 
{
	@Autowired
	InteractiveQuery interactiveQuery;
	
	//logger to print out console output
	private static final Logger log = LoggerFactory.getLogger(InteractiveQuery.class);
	
	//define url addresses
	private String url = "http://localhost:8082/orders/";
	private String urlProduct = "http://localhost:8081/products/";
	
	//build rest template for getting HTTP requests
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
	
	//Generate the total order quantity per product
	@GetMapping("/BI/all")
    List<QuantitySummary> getTotalQuantityAll(RestTemplate restTemplate) {
		ArrayList<OrderEvent> orders = getOrders(restTemplate);
		ArrayList<ProductEvent> products = getProducts(restTemplate);
		List<QuantitySummary> quantitySummaries = interactiveQuery.getTotalQuantityAll(orders,products);
		// log the output in console
		for(QuantitySummary summary : quantitySummaries)
		{
			log.info(summary.toString());
		}
        return quantitySummaries;
    }
	
	//Look up a list of products for a customer
    @GetMapping("/BI/{customerId}/productList")
    List<String> getProductList(@PathVariable Long customerId, RestTemplate restTemplate) {
    	ArrayList<OrderEvent> orders = getOrders(restTemplate);
    	ArrayList<ProductEvent> products = getProducts(restTemplate);
        return interactiveQuery.getProductList(orders, products, customerId);
    }
    
    //Look up the total order value of a list of products for a customer
    @GetMapping("/BI/{customerId}/totalValue")
    double getTotalValue(@PathVariable Long customerId, RestTemplate restTemplate) {
    	ArrayList<OrderEvent> orders = getOrders(restTemplate);
    	ArrayList<ProductEvent> products = getProducts(restTemplate);
        return interactiveQuery.getTotalValue(orders, products, customerId);
    }
    
    //Load all the order info
    ArrayList<OrderEvent> getOrders(RestTemplate restTemplate)
    {
    	ArrayList<OrderEvent> orders = new ArrayList<OrderEvent>();
    	for(int index = 1;index <= 5;index++)
    	{
    		String urlNew = url + index;
    		OrderEvent order = restTemplate.getForObject(urlNew, OrderEvent.class);
    		orders.add(order);
    	}
    	return orders;
    }
    
    //Load all the product info
    ArrayList<ProductEvent> getProducts(RestTemplate restTemplate)
    {
    	ArrayList<ProductEvent> products = new ArrayList<ProductEvent>();
    	for(int index = 1;index <= 3;index++)
    	{
    		String urlProductNew = urlProduct + index;
    		ProductEvent product = restTemplate.getForObject(urlProductNew, ProductEvent.class);
    		products.add(product);
    	}
    	return products;
    }
}
