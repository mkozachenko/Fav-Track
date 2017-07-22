package main;

import Controllers.mainController;

/**
 * Created by Admin on 20.07.2017.
 */
public class Probe {
    private mainController mc;

    public Probe(mainController mc) {
        this.mc = mc;
    }

    public void probe(String update){
        mc.updateLabel(update);
    }
}
