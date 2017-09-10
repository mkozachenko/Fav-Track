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
    private loginController lc;
    private static String update = "wait";
    private int duration, position;
    private double percent;

    public Probe(mainController mc) {
        this.mc = mc;
    }

    /*public Probe(loginController lc) {
        this.lc = lc;
    }*/

    public static void main (String[] args){
        System.out.println(new GetPropetries().getUserLogin());
        new GetPropetries().setUserLogin("sdsdfsghh");
        System.out.println(new GetPropetries().getUserLogin());
    }

    public double percentage(){
       /* MPC.getData();
        position = new MPC().getPosition();
        duration = new MPC().getDuration();*/
        //new Getter().getData();
        position = new Getter().getPosition();
        duration = new Getter().getDuration();
        percent = Math.floor((position*100f)/duration);
        return this.percent;
    }

    public void showname(){
        mc.updateShowData();
    }

    public void rememberMe(){
        //lc.rememberMe();
    }
}
