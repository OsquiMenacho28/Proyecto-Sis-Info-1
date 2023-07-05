package SalesModel;

import DataBaseManager.DataType;
import DataBaseManager.RelVar;
import InventoryModel.Inventory;
import application.FlowController.SesionAtCl;
import application.Interface.POS.POSOpen;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class POSsesion {

    public static final RelVar saleRV;

    static {
        try {
            saleRV = new RelVar(
                    new ArrayList<String>(Arrays.asList("", "")),
                    new ArrayList<DataType>(Arrays.asList(DataType.INTEGER_TYPE, DataType.STRING_TYPE)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Float OpeningCount;

    private Float ClosingCount;
    private LocalDateTime beginning;
    private LocalDateTime ending;
    private Cart cart;
    private ArrayList<Sale> sales;
    private POSOpen POSOpen;
    private SesionAtCl sesion;

    public POSsesion(Float OpeningCount, LocalDateTime beginning, LocalDateTime ending, POSOpen POSOpen){
        this.OpeningCount = OpeningCount;
        this.beginning = beginning;
        this.ending = ending;
        this.cart = new Cart(this);
        this.sales = new ArrayList<Sale>();
        this.POSOpen = POSOpen;
        this.sesion = POSOpen.getSesion();
    }
    public POSsesion(Float OpeningCount, POSOpen POSOpen){
        this(OpeningCount,LocalDateTime.now(), null, POSOpen);
    }

    public POSsesion(POSOpen POSOpen){
        this(0f, POSOpen);
    }

    public void end(Float ClosingCount){
        ending = LocalDateTime.now();
    }

    public Cart newCart(){
        this.cart = new Cart(this);
        return this.cart;
    }
    public void commitSale(Client c) throws Exception {
        if(cart.isNotEmpty()){
            Sale sale = new Sale(c, cart, sesion.getUser());
            sales.add(sale);
            newCart();
        }
    }

    public void clearCart() throws Exception {
        cart.clear();
    }

    public POSOpen getPOSOpen(){
        return POSOpen;
    }
}
