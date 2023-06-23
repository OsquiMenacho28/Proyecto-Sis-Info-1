package DataBaseManager;

import InventoryModel.Inventory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class LinkedObject {
    protected RelVar relvar;
    protected RowMirror record;
    protected Boolean linked;
    private HashMap<Value, String> bindDefinition;
    private HashMap<String, Value> linkedAtributes;
    private HashMap<String, Boolean> bindState;
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

    public Value getValue(String atribute){
        if(relvar.get_columns().contains(atribute)){
            return this.record.get_value(atribute);
        }
        else{

        }
    }

    public void setValue(String atribute, Value newValue){
        this.record.edit(atribute, newValue.);
    }

    protected void bind(Value atribute, String relvar_column){
        bindDefinition.put(atribute, relvar_column);
        bindState.put(relvar_column, true);
    }

    private void clearBindings(){
        for(String atribute : linkedAtributes.keySet()){
            bindState.put(atribute, false);
        }
    }

    private boolean checkBindings(){
        Boolean s = true;
        for(Boolean f: bindState.values()){
            s = s&&f;
        }
        return s;
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
