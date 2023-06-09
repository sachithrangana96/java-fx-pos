package com.devstack.pos.controller;

import com.devstack.pos.dao.DatabaseAccessCode;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

import java.sql.SQLException;

public class ProductMainFormController {
    public JFXButton btnSaveUpdate;
    public JFXTextField txtProductCode;
    public TextArea txtProductDiscription;
    private String searchText = "";
    public void initialize(){
        // get new product id
            loadProductId();
        // get new product id
    }

    private void loadProductId() {

        try {
            txtProductCode.setText(String.valueOf(new DatabaseAccessCode().getLastProductId()));
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
                        new DatabaseAccessCode().saveProduct(
                                Integer.parseInt(txtProductCode.getText()),
                                txtProductDiscription.getText()
                        )
                ){
                    new Alert(Alert.AlertType.CONFIRMATION,"Product Saved").show();
                    loadAllProduct(searchText);
                    clearField();
                }else{
                    new Alert(Alert.AlertType.WARNING,"Try Again!").show();
                }
            }else{
                if(new DatabaseAccessCode().saveProduct(Integer.parseInt(txtProductCode.getText()),txtProductDiscription.getText())
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

    public void loadAllProduct(String searchText){

    }

    public void btnProductOnAction(ActionEvent actionEvent) {
    }
}
