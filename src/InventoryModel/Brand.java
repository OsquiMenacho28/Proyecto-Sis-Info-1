package InventoryModel;

import DataBaseManager.*;

import java.util.ArrayList;

public class Brand extends LinkedObject {

    Int_Value pk;
    String_Value brand;

    public Brand(RowMirror record) throws Exception {
        super(record);
        bindDefinition();
        link();
    }

    public Brand(RelVar relvar, ArrayList<Value> values) throws Exception {
        super(relvar, values);
        bindDefinition();
        link();
    }

    public Brand(RelVar relvar, Value... values) throws Exception {
        super(relvar, values);
        bindDefinition();
        link();
    }

    public void bindDefinition(){
        bind("id_marca", pk);
        bind("marca", brand);
    }

    public String getBrand(){
        return this.brand.to_string();
    }

    public int getCode(){
        return this.pk.get_value();
    }
}
