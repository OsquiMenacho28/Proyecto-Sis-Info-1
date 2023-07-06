package InventoryModel;

import DataBaseManager.*;

import java.util.ArrayList;

public class Color extends LinkedObject {
    Int_Value pk;
    String_Value color;
    public Color(RowMirror record) throws Exception {
        super(record);
        bindDefinition();
        link();
    }

    public Color(RelVar relvar, ArrayList<Value> values) throws Exception {
        super(relvar, values);
        bindDefinition();
        link();
    }

    public Color(RelVar relvar, Value... values) throws Exception {
        super(relvar, values);
        bindDefinition();
        link();
    }
    public void bindDefinition(){
        bind("id_color_produ", pk);
        bind("color", color);
    }

    public String getColor(){
        return this.color.to_string();
    }

    public int getCode(){
        return this.pk.get_value();
    }
}
