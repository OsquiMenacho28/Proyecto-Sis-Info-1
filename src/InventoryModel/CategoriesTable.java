package InventoryModel;

import DataBaseManager.DBManager;
import DataBaseManager.LinkedCollection;
import DataBaseManager.RowMirror;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CategoriesTable extends LinkedCollection<Category> {
    public CategoriesTable(DBManager manager) throws Exception {
        super(manager, "categoria_producto");
        fill();
    }

    @Override
    public boolean add(RowMirror row) throws Exception {
        Category cat = new Category(row);
        return super.add(cat);
    }

    @Override
    public boolean remove(RowMirror row) throws Exception {
        Category cat = this.getWithPK(row.get_pk_val());
        return super.remove(cat);
    }

    public Category getCategory(String color){
        for(Category colorO : this.getCollection()){
            if(colorO.getCategory().equals(color))
                return colorO;
        }
        return null;
    }

    public ObservableList<Category> getCategories(){
        return super.getCollection();
    }
}
