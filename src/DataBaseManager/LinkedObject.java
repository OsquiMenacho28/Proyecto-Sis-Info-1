package DataBaseManager;

import InventoryModel.Inventory;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ObservableValueBase;


import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class LinkedObject extends ObservableValueBase<Value> {

    protected Boolean linked;
    private RowMirror tuple;
    private HashMap<Value, String> bindDefinition;
    private HashMap<String, Value> bindState;
    private ArrayList<ChangeListener<Value>> changeListeners;
    public LinkedObject(RowMirror record) throws Exception {
        this.tuple =record;
        if(record.isActive()){
            record.activate();
            record.add();
        }
        this.bindDefinition = new HashMap<Value, String>();
        this.bindState = new HashMap<String, Value>();
        this.changeListeners = new ArrayList<ChangeListener<Value>>();
        this.linked = false;
        clearBindings();
    }

    public LinkedObject(RelVar relvar, ArrayList<Value> values) throws Exception {
        this.tuple = new RowMirror(relvar, values);
        this.bindDefinition = new HashMap<Value, String>();
        this.bindState = new HashMap<String, Value>();
        this.linked = false;
        clearBindings();
    }

    public LinkedObject(RelVar relvar, Value... values) throws Exception {
        this.tuple = new RowMirror(relvar, values);
        this.bindDefinition = new HashMap<Value, String>();
        this.bindState = new HashMap<String, Value>();
       this.linked = false;
       clearBindings();
    }


    protected void bind(String relvar_column, Value atribute){
        atribute = getValue(relvar_column);
        if(bindDefinition.containsKey(atribute)){
            String last_column = bindDefinition.get(atribute);
            bindState.put(last_column, null);
        }
        if(bindState.get(relvar_column) != null){
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
        for(String atribute : tuple.getColumns()){
            bindState.put(atribute, null);
        }
    }

    private boolean checkBindings(){
        boolean s = true;
        for(String f: bindState.keySet()){
            if(bindState.get(f) != null){
                s =false;
                break;
            }
        }
        return s;
    }

    public void link() throws Exception {
        if(checkBindings()){
            if(this.tuple.isActive()){
                for(Value val : bindDefinition.keySet()){
                    val.set_value(this.getValue(bindDefinition.get(val)));
                }
            }
            else{
                if(this.tuple.getTable() != null) {
                    this.tuple.add();
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
            this.tuple.add(table);
            this.linked = true;
        }
    }

    public void set(Value atribute, Value newValue) throws SQLException {
        if(this.bindDefinition.containsKey(atribute)){
            atribute.set_value(newValue);
            this.tuple.edit(bindDefinition.get(atribute), newValue);
            this.fireValueChangedEvent();
        }
    }
    public Value getValue(String column){
        return bindState.get(column);
    }

    public Integer getID(){return tuple.get_pk_val();}

    public void unlink(){
        this.linked = false;
    }

    public void deactivate() throws SQLException {
        this.linked = false;
        this.tuple.deactivate();
    }

    public boolean isLinked(){
        return linked;
    }

    @Override
    public Value getValue() {
        return null;
    }
}
