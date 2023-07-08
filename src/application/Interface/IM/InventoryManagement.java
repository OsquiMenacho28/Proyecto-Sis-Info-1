package application.Interface.IM;

import DataBaseManager.Value;
import InventoryModel.Brand;
import InventoryModel.Category;
import InventoryModel.Inventory;
import InventoryModel.Product;
import SalesModel.Cart;
import application.FlowController.SesionAdmin;
import application.Interface.AdminPromptWindow;
import application.Interface.POS.ItemIcon;
import application.Interface.POS.POSOpen;
import application.Interface.PromptWindow;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.apache.commons.text.similarity.JaccardDistance;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

public class InventoryManagement extends AdminPromptWindow implements Initializable {

    @FXML
    private ScrollPane ListScroll;

    @FXML
    private FlowPane ProductList;

    private TextField SearchCode_F;

    @FXML
    private TextField SearchProduct_F;

    @FXML
    private ComboBox<Brand> BrandFilter_C;

    @FXML
    private ComboBox<Category> CategoryFilter_C;

    @FXML
    private Button Notification_B;

    @FXML
    private Button Mode_B;

    @FXML
    private MenuItem LightMode_Opt;

    @FXML
    private MenuItem DarkMode_Opt;

    @FXML
    private Button Close_B;

    @FXML
    private MenuButton Menu_B;

    @FXML
    private MenuItem Sales_Opt;

    @FXML
    private MenuItem AddProduct_Opt;

    @FXML
    private Button Back_B;

    @FXML
    private Button EditProduct_B;

    @FXML
    private Button EnableProduct_B;

    @FXML
    private Button DeleteProduct_B;

    @FXML
    private Button EntryProduct_B;

    @FXML
    private Button CancelProduct_B;

    private Inventory inventory;
    private Cart cart;
    private ObservableList<Node> visibleProducts;

    public ObservableList<Product> products = FXCollections.observableArrayList();

    int clickCount = 0;
    boolean isButtonEnabled = false;

    public InventoryManagement(SesionAdmin ses, PromptWindow origin) throws IOException {
        super(ses, "InventoryManagement.fxml", origin, "GESTIÓN DE INVENTARIO");
        super.stage.setMaximized(true);
        this.inventory = ses.getInventory();
        this.cart = null;
        this.load();
    }

    private class NameComparator implements Comparator<ItemIcon> {
        String target;
        private NameComparator(String target){
            this.target = target;
        }
        @Override
        public int compare(ItemIcon i1, ItemIcon i2) {
            Product p1 = i1.getProduct();
            Product p2 = i2.getProduct();

            String name1 = p1.getName();
            String name2 = p2.getName();

            return (int)(1000*(NameDistance(name1, this.target) - NameDistance(name2, this.target)));
        }
        private double NameDistance(String shot, String target){
            LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
            JaccardDistance jaccardDistance = new JaccardDistance();
            Integer maxLen = Math.max(target.length(), shot.length());
            return 0.7*(levenshteinDistance.apply(target, shot)/maxLen) + 0.3*jaccardDistance.apply(target, shot);
        }
    }

    private class CodeComparator implements Comparator<ItemIcon> {
        String target;
        private CodeComparator(String target){
            this.target = target;
        }
        @Override
        public int compare(ItemIcon i1, ItemIcon i2) {
            Product p1 = i1.getProduct();
            Product p2 = i2.getProduct();

            String name1 = String.valueOf(p1.getCode());
            String name2 = String.valueOf(p2.getCode());

            return (int)(1000*(CodeDistance(name1, this.target) - CodeDistance(name2, this.target)));
        }
        private double CodeDistance(String shot, String target){
            LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
            JaccardDistance jaccardDistance = new JaccardDistance();
            Integer maxLen = Math.max(target.length(), shot.length());
            return 0.3*(levenshteinDistance.apply(target, shot)/maxLen) + 0.7*(target.equals(shot) ? 1 : 0);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        stage.setOnCloseRequest(windowEvent -> sesion.closeApplicationRequest(windowEvent));

        Notification_B.setOnAction(actionEvent -> sesion.notificationsRequest());

        LightMode_Opt.setOnAction(actionEvent -> {

        });

        DarkMode_Opt.setOnAction(actionEvent -> {

        });

        Close_B.setOnAction(actionEvent -> sesion.logOutRequest());

        Sales_Opt.setOnAction(actionEvent -> sesion.salesRequest());

        AddProduct_Opt.setOnAction(actionEvent -> sesion.addProductRequest());

        EditProduct_B.setOnAction(actionEvent -> sesion.editProductRequest());

        EnableProduct_B.setOnMouseClicked(mouseEvent -> {
            clickCount++;
            if (clickCount % 2 == 1) {
                isButtonEnabled = true;
                EnableProduct_B.setStyle("-fx-background-color: black");
                EnableProduct_B.setFont(new Font(25));
                EnableProduct_B.setText("HABILITAR");
                //deshabilitar
            } else {
                isButtonEnabled = false;
                EnableProduct_B.setStyle("-fx-background-color: #ff8500");
                EnableProduct_B.setFont(new Font(21));
                EnableProduct_B.setText("DESHABILITAR");
                //habilitar
            }
        });

        DeleteProduct_B.setOnAction(actionEvent -> sesion.deleteProductRequest());

        EntryProduct_B.setOnAction(actionEvent -> sesion.entryProductRequest());

        Back_B.setOnAction(actionEvent -> back());

        ListScroll.setFitToWidth(true);
        //ListScroll.setFitToHeight(true);

        ListScroll.setFitToWidth(true);
        SearchProduct_F.textProperty().addListener(e -> {
            if(!SearchCode_F.getText().equals("")){SearchCode_F.clear();}
            nameSearch();
        });
        SearchCode_F.textProperty().addListener(e -> {
            if(!SearchProduct_F.getText().equals("")){SearchProduct_F.clear();}
            codeSearch();
        });

        CategoryFilter_C.setItems(inventory.getCategoriesTable().getCollection());
        BrandFilter_C.setItems(inventory.getBrandsTable().getCollection());

        Category deselect;
        try {
            deselect = new Category(Inventory.categoryRV, Value.create(-1), Value.create("QUITAR FILTRO"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        CategoryFilter_C.getItems().add(deselect);
        CategoryFilter_C.setCellFactory(new Callback<ListView<Category>, ListCell<Category>>() {
            @Override
            public ListCell<Category> call(ListView<Category> param) {
                return new ListCell<Category>() {
                    @Override
                    protected void updateItem(Category item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getCategory());
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });

        CategoryFilter_C.setConverter(new StringConverter<Category>() {
            @Override
            public String toString(Category item) {
                if (item == null) {
                    return null;
                } else {
                    return item.getCategory();
                }
            }

            @Override
            public Category fromString(String string) {
                // This method is not used for ComboBox
                return null;
            }
        });

        BrandFilter_C.setCellFactory(new Callback<ListView<Brand>, ListCell<Brand>>() {
            @Override
            public ListCell<Brand> call(ListView<Brand> param) {
                return new ListCell<Brand>() {
                    @Override
                    protected void updateItem(Brand item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getBrand());
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });

        Brand deselect1;
        try {
            deselect1 = new Brand(Inventory.brandRV, Value.create(-1), Value.create("QUITAR FILTRO"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        BrandFilter_C.getItems().add(deselect1);
        BrandFilter_C.setConverter(new StringConverter<Brand>() {
            @Override
            public String toString(Brand item) {
                if (item == null) {
                    return null;
                } else {
                    return item.getBrand();
                }
            }

            @Override
            public Brand fromString(String string) {
                // This method is not used for ComboBox
                return null;
            }
        });

        CategoryFilter_C.valueProperty().addListener(e -> {
            if(CategoryFilter_C.getValue().equals(deselect)){
                CategoryFilter_C.setValue(null);
            }
            getVisibleItems();
        });

        BrandFilter_C.valueProperty().addListener(e -> {
            if(BrandFilter_C.getValue().equals(deselect1)){
                BrandFilter_C.setValue(null);
            }
            getVisibleItems();
        });

        this.visibleProducts = FXCollections.observableArrayList();


        //Update visible products in case the inventory is changed
        inventory.getIcons().addListener((ListChangeListener<? super ItemIcon>) c -> {
            while (c.next()) {
                if (c.wasRemoved()) {
                    for (ItemIcon icon : c.getRemoved()) {
                        visibleProducts.remove(icon);
                    }
                } else if (c.wasAdded()) {
                    for (ItemIcon icon : c.getAddedSubList()) {
                        if (icon.getProduct().isVisible()) {
                            icon.setOnMouseClicked(e -> {
                                this.showDescription(icon.getProduct());
                            });
                            visibleProducts.add(icon);
                        }
                    }
                }
            }
        });

        getVisibleItems();
    }


    public void getVisibleItems(){
        visibleProducts.clear();
        //Extract the visible Items from inventory
        Category cat = CategoryFilter_C.getValue();
        Brand brand = BrandFilter_C.getValue();
        for(ItemIcon icon : inventory.getIcons()){
            if(icon.getProduct().isVisible()){
                icon.setOnMouseClicked(e -> {
                    this.showDescription(icon.getProduct());
                });
                visibleProducts.add(icon);

                if(cat != null){
                    if(cat.getCode() != icon.getProduct().getCategory().getCode()){
                        visibleProducts.remove(icon);
                    }
                }

                if(brand != null){
                    if(brand.getCode() != icon.getProduct().getBrand().getCode()){
                        visibleProducts.remove(icon);
                    }
                }
            }
        }
        //System.out.println(visibleProducts);
        ProductList.getChildren().clear();
        ProductList.getChildren().addAll(visibleProducts);
    }

    private void nameSearch(){
        String productQuery = SearchProduct_F.getText();
        visibleProducts.sort((Comparator) new NameComparator(productQuery));
        ProductList.getChildren().clear();
        ProductList.getChildren().addAll(visibleProducts);
    }

    private void codeSearch(){
        String codeQuery = SearchCode_F.getText();
        visibleProducts.sort((Comparator) new CodeComparator(codeQuery));
        ProductList.getChildren().clear();
        ProductList.getChildren().addAll(visibleProducts);
    }

    private void showDescription(Product p){

    }

}
