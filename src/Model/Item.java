package Model;

import java.util.UUID;

public class Item {

	private String id ;
	private String userId;
	private String name = "";
	private double price = 0.0;
	private String description = "";

	public Item(String userId, String name, double price, String description) {
		this.id =UUID.randomUUID().toString();
		this.userId=userId;
		this.name = name;
		this.price = price;
		this.description = description;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", price=" + price + "lv, description=" + description + "]";
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUserId() {
		return userId;
	}

}
