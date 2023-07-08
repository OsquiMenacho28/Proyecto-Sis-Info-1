package InventoryModel;

import DataBaseManager.*;

import java.util.ArrayList;

public class Category extends LinkedObject {

    Int_Value pk = Value.create(0);
    String_Value category = Value.create("");

    public Category(RowMirror record) throws Exception {
        super(record);
        defineBind();
        link();
    }

    public Category(RelVar relvar, ArrayList<Value> values) throws Exception {
        super(relvar, values);
        defineBind();
        link();
    }

    public Category(RelVar relvar, Value... values) throws Exception {
        super(relvar, values);
        defineBind();
        link();
    }
    @Override
    public void defineBind(){
        bind("ID_CATEGORIA_PRODUCTO", pk);
        bind("CATEGORIA", category);
    }

    public String getCategory(){
        return this.category.get_value();
    }

    public int getCode(){
        return this.pk.get_value();
    }
}
