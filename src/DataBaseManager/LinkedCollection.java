package DataBaseManager;

import InventoryModel.Product;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableArrayBase;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;

public abstract class LinkedCollection extends ObservableArrayBase {

    protected TableMirror table;
    public LinkedCollection(DBManager manager, String name, RelVar relvar) throws Exception {
        this.table = new TableMirror(manager, name, relvar);
    }
    public LinkedCollection(TableMirror table) throws Exception {
        this.table = table;
    }
    public void add(RowMirror row) throws Exception {
        super.add(row);
        table.add(row);
    }

    public void remove(RowMirror row) throws SQLException {
        this.remove(row);
        table.remove(row);
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
