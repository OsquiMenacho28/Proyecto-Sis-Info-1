package application;

public class Product {

	protected int id;
	protected String name;
	protected float price;
	protected int stock;
	
	public String brand;
	public String color;
	public String category;
	
	
	public Product(int id, String name, float price, int stock, String brand, String color, String category){
		this.id = id;
		this.name =  name;
		this.price =  price;
		this.stock = stock;
		this.brand = brand;
		this.color = color;
		this.category = category;
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
	 * @return the categrory
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param categrory the categrory to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
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

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	
}
