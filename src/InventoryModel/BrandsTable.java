package InventoryModel;

import DataBaseManager.DBManager;
import DataBaseManager.LinkedCollection;
import DataBaseManager.RowMirror;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BrandsTable extends LinkedCollection<Brand> {

    ObservableList<String> link = FXCollections.observableArrayList();

    public BrandsTable(DBManager manager) throws Exception {
        super(manager, "marca_producto");
    }

    @Override
    public boolean add(RowMirror row) throws Exception {
        Brand marca = new Brand(row);
        link.add(marca.getBrand());
        return super.add(marca);
    }

    @Override
    public boolean remove(RowMirror row) throws Exception {
        Brand marca = this.getWithPK(row.get_pk_val());
        link.remove(marca.getBrand());
        return super.remove(marca);
    }

    public Brand getBrand(String color){
        for(Brand colorO : collection){
            if(colorO.getBrand().equals(color))
                return colorO;
        }
        return null;
    }

    public ObservableList<String> getBrands(){
        return link;
    }
}
