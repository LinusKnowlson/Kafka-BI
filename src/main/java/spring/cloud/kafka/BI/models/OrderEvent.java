package spring.cloud.kafka.BI.models;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class OrderEvent 
{
	//value object/stream topic for Order event
    private Long id;
    private String supplier;
    private String product;
    private int quantity;
    private Long customerId;
    private Long productId;

    public OrderEvent() {}

    public OrderEvent(String supplier, String product, int quantity,Long customerId, Long productId) {
    	this.supplier = supplier;
        this.product = product;
        this.quantity = quantity;
        this.customerId = customerId;
        this.productId = productId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
    
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", supplier='" + supplier + '\'' +
                ", product='" + product + '\'' +
                ", quantity='" + quantity + '\'' +
                ", customer='" + customerId + '\'' +
                ", productId='" + productId + '\'' +
                '}';
    }
}
