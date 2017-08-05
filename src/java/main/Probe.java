package main;

import Controllers.*;
import Parsers.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * Created by Admin on 20.07.2017.
 */
public class Probe {
    private mainController mc;
    private static String update = "wait";
    private int duration, position;
    private double percent;

    public Probe(mainController mc) {
        this.mc = mc;
    }

    public static void main (String[] args){
        new Getter().getData();
        update = new Getter().getShowname();
        System.out.println(update);
    }

    public double percentage(){
        MPC.getData();
        position = new MPC().getPosition();
        duration = new MPC().getDuration();
        percent = Math.floor((position*100f)/duration);
        return this.percent;
    }

    public void showname(){
        mc.updateShowname(update);
    }
}
