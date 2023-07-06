package DataBaseManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class LinkedCollection<T extends LinkedObject> {

    protected TableMirror table;
    public ObservableList <T> collection;
    public LinkedCollection(DBManager manager, String name, RelVar relvar) throws Exception {
        collection = FXCollections.observableArrayList();
        this.table = new TableMirror(manager, name, relvar);
    }
    public LinkedCollection(TableMirror table) throws Exception {
        collection = FXCollections.observableArrayList();
        this.table = table;
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

    public abstract boolean add(RowMirror row) throws Exception;
    public abstract boolean remove(RowMirror row) throws Exception;
    public boolean remove(LinkedObject row) throws Exception {
        if(row != null){
            this.remove(row);
            row.deactivate();
            return true;
        }
        else{
            return false;
        }
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
        table.fill();
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

    public int getNewPK(){
        int i = 0;
        for(RowMirror row : table.getRows()){
            if(row.get_pk_val() > i){
                i = row.get_pk_val();
            }
        }
        return i+1;
    }

}
