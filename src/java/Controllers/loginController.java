package Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import main.MyShowsOAuth;

import java.io.IOException;

/**
 * Created by symph on 09.07.2017.
 */
public class loginController {
    public static String username;
    public static String password;

    @FXML
    Button exit, login;
    @FXML
    TextField usernameField, passwordField;
    @FXML
    Label errorOut;

    @FXML protected void fieldValidation(ActionEvent event) throws IOException {
        usernameField.textProperty().addListener((observable, oldValue, newValue) -> {

                if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
                    // check condition and apply necessay style
                    usernameField.setStyle("-fx-background-color: #068383;");
                    passwordField.setStyle("-fx-background-color: #068383;");
                } else {
                    usernameField.setStyle("-fx-background-color: #068383;");
                    passwordField.setStyle("-fx-background-color: #068383;");
                }
        });
    }

    @FXML protected void handleLogin(ActionEvent event) throws IOException {
        System.out.println("click! login");
        //sernameField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(usernameField.getText().isEmpty()||passwordField.getText().isEmpty()) { // we only care about loosing focus
                // check condition and apply necessay style
                    usernameField.setStyle("-fx-background-color: #E96464;");
                    passwordField.setStyle("-fx-background-color: #E96464;");
                } else {
                    //usernameField.setStyle("-fx-background-color: #8BCB0A;");
                    //passwordField.setStyle("-fx-background-color: #8BCB0A;");
                    MyShowsOAuth.getToken(usernameField.getText(), passwordField.getText());
                    errorOut.setText(MyShowsOAuth.errorHandle());
                    if (MyShowsOAuth.getResponse() != null) {
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("mainWindow.fxml"));
                            Parent root1 = (Parent) fxmlLoader.load();
                            Stage stageOld = (Stage) exit.getScene().getWindow();
                            // do what you have to do
                            stageOld.hide();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(root1));
                            stage.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        //});
    }

    @FXML protected void handleQuit(ActionEvent event) throws IOException {
        Stage stage = (Stage) exit.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML protected void enterSubmit (KeyEvent event){
    if (event.getCode() == KeyCode.ENTER) {
        if(usernameField.getText().isEmpty()||passwordField.getText().isEmpty()) { // we only care about loosing focus
            // check condition and apply necessay style
            usernameField.setStyle("-fx-background-color: #E96464;");
            passwordField.setStyle("-fx-background-color: #E96464;");
        } else {
            //usernameField.setStyle("-fx-background-color: #8BCB0A;");
            //passwordField.setStyle("-fx-background-color: #8BCB0A;");
            MyShowsOAuth.getToken(usernameField.getText(), passwordField.getText());
            errorOut.setText(MyShowsOAuth.errorHandle());
            if (MyShowsOAuth.getResponse() != null) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("mainWindow.fxml"));
                    Parent root1 = (Parent) fxmlLoader.load();
                    Stage stageOld = (Stage) exit.getScene().getWindow();
                    stageOld.hide();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root1));
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


}
