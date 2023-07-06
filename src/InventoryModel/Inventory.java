package InventoryModel;

import DataBaseManager.*;
import SalesModel.Sale;
import application.Interface.POS.ItemIcon;
import javafx.beans.value.ObservableMapValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Inventory extends LinkedCollection<Product> {
    public static RelVar productRV;
    public static RelVar colorRV;
    public static RelVar brandRV;
    public static RelVar categoryRV;

    public ColorsTable colorsTable;
    public BrandsTable brandsTable;
    public CategoriesTable categoriesTable;
    private ObservableList<ItemIcon> icons;

    public Inventory(DBManager manager) throws Exception {
        super(manager, "producto");
        colorsTable = new ColorsTable(manager);
        brandsTable = new BrandsTable(manager);
        categoriesTable = new CategoriesTable(manager);
        productRV = this.table.getRelVar();
        colorRV = colorsTable.getRelVar();
        brandRV = brandsTable.getRelVar();
        categoryRV = categoriesTable.getRelVar();
    }

    public Inventory(TableMirror table) throws Exception {
        super(table);
        this.icons = calculateIcons();
        fill();

        this.addListener((ListChangeListener<Product>) c -> {
            while (c.next()) {
                if (c.wasRemoved()) {
                    for(Product product : c.getRemoved()){
                        icons.remove(product.getIcon());
                    }
                }
                else if(c.wasAdded()){
                    for(Product product : c.getAddedSubList()){
                        icons.add(product.getIcon());
                    }
                }
            }
        });
    }

    private Product getProductWithId(int code) {
        return super.getWithPK(code);
    }

    private ObservableList<ItemIcon> calculateIcons(){
        ObservableList<ItemIcon> icons = FXCollections.observableArrayList();
        for(Product product : this){
            icons.add(product.getIcon());
        }
        return icons;
    }

    public ObservableList<ItemIcon> getIcons(){
        return this.icons;
    }

    @Override
    public boolean add(RowMirror row) throws Exception {
        return super.add(new Product(this, row));
    }

    @Override
    public boolean remove(RowMirror row) throws Exception {
        Product product = getProductWithId(Integer.valueOf(row.get_pk_val()));
        return super.remove(product);
    }

    public void materialIntake(Product product, int cant) throws Exception {
        if(this.contains(product)){
            product.addUnits(this, cant);
        }
    }

    public void materialWithdrawal(Product product, int cant) throws Exception {
        if(this.contains(product)){
            product.retireUnits(this, cant);
        }
    }

    public CategoriesTable getCategoriesTable(){
        return categoriesTable;
    }

    public BrandsTable getBrandsTable(){
        return brandsTable;
    }


    public int checkColor(String inputColor) throws Exception {
        Color color = colorsTable.getColor(inputColor);
        if(color == null){
            color = new Color(Inventory.colorRV,
                    Value.create(colorsTable.getNewPK()),
                    Value.create(inputColor));
            colorsTable.add(color);
        }
        return color.getCode();
    }


    public int checkBrand(String inputBrand) throws Exception {
        Brand brand = brandsTable.getBrand(inputBrand);
        if(brand == null){
            brand = new Brand(Inventory.brandRV,
                    Value.create(brandsTable.getNewPK()),
                    Value.create(inputBrand));
            brandsTable.add(brand);
        }
        return brand.getCode();
    }

    public int checkCategory(String inputCategory) throws Exception {
        Category category = categoriesTable.getCategory(inputCategory);
        if(category == null){
            category = new Category(Inventory.categoryRV,
                    Value.create(categoriesTable.getNewPK()),
                    Value.create(inputCategory));
            categoriesTable.add(category);
        }
        return category.getCode();
    }
}
