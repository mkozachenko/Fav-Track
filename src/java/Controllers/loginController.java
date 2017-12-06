package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.GetPropetries;
import main.MyShowsOAuth;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Created by symph on 09.07.2017.
 */
public class loginController {
    public static String username;
    public static String password;

    @FXML
    private Button exit, loginSave;
    @FXML
    private TextField usernameField, passwordField;
    @FXML
    private Label loginState;
    @FXML
    private CheckBox autoLogin, rememberMe;

    public void initialize() {
        setValues(usernameField, new GetPropetries().getUserLogin());
        setValues(passwordField, new GetPropetries().getUserPassword());
        autoLogin.setSelected(new GetPropetries().getAutoLogin());
        rememberMe.setSelected(new GetPropetries().getRememberMe());
    }

    @FXML
    private void handleLoginSave() throws UnknownHostException {
        //new MyShowsOAuth().getToken(usernameField.getText(), passwordField.getText());
        if(new MyShowsOAuth().getToken(usernameField.getText(), passwordField.getText())=="ok"){
            new GetPropetries().setUserLogin(usernameField.getText());
            new GetPropetries().setUserPassword(passwordField.getText());
            /*new GetPropetries().setAutoLogin(Boolean.toString(autoLogin.isSelected()));
            new GetPropetries().setRememberMe(Boolean.toString(rememberMe.isSelected()));*/
            Stage stage = (Stage) loginSave.getScene().getWindow();
            stage.close();
        } else {
            loginState.setText(MyShowsOAuth.errorHandle());
        }
    }

    @FXML protected void handleQuit(ActionEvent event) throws IOException {
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }

    private static void setValues(TextField field, String value){
        if(value!=""){
            field.setText(value);
        }
    }

    /*public void rememberMe(){
        System.out.println(new GetPropetries().getRememberMe());
        System.out.println(new GetPropetries().getUserLogin());
        if(new GetPropetries().getRememberMe()){
            usernameField.setText(new GetPropetries().getUserLogin());
            passwordField.setFocusTraversable(true);
            rememberMe.setSelected(true);
        }
    }*/



    /*@FXML protected void fieldValidation(ActionEvent event) throws IOException {
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



    @FXML protected void enterSubmit (KeyEvent event){

    }*/


/*    @FXML protected void enterSubmit (KeyEvent event){
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
    }*/


}
