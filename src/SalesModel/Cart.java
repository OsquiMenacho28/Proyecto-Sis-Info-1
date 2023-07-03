package SalesModel;

import InventoryModel.Product.AddedProduct;
import InventoryModel.Product;
import com.sun.javafx.collections.ObservableListWrapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class Cart extends ObservableListWrapper<AddedProduct> {
    private Float cartTotal;
    private POSsesion sesion;
    public Cart(POSsesion sesion){
        super(new ArrayList<AddedProduct>());
        this.cartTotal = 0f;
        this.sesion = sesion;
    }

    public Cart(POSsesion sesion, ArrayList<AddedProduct> products){
        super(products);
        this.cartTotal = 0f;
        this.sesion = sesion;
    }

    public void add(Product product, int cant) throws Exception {
        this.add(product.addToCart(cant, this));
    }

    public float getTotalPrice(){
        float sum = 0f;
        for(AddedProduct product : this){
            sum += product.getPrice();
        }
        return sum;
    }

    public void clear() {
        for(AddedProduct product : this){
            try {
                product.backToInventory();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            this.remove(product);
        }
    }

    public boolean isNotEmpty(){
        return !this.isEmpty();
    }
}
