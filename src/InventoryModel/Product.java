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
		super(Inventory.productRV,
									Value.create(code),
									Value.create(name),
									Value.create(description),
									Value.create(color),
									Value.create(brand),
									Value.create(category),
									Value.create(price));
		defineBind();
		link();
	}

	public Product(RowMirror record) throws Exception {
		super(record);
		defineBind();
		link();
	}

	protected void defineBind(){
		bind("codigo", code);
		bind("cantidad", quantity);
		bind("nombre", name);
		bind("desrcipcion", description);
		bind("color", color);
		bind("marca", brand);
		bind("categoria", category);
		bind("precio", price);
	}
	public Integer getCode() {
		return code.get_value();
	}
	public void setCode(Integer code) throws SQLException {
		set(this.code,Value.create(code));
	}

	public Integer getQuantity() {
		return quantity.get_value();
	}
	public void setQuantity(Integer quantity) throws SQLException {
		if(quantity > 0){
			 set(this.quantity, Value.create(quantity));
		}
		else{
			set(this.quantity, Value.create(0));
		}
	}

	public String getName() {
		return name.get_value();
	}
	public void setName(String name) throws SQLException {
		set(this.name, Value.create(name));
	}

	public String getDescription() {
		return description.get_value();
	}
	public void setDescription(String description) throws SQLException {
		set(this.description, Value.create(description));
	}

	public String getColor() {
		return color.get_value();
	}
	public void setColor(String color) throws SQLException {
		set(this.color, Value.create(color));
	}

	public String getBrand() {
		return brand.get_value();
	}
	public void setBrand(String brand) throws SQLException {
		set(this.brand, Value.create(brand));
	}

	public String getCategory() {
		return category.get_value();
	}
	public void setCategory(String category) throws SQLException {
		set(this.category, Value.create(category));
	}

	public float getPrice() {
		return price.get_value();
	}
	public void setPrice(int price) throws SQLException {
		set(this.price, Value.create(price));
	}
}
