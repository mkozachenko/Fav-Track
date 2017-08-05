package Controllers;

import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import Parsers.*;
import main.Getter;

/**
 * Created by symph on 09.07.2017.
 */
public class mainController {

    private int duration, position;
    private long percent;

    @FXML
    private TextField shownameField,seasonField, episodeField;

    @FXML
    private Label showLabel;


    public void updateShowname(String update){
        shownameField.setText(update);
    }

public void receiveData(){

}

}
