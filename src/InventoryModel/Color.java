package InventoryModel;

import DataBaseManager.*;

import java.util.ArrayList;

public class Color extends LinkedObject {
    Int_Value pk = Value.create(0);
    String_Value color = Value.create("");
    public Color(RowMirror record) throws Exception {
        super(record);
        defineBind();
        link();
    }

    public Color(RelVar relvar, ArrayList<Value> values) throws Exception {
        super(relvar, values);
        defineBind();
        link();
    }

    public Color(RelVar relvar, Value... values) throws Exception {
        super(relvar, values);
        defineBind();
        link();
    }
    @Override
    public void defineBind(){
        bind("ID_COLOR_PRODUCTO", this.pk);
        bind("COLOR", this.color);
    }

    public String getColor(){
        return this.color.get_value();
    }

    public int getCode(){
        return this.pk.get_value();
    }
}
