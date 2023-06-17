package DataBaseManager;

import InventoryModel.Inventory;

import java.util.ArrayList;

public abstract class LinkedObject {
    protected RowMirror record;
    protected Boolean linked;
    protected ArrayList<Value>linkedAtributes;
    public LinkedObject(RowMirror record) throws Exception {
        setRecord(record);
        for(String col: record.get_relvar().get_columns()){
            linkedAtributes.add(record.get_value(col));
        }
        this.linked = false;
        link();
    }

    public LinkedObject(RelVar relVar, ArrayList<Value>linkedAtributes) throws Exception {
        this.linkedAtributes = linkedAtributes;

        setRecord(new RowMirror(relVar, linkedAtributes));
        this.linked = false;
    }

    protected void setRecord(RowMirror record) throws Exception {
        if(!record.equals(null)){
            if(record.get_relvar().is_compatible(Inventory.productRV)){
                this.record = record;
                this.linked = false;
            }
            else{
                throw new Exception("Not compatible");
            }
        }
        else{
            throw new Exception("Not compatible");
        }
    }
    protected void sync() throws Exception {
        if(linked){
            downSync();
        }
        else{
            throw new Exception("Not linked");
        }
    }
    protected void link() throws Exception {
        if(!record.equals(null)){
            if(record.isActive()){
                this.linked = true;
            }
        }
        sync();
    }

    protected void unlink(){
        this.linked = false;
    }

    protected abstract void downSync();
    protected abstract void upSync();

}
