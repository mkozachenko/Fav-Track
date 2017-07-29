package Controllers;

import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import Parsers.*;

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


    public void updateLabel(String update){
        shownameField.setText(update);
    }


/*
    public void update(){
        do{
            MPC.getData();
            position = new MPC().getPosition();
            duration = new MPC().getDuration();
            percent = (long) Math.floor(position/duration);
            System.out.println(percent);
            System.out.println(position);
            System.out.println(duration);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while(percent<0.97);
    }
*/

public void receiveData(){

}

}
