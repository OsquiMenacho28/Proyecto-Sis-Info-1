package InventoryModel;

import DataBaseManager.*;
import SalesModel.Sale;
import application.Interface.POS.ItemIcon;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Inventory extends LinkedCollection<Product> {
    public static final RelVar productRV;

    static {
        try {
            productRV = new RelVar(
                    new ArrayList<String>(Arrays.asList("code","quantity", "name", "description", "color", "brand", "category", "price")),
                    new ArrayList<DataType>(Arrays.asList(DataType.INTEGER_TYPE, DataType.INTEGER_TYPE, DataType.STRING_TYPE, DataType.STRING_TYPE,
                                                          DataType.STRING_TYPE, DataType.STRING_TYPE, DataType.STRING_TYPE, DataType.FLOAT_TYPE)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private ObservableList<ItemIcon> icons;

    public Inventory(DBManager manager, String table) throws Exception {
        this(manager.getTable(table));
    }

    public Inventory(TableMirror table) throws Exception {
        super(table);
        this.icons = calculateIcons();
        fill();

        this.addListener((ListChangeListener<Product>) c -> {
            while (c.next()) {
                if (c.wasRemoved()) {
                    for(Product product : c.getRemoved()){
                        icons.remove(product.getIcon());
                    }
                }
                else if(c.wasAdded()){
                    for(Product product : c.getAddedSubList()){
                        icons.add(product.getIcon());
                    }
                }
            }
        });
    }

    private Product getProductWithId(int code) {
        int index;
        for(index = 0; index < this.size(); index++) {
            Product product = ((Product)this.get(index));
            if (product.getCode() == code) {
                return product;
            }
        }
        return null;
    }

    private ObservableList<ItemIcon> calculateIcons(){
        ObservableList<ItemIcon> icons = FXCollections.observableArrayList();
        for(Product product : this){
            icons.add(product.getIcon());
        }
        return icons;
    }

    public ObservableList<ItemIcon> getIcons(){
        return this.icons;
    }

    @Override
    public boolean add(RowMirror row) throws Exception {
        return super.add(new Product(this, row));
    }

    @Override
    public boolean remove(RowMirror row) throws Exception {
        Product product = getProductWithId(Integer.valueOf(row.get_pk_val()));
        return super.remove(product);
    }

    public void materialIntake(Product product, int cant) throws Exception {
        if(this.contains(product)){
            product.addUnits(this, cant);
        }
    }

    public void materialWithdrawal(Product product, int cant) throws Exception {
        if(this.contains(product)){
            product.retireUnits(this, cant);
        }
    }
}
