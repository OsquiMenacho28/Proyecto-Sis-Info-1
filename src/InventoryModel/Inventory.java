package InventoryModel;

import DataBaseManager.*;
import SalesModel.Sale;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Inventory extends LinkedCollection {
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

    private ObservableList<RowMirror> inventory;
    public Inventory(DBManager manager, String table) throws Exception {
        super(manager.getTable(table));
    }

    public void materialIntake(Product product, int cant) throws Exception {
        if(this.inventory.contains(product)){
            product.addUnits(this, cant);
        }
    }

    public void materialWithdrawal(Product product, int cant) throws Exception {
        if(this.inventory.contains(product)){
            product.retireUnits(this, cant);
        }
    }

    public ObservableList<RowMirror> getInventory(){
        return inventory;
    }

}
