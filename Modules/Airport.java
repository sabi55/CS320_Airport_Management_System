package Modules;

import java.util.ArrayList;
public class Airport {
    public static final int numberOfHangars=5;
    public static final int numberOfRunways=5;

    public ArrayList<Hangar> hangars;
    public ArrayList<Runway> runways;


    /**
     *
     */
    public Airport() {
        this.hangars=new ArrayList<>();
        this.runways=new ArrayList<>();
        for (int i=0;i<numberOfHangars;i++)
            this.hangars.add(new Hangar());
        for (int i=0;i<numberOfRunways;i++)
            this.runways.add(new Runway());

    }


    public ArrayList<Hangar> getHangars() {
        return hangars;
    }


    public void setHangars(ArrayList<Hangar> hangars) {
        this.hangars = hangars;
    }


    public ArrayList<Runway> getRunways() {
        return runways;
    }


    public void setRunways(ArrayList<Runway> runways) {
        this.runways = runways;
    }





}
