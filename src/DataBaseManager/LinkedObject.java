package DataBaseManager;

import InventoryModel.Inventory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class LinkedObject {
    protected RelVar relvar;
    protected RowMirror record;
    protected Boolean linked;
    private HashMap<String, Value> linkedAtributes;
    private HashMap<String, Boolean> bindings;

    public LinkedObject(RowMirror record) throws Exception {
        this.relvar = record.get_relvar();
        setRecord(record);
        this.linkedAtributes = record.getRecord();
        clearBindings();
        this.linked = false;
        link();
    }

    public LinkedObject(RelVar relvar, ArrayList<Value> values) throws Exception {
        if(values.size() == relvar.get_size()){
            this.relvar =  relvar;
            for(int i = 0; i < values.size(); i++){
                if(relvar.get_type(i) == values.get(i).get_type()) {
                    linkedAtributes.put(relvar.get_columns().get(i), values.get(i));
                }
                else throw new Exception("Not compatible");
            }
            setRecord(new RowMirror(relvar, new ArrayList<Value>(linkedAtributes.values())));
            clearBindings();
            this.linked = false;
        }
        else throw new Exception("Not compatible");
    }

    public LinkedObject(RelVar relVar, Value... values) throws Exception {
        if(values.length == relVar.get_size()){
            this.relvar =  relvar;
            for(int i = 0; i < values.length; i++){
                if(relvar.get_type(i) == values[i].get_type()) {
                    linkedAtributes.put(relvar.get_columns().get(i), values[i]);
                }
                else throw new Exception("Not compatible");
            }
            setRecord(new RowMirror(relVar, new ArrayList<Value>(linkedAtributes.values())));
            clearBindings();
            this.linked = false;
        }
        else throw new Exception("Not compatible");
    }

    public void bind(Value atribute, String relvar_column){
        atribute = record.get_value(relvar_column);
        bindings.put(relvar_column, true);
    }

    public void clearBindings(){
        for(String atribute : linkedAtributes.keySet()){
            bindings.put(atribute, false);
        }
    }

    protected void setRecord(RowMirror record) throws Exception {
        if(!record.equals(null)){
            if(record.get_relvar().is_compatible(relvar)){
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
    protected void sync() throws Exception {
        if(linked){
            downSync();
        }
        else{
            throw new Exception("Not linked");
        }
    }

    private void createField(String key, Value value){
        try {
            Field field = getClass().getDeclaredField(key);
            field.setAccessible(true);
            field.set(this, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    protected abstract void downSync();
    protected abstract void upSync();

}
