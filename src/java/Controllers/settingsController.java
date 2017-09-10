package Controllers;

import MYSHOWS.Manage;
import MYSHOWS.Shows;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import main.GetPropetries;

public class settingsController {
    @FXML
    private RadioButton select_MPC, select_VLC;
    @FXML
    private TextField MPC_host_field, MPC_port_field, VLC_host_field, VLC_port_field, VLC_login_field, VLC_password_field;
    @FXML
    private Button settingsSave, settingsDiscard;

    public void initialize() {
        setValues(MPC_host_field,new GetPropetries().getMPC_host());
        setValues(MPC_port_field,new GetPropetries().getMPC_port());

        setValues(VLC_host_field,new GetPropetries().getVLC_host());
        setValues(VLC_port_field,new GetPropetries().getVLC_port());
        setValues(VLC_login_field,new GetPropetries().getVLC_login());
        setValues(VLC_password_field,new GetPropetries().getVLC_password());

        switch(new GetPropetries().getUserPref_Player()){
            case "MPC":
                select_MPC.setSelected(true);
                break;
            case "VLC":
                select_VLC.setSelected(true);
                break;
        }
    }

    @FXML
    private void сlickSettingsSave(){
        if(select_VLC.isSelected()){
            new GetPropetries().setUserPlayer("VLC");
        } else if(select_MPC.isSelected()){
            new GetPropetries().setUserPlayer("MPC");
        }

        new GetPropetries().setMPC_host(MPC_host_field.getText());
        new GetPropetries().setMPC_port(MPC_port_field.getText());

        new GetPropetries().setVLC_host(VLC_host_field.getText());
        new GetPropetries().setVLC_port(VLC_port_field.getText());
        new GetPropetries().setVLC_login(VLC_login_field.getText());
        new GetPropetries().setVLC_password(VLC_password_field.getText());

        Stage stage = (Stage) settingsDiscard.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void clickSettingsDiscard(){
        Stage stage = (Stage) settingsDiscard.getScene().getWindow();
        stage.close();
    }

    private static void setValues(TextField field, String value){
        if(value!=""){
            field.setText(value);
        }
    }

}
