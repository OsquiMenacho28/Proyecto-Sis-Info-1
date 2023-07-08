package InventoryModel;

import DataBaseManager.*;

import java.util.ArrayList;

public class Brand extends LinkedObject {

    Int_Value pk = Value.create(0);
    String_Value brand = Value.create("");

    public Brand(RowMirror record) throws Exception {
        super(record);
        defineBind();
        link();
    }

    public Brand(RelVar relvar, ArrayList<Value> values) throws Exception {
        super(relvar, values);
        defineBind();
        link();
    }

    public Brand(RelVar relvar, Value... values) throws Exception {
        super(relvar, values);
        defineBind();
        link();
    }
    @Override
    public void defineBind(){
        bind("ID_MARCA", pk);
        bind("MARCA", brand);
    }

    public String getBrand(){
        return this.brand.get_value();
    }

    public int getCode(){
        return this.pk.get_value();
    }
}
