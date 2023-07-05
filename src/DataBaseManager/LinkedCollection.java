package DataBaseManager;

import InventoryModel.Product;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableArrayBase;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;

public abstract class LinkedCollection<T extends LinkedObject> extends ObservableListWrapper<T> {

    protected TableMirror table;
    public LinkedCollection(DBManager manager, String name, RelVar relvar) throws Exception {
        super(new ArrayList<T>());
        this.table = new TableMirror(manager, name, relvar);
    }
    public LinkedCollection(TableMirror table) throws Exception {
        super(new ArrayList<T>());
        this.table = table;
    }

    public boolean add(T object) {
        if(object != null){
            super.add(object);
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

    public void fill() throws Exception {
        table.fill();
        for(RowMirror row : table.getRows()){
            this.add(row);
        }
    }

    public RowMirror contains(RowMirror row){
        return table.contains(row);
    }


}