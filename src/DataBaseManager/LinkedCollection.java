package DataBaseManager;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public abstract class LinkedCollection<T extends LinkedObject> {

    protected TableMirror table;
    private ObservableList <T> collection;

    public LinkedCollection(TableMirror table) throws Exception {
        this.table = table;
        this.collection = FXCollections.observableArrayList();
    }
    public LinkedCollection(DBManager manager, String name) throws Exception {
        this(manager.getTable(name));
    }

    public boolean add(T object) {
        if(object != null){
            collection.add(object);
            try {
                object.link(table);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return true;
        }
        else{
            return false;
        }
    }

    public boolean remove(T row) throws Exception {
        if(row != null){
            collection.remove(row);
            row.deactivate();
            return true;
        }
        else{
            return false;
        }
    }

    public abstract boolean add(RowMirror row) throws Exception;
    public abstract boolean remove(RowMirror row) throws Exception;

    public void addListener(ListChangeListener<T> listener){
        collection.addListener(listener);
    }

    public void removeListener(ListChangeListener<T> listener){
        collection.removeListener(listener);
    }
    public T getWithPK(int pk){
        for(T object: collection){
            if(object.getID() == pk){
                return object;
            }
        }
        return null;
    }

    public void fill() throws Exception {
        for(RowMirror row : table.getRows()){
            this.add(row);
        }
    }

    public RelVar getRelVar(){
        return this.table.getRelVar();
    }

    public RowMirror contains(RowMirror row){
        return table.contains(row);
    }
    public T contains(T row){
        if(collection.contains(row)){
            return row;
        }
        else{
            return null;
        }
    }

    public int getNewPK(){
        int i = 0;
        for(RowMirror row : table.getRows()){
            if(row.get_pk_val() > i){
                i = row.get_pk_val();
            }
        }
        return i+1;
    }

    public ObservableList<T> getCollection(){
        return collection;
    }

}
