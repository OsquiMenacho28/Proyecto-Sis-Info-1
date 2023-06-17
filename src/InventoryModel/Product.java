package InventoryModel;
import DataBaseManager.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class Product extends LinkedObject {


	protected Int_Value code;
	protected Int_Value quantity;
	protected String_Value name;
	protected String_Value description;
	protected String_Value color;
	protected String_Value brand;
	protected String_Value category;
	protected Float_Value price;
	
	
	public Product(int code, int quantity, String name, String description, String color, String brand, String category, float price) throws Exception {
		super(new RowMirror(Inventory.productRV, new ArrayList< Value >(
				Arrays.asList(
						Value.create(code),Value.create(name),
						Value.create(description), Value.create(color),
						Value.create(brand), Value.create(category),
						Value.create(price)
				)
		)));
	}

	public Product(RowMirror record) throws Exception {
		super(record);
	}

	@Override
	protected void downSync() {
		this.code = ((Int_Value)record.get_value("code")).get_value();
		this.quantity = ((Int_Value)record.get_value("quantity")).get_value();
		this.name = ((String_Value)record.get_value("name")).get_value();
		this.color = ((String_Value)record.get_value("color")).get_value();
		this.brand = ((String_Value)record.get_value("brand")).get_value();
		this.category = ((String_Value)record.get_value("category")).get_value();
		this.price =  ((Float_Value)record.get_value("price")).get_value();
	}

	@Override
	protected void upSync() {

	}

	public int getCode() {
		return code;
	}
	public void setCode(int code) throws SQLException {
		this.code = code;
		if(linked) record.edit("code", code);
	}

	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) throws SQLException {
		if(quantity > 0){
			this.quantity = quantity;
			if(linked) record.edit("quantity", quantity);
		}
		else{
			this.quantity = 0;
			if(linked) record.edit("quantity", 0);
		}
	}

	public String getName() {
		return name;
	}
	public void setName(String name) throws SQLException {
		this.name = name;
		if(linked) record.edit("name", name);
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) throws SQLException {
		this.description = description;
		if(linked) record.edit("description", description);
	}

	public String getColor() {
		return color;
	}
	public void setColor(String color) throws SQLException {
		this.color = color;
		if(linked) record.edit("color", color);
	}

	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) throws SQLException {
		this.brand = brand;
		if(linked) record.edit("brand", brand);
	}

	public String getCategory() {
		return category;
	}
	public void setCategory(String category) throws SQLException {
		this.category = category;
		if(linked) record.edit("category", category);
	}

	public float getPrice() {
		return price;
	}
	public void setPrice(int price) throws SQLException {
		this.price = price;
		if(linked) record.edit("price", price);
	}
}
