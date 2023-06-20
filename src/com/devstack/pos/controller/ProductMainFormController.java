package com.devstack.pos.controller;

import com.devstack.pos.bo.BoFactory;
import com.devstack.pos.bo.custom.ProductBo;
import com.devstack.pos.bo.custom.UserBo;
import com.devstack.pos.bo.custom.impl.ProductBoImpl;
import com.devstack.pos.dao.DatabaseAccessCode;
import com.devstack.pos.dto.ProductDto;
import com.devstack.pos.enums.BoType;
import com.devstack.pos.view.tm.CustomerTm;
import com.devstack.pos.view.tm.ProductTm;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class ProductMainFormController {
    public JFXButton btnSaveUpdate;
    public JFXTextField txtProductCode;
    public TextArea txtProductDiscription;
    public TableView<ProductTm> tbp;
    public TableColumn colProductId;
    public TableColumn colProductDesc;
    public TableColumn colProductShowMore;
    public TableColumn colProductDelete;
    public TextArea txtSelectedProdDesc;
    public TextField txtSelectedProdId;
    private String searchText = "";

    ProductBo productBo = BoFactory.getInstance().getBo(BoType.PRODUCT);
    public void initialize() throws SQLException, ClassNotFoundException {

        colProductId.setCellValueFactory(new PropertyValueFactory<>("code"));
        colProductDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colProductShowMore.setCellValueFactory(new PropertyValueFactory<>("showMore"));
        colProductDelete.setCellValueFactory(new PropertyValueFactory<>("delete"));




        // get new product id
            loadProductId();
        // get new product id
          loadAllProduct(searchText);

          tbp.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
              setData(newValue);
          });
    }

    private void setData(ProductTm newValue) {
        txtSelectedProdId.setText(String.valueOf(newValue.getCode()));
        txtProductDiscription.setText(newValue.getDescription());
    }

    private void loadProductId() {

        try {
            txtProductCode.setText(String.valueOf(productBo.getLastProductId()));
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnBackToHomeOnAction(ActionEvent actionEvent) {
    }

    public void btnNewProductOnAction(ActionEvent actionEvent) {
        try{


            if(btnSaveUpdate.getText().equals("Save Product")){
                if(
                        productBo.saveProduct(
                                new ProductDto(
                                Integer.parseInt(txtProductCode.getText()),
                                txtProductDiscription.getText()
                                )
                        )
                ){
                    new Alert(Alert.AlertType.CONFIRMATION,"Product Saved").show();
                    loadAllProduct(searchText);
                    clearField();
                }else{
                    new Alert(Alert.AlertType.WARNING,"Try Again!").show();
                }
            }else{
                if(productBo.saveProduct(
                        new ProductDto(
                        Integer.parseInt(txtProductCode.getText()),txtProductDiscription.getText())
                )
                ){
                    new Alert(Alert.AlertType.CONFIRMATION,"Product Update").show();
                    loadAllProduct(searchText);
                    clearField();

                    //.................................

                    btnSaveUpdate.setText("Save Product");

                }else{
                    new Alert(Alert.AlertType.WARNING,"Try Again!").show();
                }
            }





        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void clearField() {
      txtProductCode.clear();
      txtProductDiscription.clear();
        loadProductId();
    }

    public void loadAllProduct(String searchText) throws SQLException, ClassNotFoundException {
        ObservableList<ProductTm> tms = FXCollections.observableArrayList();
        for (ProductDto dto: productBo.findAllProduct()){

            Button shoeMore = new Button("Show more");
            Button delete = new Button("Delete");
            ProductTm tm = new ProductTm(dto.getCode(),dto.getDescription(),shoeMore,delete);
            tms.add(tm);
        }
        System.out.println(tms);
        tbp.setItems(tms);
    }

    public void btnProductOnAction(ActionEvent actionEvent) {
    }

    public void btnNewBatchOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
//        Parent load = FXMLLoader.load(getClass().getResource("../view/NewBatchForm"));
//        stage.setScene(new Scene(load));

//        stage.centerOnScreen();



        stage.setScene(
                new Scene(FXMLLoader.load(getClass().getResource("../view/NewBatchForm.fxml")))
        );
        stage.show();
        stage.centerOnScreen();
    }
}
