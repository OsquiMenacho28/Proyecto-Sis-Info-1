package InventoryModel;

import DataBaseManager.DBManager;
import DataBaseManager.LinkedCollection;
import DataBaseManager.RowMirror;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CategoriesTable extends LinkedCollection<Category> {
    ObservableList<String> link = FXCollections.observableArrayList();

    public CategoriesTable(DBManager manager) throws Exception {
        super(manager, "categoria_producto");
    }

    @Override
    public boolean add(RowMirror row) throws Exception {
        Category cat = new Category(row);
        link.add(cat.getCategory());
        return super.add(cat);
    }

    @Override
    public boolean remove(RowMirror row) throws Exception {
        Category cat = this.getWithPK(row.get_pk_val());
        link.remove(cat.getCategory());
        return super.remove(cat);
    }

    public Category getCategory(String color){
        for(Category colorO : this){
            if(colorO.getCategory().equals(color))
                return colorO;
        }
        return null;
    }

    public ObservableList<String> getCategories(){
        return link;
    }
}
