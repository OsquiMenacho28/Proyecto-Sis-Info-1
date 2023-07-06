package InventoryModel;

import DataBaseManager.*;

import java.util.ArrayList;

public class Category extends LinkedObject {

    Int_Value pk;
    String_Value category;

    public Category(RowMirror record) throws Exception {
        super(record);
        bindDefinition();
        link();
    }

    public Category(RelVar relvar, ArrayList<Value> values) throws Exception {
        super(relvar, values);
        bindDefinition();
        link();
    }

    public Category(RelVar relvar, Value... values) throws Exception {
        super(relvar, values);
        bindDefinition();
        link();
    }

    public void bindDefinition(){
        bind("id_categoria_producto", pk);
        bind("categoria", category);
    }

    public String getCategory(){
        return this.category.to_string();
    }

    public int getCode(){
        return this.pk.get_value();
    }
}
