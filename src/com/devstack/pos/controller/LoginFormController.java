package com.devstack.pos.controller;

import com.devstack.pos.bo.BoFactory;
import com.devstack.pos.bo.custom.CustomerBo;
import com.devstack.pos.bo.custom.UserBo;
import com.devstack.pos.bo.custom.impl.UserBoImpl;
import com.devstack.pos.dao.DatabaseAccessCode;
import com.devstack.pos.dto.UserDto;
import com.devstack.pos.enums.BoType;
import com.devstack.pos.util.PasswordManager;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class LoginFormController {
    public JFXTextField txtEmail;
    public JFXTextField txtPassword;
    public JFXButton btnRegisterOnAction;
    public AnchorPane contxtSign;

    UserBo userBo = BoFactory.getInstance().getBo(BoType.USER);


    public void btnSignInOnAction(ActionEvent actionEvent) {
        try {
            UserDto dto = userBo.findUser(txtEmail.getText());

            if(dto!=null){
               if(PasswordManager.checkPassword(txtPassword.getText(),dto.getPassword())){
                    System.out.println("check");
                    setUi("DashboardForm");
               }else {
                   new Alert(Alert.AlertType.WARNING,"Password is Incorrect ,Try Again").show();
               }

            }else {
                new Alert(Alert.AlertType.WARNING,"User email not found").show();
            }

        }catch (ClassNotFoundException | SQLException | IOException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void btnCreateOnAction(ActionEvent actionEvent) throws IOException {
        setUi("SignupForm");
    }

    private void setUi(String url) throws IOException {
      Stage stage = (Stage)contxtSign.getScene().getWindow();
      stage.setScene(
              new Scene(FXMLLoader.load(getClass().getResource("../view/"+url+".fxml")))
      );
      stage.centerOnScreen();

    }
}
