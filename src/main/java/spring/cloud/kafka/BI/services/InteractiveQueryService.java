package spring.cloud.kafka.BI.services;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import spring.cloud.kafka.BI.models.OrderEvent;
import spring.cloud.kafka.BI.models.ProductEvent;

@Service
public class InteractiveQueryService 
{
	//Generate the total order quantity per product
	public HashMap<String,Integer> getTotalQuantityAll(ArrayList<OrderEvent> orders, ArrayList<ProductEvent> products)
	{
		HashMap<Long,Integer> result = new HashMap<Long,Integer>();
		HashMap<String,Integer> values = new HashMap<String,Integer>();
		for(OrderEvent order:orders)
		{
			if(result.get(order.getId())==null)
			{
				result.put(order.getId(), order.getQuantity());
			}
			else
			{
				Integer value = result.get(order.getId()) + order.getQuantity();
				result.replace(order.getId(), value);
			}
		}
		for(Long id : result.keySet())
		{
			for(ProductEvent product:products)
			{
				if(product.getId() == id)
				{
					values.put(product.getName(), result.get(id));
					break;
				}
			}
		}	
        return values;
    }
	//Look up a list of product id for a customer
	public List<Integer> getProducts(Long customerId, ArrayList<OrderEvent> orders) {
		List<Integer> products = new ArrayList<Integer>();
		for(OrderEvent order:orders)
		{
			if(order.getCustomerId() == customerId)
			{
				products.add(order.getProductId().intValue());
			}
		}
        return products;
    }
	//Look up a list of product id and product price
	public HashMap<Integer,Double> getValues(List<Integer> keys, ArrayList<ProductEvent> products) 
	{	
		HashMap<Integer,Double> prices = new HashMap<Integer,Double>();
		for(Integer key:keys)
		{
			for(ProductEvent product:products)
			{
				if(product.getId().intValue() == key)
				{
					prices.put(product.getId().intValue(), product.getPrice());
					break;
				}
			}
		}
	    return prices;
	}
}
