package DataBaseManager;

import InventoryModel.Inventory;
import javafx.beans.value.ChangeListener;


import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class LinkedObject extends RowMirror{

    protected Boolean linked;
    private ChangeListener listener;
    private HashMap<Value, String> bindDefinition;
    private HashMap<String, Value> bindState;
    public LinkedObject(RowMirror record) throws Exception {
        super(record.get_relvar(), record.getValues());
        if(record.isActive()){
            this.activate();
            record.add();
        }
        this.bindDefinition = new HashMap<Value, String>();
        this.bindState = new HashMap<String, Value>();
        this.linked = false;
        clearBindings();
    }

    public LinkedObject(RelVar relvar, ArrayList<Value> values) throws Exception {
        super(relvar, values);
        this.bindDefinition = new HashMap<Value, String>();
        this.bindState = new HashMap<String, Value>();
        this.linked = false;
        clearBindings();
    }

    public LinkedObject(RelVar relvar, Value... values) throws Exception {
       super(relvar, values);
        this.bindDefinition = new HashMap<Value, String>();
        this.bindState = new HashMap<String, Value>();
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

    public void link() throws Exception {
        if(checkBindings()){
            if(this.isActive()){
                for(Value val : bindDefinition.keySet()){
                    val.set_value(this.get_value(bindDefinition.get(val)));
                }
            }
            else{
                if(!this.table.equals(null)) {
                    this.table.add(this);
                }
                else{
                    throw new Exception("No table to link");
                }
            }
            this.linked = true;
        }
    }

    public void link(TableMirror table) throws Exception {
        if(checkBindings()){
            this.setTable(table);
            this.table.add(this);
            this.linked = true;
        }
    }

    public void set(Value atribute, Value newValue) throws SQLException {
        if(this.bindDefinition.keySet().contains(atribute)){
            atribute.set_value(newValue);
            this.edit(bindDefinition.get(atribute), newValue);
            this.fireChangeEvent();
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

    protected abstract void defineBind();

    private void fireChangeEvent(){
        if(this.listener != null){
            listener.changed(null,null, null);
        }
    }

    public void addListener(ChangeListener listener){
        this.listener = listener;
    }

}
