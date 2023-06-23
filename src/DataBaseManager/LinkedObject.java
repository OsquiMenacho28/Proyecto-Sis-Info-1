package DataBaseManager;

import InventoryModel.Inventory;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class LinkedObject extends RowMirror{

    protected Boolean linked;
    private HashMap<Value, String> bindDefinition;
    private HashMap<String, Value> bindState;
    public LinkedObject(RowMirror record) throws Exception {
        super(record.get_relvar(), record.getValues());
        if(record.isActive()){
            this.activate();
        }
        this.linked = false;
        clearBindings();
    }

    public LinkedObject(RelVar relvar, ArrayList<Value> values) throws Exception {
        super(relvar, values);
        this.linked = false;
        clearBindings();
    }

    public LinkedObject(RelVar relvar, Value... values) throws Exception {
       super(relvar, values);
       this.linked = false;
       clearBindings();
    }


    protected void bind(String relvar_column, Value atribute){
        atribute = get_value(relvar_column);
        if(bindDefinition.containsKey(atribute)){
            String last_column = bindDefinition.get(atribute);
            bindState.put(last_column, null);
        }
        if(!bindState.get(relvar_column).equals(null)){
            Value last_value = bindState.get(relvar_column);
            bindDefinition.remove(last_value);
        }
        bindDefinition.put(atribute, relvar_column);
        bindState.put(relvar_column, atribute);
    }

    protected void bind(ArrayList<String> columns, Value... values) throws Exception {
        if(columns.size() == values.length){
            for(int i = 0; i < values.length; i++){
                bind(columns.get(i), values[i]);
            }
        }
        else{
            throw new Exception("Not the same number of columns and atributes");
        }
    }

    private void clearBindings(){
        for(String atribute : record.keySet()){
            bindState.put(atribute, null);
        }
    }

    private boolean checkBindings(){
        Boolean s = true;
        for(Value f: bindState.values()){
            if(f.equals(null)){
                s =false;
                break;
            }
        }
        return s;
    }

    public void link(TableMirror table) throws Exception {
        if(checkBindings()){
            if(this.isActive()){

            }
            else{

            }
            this.linked = true;
        }
    }

    public void set(Value atribute, Value newValue) throws SQLException {
        if(this.bindDefinition.keySet().contains(atribute)){
            this.edit(bindDefinition.get(atribute), newValue);
        }
    }

    public void unlink(){
        this.linked = false;
    }

    @Override
    public void deactivate(){
        this.linked = false;
        this.active = false;
    }


}
