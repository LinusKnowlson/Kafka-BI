package spring.cloud.kafka.BI.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class QuantitySummary 
{
	//Stream topic - used to contain the data of total order quantity per product
	private String productName;
	private Long quantity;
	
	public QuantitySummary() {
		// TODO Auto-generated constructor stub
	}
	
	public QuantitySummary(String productName, Long quantity) {
		// TODO Auto-generated constructor stub
		this.productName = productName;
		this.quantity = quantity;
	}
	
	public String getProductName() {
		return productName;
	}
	
	public Long getQuantity() {
		return quantity;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Quantity Summary:{" +
        "Product Name='" + productName + '\'' +
        ", Total Quantity Value='" + quantity + '\'' +
        '}';
	}
}
