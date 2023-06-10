package com.devstack.pos.controller;

import com.devstack.pos.bo.custom.impl.CustomerBoImpl;
import com.devstack.pos.dao.DatabaseAccessCode;
import com.devstack.pos.dto.CustomerDto;
import com.devstack.pos.view.tm.CustomerTm;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class CustomerFormController {
    public AnchorPane contextCustomer;
    public JFXTextField txtEmail;
    public JFXTextField txtName;
    public JFXTextField txtContact;
    public JFXTextField txtSalary;
    public JFXButton btnSave;
    public TextField txtSearch;
    public TableView<CustomerTm> tbc;
    public TableColumn colId;
    public TableColumn colEmail;
    public TableColumn colName;
    public TableColumn colContact;
    public TableColumn colSalary;
    public TableColumn colOperate;

    private String searchText = "";


    public void initialize() throws SQLException, ClassNotFoundException {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colOperate.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));

        loadAllCustomers(searchText);

        tbc.getSelectionModel()
                .selectedItemProperty()
                .addListener(((observable, oldValue, newValue) -> {
                    if(newValue!=null){
                        setData(newValue);
                    }
                }));

        txtSearch.textProperty().addListener(((observable, oldValue, newValue) -> {
            searchText = newValue;
            try {
                loadAllCustomers(searchText);
            }catch (SQLException | ClassNotFoundException e){
                throw new RuntimeException(e);
            }
        }));
    }

    private void setData(CustomerTm newValue) {
        txtEmail.setEditable(false);
        btnSave.setText("Update Customer");
        txtEmail.setText(newValue.getEmail());
        txtName.setText(newValue.getName());
        txtContact.setText(newValue.getContact());
        txtSalary.setText(String.valueOf(newValue.getSalary()));

        
    }

    private void loadAllCustomers(String searchText) throws SQLException, ClassNotFoundException {
        ObservableList<CustomerTm> observableList = FXCollections.observableArrayList();
        int counter=1;
        for (CustomerDto dto: searchText.length() >0 ? new CustomerBoImpl().searchCustomer(searchText) :
                new CustomerBoImpl().findAllCustomer()
        ){
            Button btn = new Button("Delete");
            CustomerTm tm = new CustomerTm(
                    counter,dto.getEmail(), dto.getName(), dto.getContact(), dto.getSalary(),
                    btn
            );
            observableList.add(tm);
            counter++;

            btn.setOnAction((e)->{

                try{
                    Alert alert =  new Alert(Alert.AlertType.CONFIRMATION,"Are you sure",ButtonType.YES,ButtonType.NO);
                    Optional<ButtonType> selectedButtonType = alert.showAndWait();
                    if(selectedButtonType.get().equals(ButtonType.YES)){
                        if(
                                new CustomerBoImpl().deleteCustomer(dto.getEmail())
                        ){
                            new Alert(Alert.AlertType.CONFIRMATION,"Customer Deleted").show();

                            loadAllCustomers(searchText);
                        }else{
                            new Alert(Alert.AlertType.WARNING,"Try Again!").show();
                        }
                    }else{
                        System.out.println(ButtonType.YES);
                    }
                    } catch (SQLException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                    new Alert(Alert.AlertType.ERROR,ex.getMessage()).show();
                }
                });

        }
        tbc.setItems(observableList);
    }

    public void btnSaveUpdateOnAction(ActionEvent actionEvent) {
        try{


            if(btnSave.getText().equals("Save Customer")){
                if(
                        new CustomerBoImpl().saveCustomer(
                                new CustomerDto(
                                txtEmail.getText(),
                                txtName.getText(),
                                txtContact.getText(),
                                Double.parseDouble(txtSalary.getText())
                                )
                        )
                ){
                    new Alert(Alert.AlertType.CONFIRMATION,"Customer Saved").show();
                    loadAllCustomers(searchText);
                    clearField();
                }else{
                    new Alert(Alert.AlertType.WARNING,"Try Again!").show();
                }
            }else{
                if(
                        new CustomerBoImpl().updateCustomer(
                                new CustomerDto(
                                txtEmail.getText(),
                                txtName.getText(),
                                txtContact.getText(),
                                Double.parseDouble(txtSalary.getText())
                                )
                        )
                ){
                    new Alert(Alert.AlertType.CONFIRMATION,"Customer Update").show();
                    loadAllCustomers(searchText);
                    clearField();

                    //.................................

                    txtEmail.setEditable(true);
                    btnSave.setText("Save Customer");

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
        txtEmail.clear();
        txtContact.clear();
        txtName.clear();
        txtSalary.clear();
    }



    public void btnBackToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardForm");
    }

    private void setUi(String url) throws IOException {
        Stage stage = (Stage)contextCustomer.getScene().getWindow();
        stage.setScene(
                new Scene(FXMLLoader.load(getClass().getResource("../view/"+url+".fxml")))
        );
        stage.centerOnScreen();

    }

    public void btnNewCustomerOnAction(ActionEvent actionEvent) {
        txtEmail.setEditable(true);
        btnSave.setText("Save Customer");
        clearField();
    }
}
