package main;

import Controllers.*;
import Parsers.*;

/**
 * Created by Admin on 20.07.2017.
 */
public class Probe {
    private mainController mc;

    private int duration, position;
    private double percent;

    public Probe(mainController mc) {
        this.mc = mc;
    }


    public double percentage(){
        MPC.getData();
        position = new MPC().getPosition();
        duration = new MPC().getDuration();
        percent = Math.floor((position*100f)/duration);
        return this.percent;
    }
}
