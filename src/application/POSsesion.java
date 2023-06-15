package application;

import javafx.collections.ObservableList;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class POSsesion {

    private Float OpeningCount;

    private Float ClosingCount;
    private LocalDateTime beginning;
    private LocalDateTime ending;
    private Float sum;
    private ArrayList<Sale> sales;
    private POSOpen POSOpen;

    public POSsesion(Float OpeningCount, LocalDateTime beginning, LocalDateTime ending, POSOpen POSOpen){
        this.OpeningCount = OpeningCount;
        this.beginning = beginning;
        this.ending = ending;
        this.sum = 0f;
        this.sales = new ArrayList<Sale>();
        this.POSOpen = POSOpen;
    }
    public POSsesion(Float OpeningCount, POSOpen POSOpen){
        this(OpeningCount,LocalDateTime.now(), null, POSOpen);
        this.OpeningCount = OpeningCount;
    }

    public POSsesion(POSOpen POSOpen){
        this(0f, POSOpen);
    }

    public void end(Float ClosingCount){
        ending = LocalDateTime.now();
    }


    public void addSale(Client c, ObservableList<AddedProduct> cart) throws IOException {
        Sale aux = new Sale(c.getName(), c.getNIT(), cart);
        sum += aux.getMonto();
        sales.add(aux);
        POSOpen.clearCart();
    }
}
