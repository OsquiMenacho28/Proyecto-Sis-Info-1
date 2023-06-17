package InventoryModel;

import DataBaseManager.DataType;
import DataBaseManager.RelVar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Inventory {
    public static final RelVar productRV;

    static {
        try {
            productRV = new RelVar(new ArrayList<String>(Arrays.asList("code","quantity", "name", "description", "color", "brand", "category", "price")),
                    new ArrayList<DataType>(Arrays.asList(DataType.INTEGER_TYPE, DataType.INTEGER_TYPE, DataType.STRING_TYPE, DataType.STRING_TYPE,
                                                          DataType.STRING_TYPE, DataType.STRING_TYPE, DataType.STRING_TYPE, DataType.FLOAT_TYPE)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public Inventory() throws Exception {

    }
}
