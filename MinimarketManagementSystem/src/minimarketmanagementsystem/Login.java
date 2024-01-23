/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package minimarketmanagementsystem;

import java.net.URL;
import java.util.ResourceBundle;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.event.ActionEvent;

import javafx.fxml.Initializable;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


/**
 *
 * @author herli
 */
public class Login implements Initializable {
    
    
    @FXML
    private TextField admin_username;

    @FXML
    private Hyperlink cust_hyperLink;

    @FXML
    private AnchorPane admin_form;

    @FXML
    private AnchorPane cust_form;

    @FXML
    private PasswordField cust_password;

    @FXML
    private Button cust_loginBtn;

    @FXML
    private TextField cust_username;

    @FXML
    private Hyperlink admin_hyperLink;

    @FXML
    private PasswordField admin_password;

    @FXML
    private Button admin_loginBtn;
    
    //db tools
    private Connection connect;
    private ResultSet result;
    private PreparedStatement prepare;
    
    public void adminLogin() throws SQLException{
        String adminData = "SELECT * FROM admin WHERE admin_username = ? and admin_password = ?";
        connect = Database.getDBConnection();
        
        try{
            
            Alert alert;
            // check apabila textfield kosong
            if(admin_username.getText().isEmpty()
                    || admin_password.getText().isEmpty()){
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Tolong lengkapi kolom inputan");
                alert.showAndWait();
            }else{
            
            prepare = connect.prepareStatement(adminData);
            prepare.setString(1, admin_username.getText());
            prepare.setString(2, admin_password.getText());
            result = prepare.executeQuery();
            
            if(result.next()){
                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Login Berhasil!");
                alert.showAndWait();
                
                admin_loginBtn.getScene().getWindow().hide();
                
                Parent root = FXMLLoader.load(getClass().getResource("admin_dashboard.fxml"));
                
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                
                stage.setScene(scene);
                stage.show();
                
            }else{
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Username atau Password Salah");
                alert.showAndWait();
            }
          
         }
            
        }catch(Exception e){ e.printStackTrace();}
       
    }
    
    
    
    public void switchForm(ActionEvent event){
        if(event.getSource()== admin_hyperLink ){
            admin_form.setVisible(false);
            cust_form.setVisible(true);
        }else if(event.getSource() == cust_hyperLink){
            admin_form.setVisible(true);
            cust_form.setVisible(false);
        }
    }
    
    public void close(){
    System.exit(0);
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
