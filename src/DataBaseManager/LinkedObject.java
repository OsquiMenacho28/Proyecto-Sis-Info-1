package DataBaseManager;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ObservableValueBase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class LinkedObject implements ObservableValue<Value> {

    protected Boolean linked;
    private RowMirror tuple;
    private HashMap<Value, String> bindDefinition;
    private HashMap<String, Value> bindState;
    private List<ChangeListener<? super Value>> changeListeners;
    private List<InvalidationListener> invalidationListeners;
    public LinkedObject(RowMirror record) throws Exception {
        this.tuple =record;
        this.bindDefinition = new HashMap<Value, String>();
        this.bindState = new HashMap<String, Value>();
        this.changeListeners = new ArrayList<ChangeListener<? super Value>>();
        this.invalidationListeners = new ArrayList<InvalidationListener>();
        this.linked = false;
        clearBindings();
    }

    public LinkedObject(RelVar relvar, ArrayList<Value> values) throws Exception {
        this.tuple = new RowMirror(relvar, values);
        this.bindDefinition = new HashMap<Value, String>();
        this.bindState = new HashMap<String, Value>();
        this.changeListeners = new ArrayList<ChangeListener<? super Value>>();
        this.invalidationListeners = new ArrayList<InvalidationListener>();
        this.linked = false;
        clearBindings();
    }

    public LinkedObject(RelVar relvar, Value... values) throws Exception {
        this.tuple = new RowMirror(relvar, values);
        this.bindDefinition = new HashMap<Value, String>();
        this.bindState = new HashMap<String, Value>();
        this.changeListeners = new ArrayList<ChangeListener<? super Value>>();
        this.invalidationListeners = new ArrayList<InvalidationListener>();
       this.linked = false;
       clearBindings();
    }

    protected abstract void defineBind();

    protected void bind(String relvar_column, Value atribute){
        atribute.set_value(this.getValue(relvar_column).get_value());

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

    public boolean checkBindings(){
        boolean s = true;
        for(String f: bindState.keySet()){
            if(bindState.get(f) == null){
                s =false;
                break;
            }
        }
        return s;
    }

    public void link() throws Exception {
        if(checkBindings()){
            if(!this.tuple.isActive()){
                if(this.tuple.getTable() != null) {

                    this.tuple.add();
                    this.linked = true;
                }
            }
        }
    }

    public void link(TableMirror table) throws Exception {
        if(checkBindings()){
            this.tuple.setTable(table);
            link();
        }
    }

    public void set(Value atribute, Value newValue) throws SQLException {
        if(this.bindDefinition.containsKey(atribute)){
            atribute.set_value(newValue.get_value());
            this.tuple.edit(bindDefinition.get(atribute), newValue);
            this.notifyListeners(atribute, newValue);
        }
    }
    public Value getValue(String column){
        return tuple.get_value(column);
    }

    public Integer getID(){return tuple.get_pk_val();}

    public void unlink(){
        this.linked = false;
    }

    public void deactivate() throws SQLException {
        this.linked = false;
        this.tuple.deactivate();
        tuple.table.remove(tuple);
    }

    public boolean isLinked(){
        return linked;
    }

    public boolean isActive(){
        return tuple.isActive();
    }

    private void notifyListeners(Value oldValue, Value newValue) {
        for (ChangeListener<? super Value> listener : changeListeners) {
            listener.changed(this, oldValue, newValue);
        }

        for (InvalidationListener listener : invalidationListeners) {
            listener.invalidated(this);
        }
    }

    public HashMap<String, Value> getBindState(){
        return bindState;
    }

    public HashMap<Value, String> getBindDefinition(){
        return bindDefinition;
    }

    @Override
    public void addListener(ChangeListener<? super Value> listener) {
        changeListeners.add(listener);
    }

    @Override
    public void removeListener(ChangeListener<? super Value> listener) {
        changeListeners.remove(listener);
    }

    @Override
    public Value getValue() {
        return null;
    }

    @Override
    public void addListener(InvalidationListener listener) {
        invalidationListeners.add(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        invalidationListeners.remove(listener);
    }

    public RowMirror getTuple(){
        return this.tuple;
    }
}
