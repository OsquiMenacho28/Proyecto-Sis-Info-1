package SalesModel;

import InventoryModel.Product;
import InventoryModel.Product.AddedProduct;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.util.ArrayList;

public class Cart {
    private Float cartTotal;
    private POSsesion sesion;
    private TableView<AddedProduct> CartList_T;
    public ObservableList <AddedProduct> collection;
    public Cart(POSsesion sesion){
        this(sesion, new ArrayList<AddedProduct>());
    }

    public Cart(POSsesion sesion, ArrayList<AddedProduct> products){
        collection = FXCollections.observableArrayList();
        this.cartTotal = 0f;
        this.sesion = sesion;
        this.CartList_T = sesion.getPOSOpen().getCartTable();

        collection.addListener((ListChangeListener<? super AddedProduct>) e -> {
            CartList_T.refresh();
            sesion.getPOSOpen().setTotalLabel();
        });
    }

    public boolean add(Product product, int cant) throws Exception {
        if(product != null){
            collection.add(product.addToCart(cant, this));
            getTotalPrice();
            return true;
        }
        else{
            return false;
        }
    }

    public boolean remove(AddedProduct product) throws Exception {
        if(product != null){
            collection.remove(product);
            product.backToInventory();
            return true;
        }
        else{
            return false;
        }
    }

    public float getTotalPrice(){
        float sum = 0f;
        for(AddedProduct product : this.collection){
            sum += product.getPrice();
        }
        this.cartTotal = sum;
        return sum;
    }

    public void clear() {
        for(AddedProduct product : this.collection){
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
        return !this.collection.isEmpty();
    }

    public TableView<AddedProduct> getCartTable(){
        return this.CartList_T;
    }
}
