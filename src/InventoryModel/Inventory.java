package InventoryModel;

import DataBaseManager.*;
import SalesModel.Sale;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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

    public Inventory(DBManager manager, String table) throws Exception {
        super(manager.getTable(table));
    }

    public Inventory(TableMirror table) throws Exception {
        super(table);
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


    @Override
    public boolean add(RowMirror row) throws Exception {
        return super.add(new Product(this, row));
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
