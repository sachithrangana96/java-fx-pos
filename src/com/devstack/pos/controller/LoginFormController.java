package com.devstack.pos.controller;

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


    public void btnSignInOnAction(ActionEvent actionEvent) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pos","root","12345");
            String sql = "SELECT * FROM user WHERE email=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,txtEmail.getText());
            ResultSet set = preparedStatement.executeQuery();


            if(set.next()){
               if(PasswordManager.checkPassword(txtPassword.getText(),set.getString("password"))){
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
