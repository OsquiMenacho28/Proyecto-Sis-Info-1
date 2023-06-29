package SalesModel;

import InventoryModel.Product.AddedProduct;
import InventoryModel.Product;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class Cart {
    private ArrayList<AddedProduct> productList;
    private Float cartTotal;
    private POSsesion sesion;
    public Cart(POSsesion sesion){
        this.productList = new ArrayList<AddedProduct>();
        this.cartTotal = 0f;
        this.sesion = sesion;
    }

    public Cart(POSsesion sesion, ArrayList<AddedProduct> products){
        this.cartTotal = 0f;
        this.sesion = sesion;

        add(products);
    }

    public void add(Product product, int cant) throws Exception {
        productList.add(product.addToCart(cant, this));
    }

    public void add(AddedProduct product){
        productList.add(product);
    }

    public void add(ArrayList<AddedProduct> products){
        productList.addAll(products);
    }

    public void remove(AddedProduct product){
        productList.remove(product);
    }

    public void remove(int i){
        productList.remove(i);
    }

    public float getTotalPrice(){
        float sum = 0f;
        for(AddedProduct product : productList){
            sum += product.getPrice();
        }
        return sum;
    }

    public void clear() throws Exception {
        for(AddedProduct product : productList){
            product.backToInventory();
            productList.remove(product);
        }
    }

    public boolean isNotEmpty(){
        return !productList.isEmpty();
    }
}
