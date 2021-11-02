package spring.cloud.kafka.BI.models;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerEvent 
{
	//value object/stream topic for Customer event
	private Long id;
    private String companyName;
    private String address;
    private String country;
    private Long contact;
    
    public CustomerEvent() {}
    
    public CustomerEvent(String companyName, String address, String country, Long contact) 
    {
        this.companyName = companyName;
        this.address = address;
        this.country = country;
        this.contact = contact;
    }
    //accessors and mutators
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    
    public Long getContact() {
		return contact;
	}

	public void setContact(Long contact) {
		this.contact = contact;
	}
	
	//retrieve all the information of customer
    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", address='" + address + '\'' +
                ", country='" + country + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
