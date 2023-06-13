package application;

public class Product {

	protected int code;

	protected int quantity;

	protected String name;
	protected String description;

	public String color;
	public String brand;
	public String category;

	protected float price;
	
	
	public Product(int code, int quantity, String name, String description, String color, String brand, String category, float price){
		this.code = code;
		this.quantity = quantity;
		this.name =  name;
		this.description = description;
		this.color = color;
		this.brand = brand;
		this.category = category;
		this.price =  price;
	}
	
	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the categrory to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public float getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	
}
