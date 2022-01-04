package Model;

import java.io.File;
import java.sql.Blob;
import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

public class Item {

	private String id;
	private String userId;
	private String name;
	private double price;
	private String description;
	private Date date;
	private String category;
	private boolean status;
	private Blob image;

	public Item(String userId, String name, double price, String description, String category,Blob image) {
		this.id = UUID.randomUUID().toString();
		this.userId = userId;
		this.name = name;
		this.price = price;
		this.description = description;
		this.category = category;
		this.date = Date.valueOf(LocalDate.now());
		this.status = true;
		this.image=image;
	}

	public Item(String id, String name, String description, double price, boolean status, String userId,
			String category, Date date,Blob image) {
		this.id = id;
		this.userId = userId;
		this.name = name;
		this.price = price;
		this.description = description;
		this.status = status;
		this.category = category;
		this.date = date;
		this.image=image;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isActive() {
		return status;
	}

	public void setActive(boolean status) {
		this.status = status;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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
	
	public Blob getImage() {
		return image;
	}

	public void setImage(Blob image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", price=" + price + ", description=" + description + ", category="
				+ category + ", active=" + status + ", date=" + date + "]";
	}

}
