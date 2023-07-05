package SalesModel;

import InventoryModel.Product.AddedProduct;
import InventoryModel.Product;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.collections.ListChangeListener;
import javafx.scene.control.TableView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class Cart extends ObservableListWrapper<AddedProduct> {
    private Float cartTotal;
    private POSsesion sesion;
    private TableView<AddedProduct> CartList_T;
    public Cart(POSsesion sesion){
        this(sesion, new ArrayList<AddedProduct>());
    }

    public Cart(POSsesion sesion, ArrayList<AddedProduct> products){
        super(products);
        this.cartTotal = 0f;
        this.sesion = sesion;
        this.CartList_T = sesion.getPOSOpen().getCartTable();

        addListener((ListChangeListener<? super AddedProduct>) e -> {
            CartList_T.refresh();
            sesion.getPOSOpen().setTotalLabel();
        });
    }

    public boolean add(Product product, int cant) throws Exception {
        if(product != null){
            super.add(product.addToCart(cant, this));
            getTotalPrice();
            return true;
        }
        else{
            return false;
        }
    }

    public boolean remove(AddedProduct product) throws Exception {
        if(product != null){
            super.remove(product);
            product.backToInventory();
            return true;
        }
        else{
            return false;
        }
    }

    public float getTotalPrice(){
        float sum = 0f;
        for(AddedProduct product : this){
            sum += product.getPrice();
        }
        this.cartTotal = sum;
        return sum;
    }

    public void clear() {
        for(AddedProduct product : this){
            try {
                this.remove(product);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public POSsesion getPOSsesion(){
        return sesion;
    }

    public boolean isNotEmpty(){
        return !this.isEmpty();
    }

    public TableView<AddedProduct> getCartTable(){
        return this.CartList_T;
    }
}
