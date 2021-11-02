package spring.cloud.kafka.BI.services;
import org.springframework.stereotype.Service;

import spring.cloud.kafka.BI.models.OrderEvent;
import spring.cloud.kafka.BI.models.ProductEvent;
import spring.cloud.kafka.BI.models.QuantitySummary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class InteractiveQuery
{
	private InteractiveQueryService interactiveQueryService;
    
	@Autowired
	public InteractiveQuery(InteractiveQueryService interactiveQueryService) {
        this.interactiveQueryService = interactiveQueryService;
    }
	
	//Generate the total order quantity per product
	public List<QuantitySummary> getTotalQuantityAll(ArrayList<OrderEvent> orders, ArrayList<ProductEvent> products) {
		List<QuantitySummary> totalQuantityPerProduct = new ArrayList<QuantitySummary>();
		HashMap<String,Integer> result = interactiveQueryService.getTotalQuantityAll(orders,products);
		for(String key:result.keySet())
		{
			totalQuantityPerProduct.add(new QuantitySummary(key,result.get(key).longValue()));
		}
        return totalQuantityPerProduct;
    }
	
	//Look up a list of products for a customer
	public List<String> getProductList(ArrayList<OrderEvent> orders, ArrayList<ProductEvent> products, Long customerId) {
		List<String> productList = new ArrayList<String>();
		List<Integer> ids = interactiveQueryService.getProducts(customerId,orders);
		for(Integer id:ids)
		{
			for(ProductEvent product:products)
			{
				if(product.getId().longValue() == id)
				{
					productList.add(product.getName());
					break;
				}
			}
		}
        return productList;
    }
	
	//Look up the total order value of a list of products for a customer
	public double getTotalValue(ArrayList<OrderEvent> orders, ArrayList<ProductEvent> products, Long customerId) 
	{
		List<Integer> keys = interactiveQueryService.getProducts(customerId,orders);
		HashMap<Integer,Double> result = interactiveQueryService.getValues(keys, products);
		double totalValue = 0;
		for(OrderEvent order:orders)
		{
			if(order.getCustomerId() == customerId)
			{
				int quantity = order.getQuantity();
				for(Integer id:result.keySet())
				{
					if(order.getProductId().intValue() == id)
					{
						totalValue += quantity * result.get(id);
						break;
					}
				}
			}
		}
        return totalValue;
    }
}
