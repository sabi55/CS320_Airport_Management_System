import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import javax.swing.JOptionPane;

public class AirportManagementSystem {

    private Airport airport;
    public  PassengerManagementSystem passengerManagementSystem;

    public AirportManagementSystem() {
        this.airport = new Airport();
        passengerManagementSystem=new PassengerManagementSystem();
    }

    public void addHangar() {
        ArrayList<Hangar> hangars = airport.getHangars();
        hangars.add(new Hangar());

        airport.setHangars(hangars);
    }

    public void removeHangar(String hangarId) {
        ArrayList<Hangar> hangars = airport.getHangars();
        try {
            int hangarCode = Integer.valueOf(hangarId);
            Hangar hangar=findHangar(hangars, hangarCode);
            if (hangar!=null) {
                if (hangar.getPlane()==null) {
                    hangars.remove(hangars.indexOf(hangar));
                    airport.setHangars(hangars);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Selected hangar cannot be removed. It is reserved by "+hangar.getPlane().getPlaneId(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Wrong Hangar ID", "Error", JOptionPane.ERROR_MESSAGE);

            }
        }catch (NumberFormatException e1) {
            JOptionPane.showMessageDialog(null, "Hangar id must  be a number", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void addRunway() {
        ArrayList<Runway> runways = airport.getRunways();
        runways.add(new Runway());
        airport.setRunways(runways);
    }

    public void reserveHangar(PlaneManagementSystem pms, String hangarId, String planeId) {
        try {
            int hangarCode = Integer.valueOf(hangarId);
            if (! planeId.isEmpty()) {
                ArrayList<Hangar> hangars = airport.getHangars();
                Hangar hangar=findHangar(hangars,hangarCode);
                if (hangar!=null) {
                    if (hangar.getPlane() == null) {
                        Plane plane = pms.getPlaneList().get(Integer.valueOf(planeId));
                        hangar.setPlane(plane);
                        airport.setHangars(hangars);
                        JOptionPane.showMessageDialog(null, "Hangar " + hangarId+ " is reserved by " + plane.getPlaneId(),"Info",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Selected Hangar is not available", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }

            } else {
                JOptionPane.showMessageDialog(null, "Select a plane and type Hangar Id", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e1) {
            JOptionPane.showMessageDialog(null, "Hangar id must  be a number", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }
    public void removeRunway(String runwayId) {
        ArrayList<Runway> runways = airport.getRunways();
        try {
            int runwayCode = Integer.valueOf(runwayId);
            Runway runway=findRunway(runways, runwayCode);
            if (runway!=null) {
                if (runway.getPlane()==null) {
                    runways.remove(runways.indexOf(runway));
                    airport.setRunways(runways);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Selected runway cannot be removed. It is reserved by "+runway.getPlane().getPlaneId(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Wrong Runway ID", "Error", JOptionPane.ERROR_MESSAGE);

            }
        }catch (NumberFormatException e1) {
            JOptionPane.showMessageDialog(null, "Runway id must  be a number", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Hangar findHangar(ArrayList<Hangar> hangars, int hangarCode) {
        for (Hangar hangar : hangars) {
            if (hangar.getHangarId()==hangarCode)
                return hangar;
        }
        return null;
    }
    private Runway findRunway(ArrayList<Runway> runways, int runwayCode) {
        for (Runway runway : runways) {
            if (runway.getRunwayId()==runwayCode)
                return runway;
        }
        return null;
    }

    public void reserveRunway(PlaneManagementSystem pms, String runwayId, String planeId) {
        try {
            int runwayCode = Integer.valueOf(runwayId);
            if (! planeId.isEmpty()) {
                ArrayList<Runway> runways = airport.getRunways();
                Runway runway=findRunway(runways,runwayCode);
                if (runway!=null) {
                    if (runway.getPlane() == null) {
                        Plane plane = pms.getPlaneList().get(Integer.valueOf(planeId));
                        runway.setPlane(plane);
                        airport.setRunways(runways);
                        JOptionPane.showMessageDialog(null, "Runway " + runwayId+ " is reserved by " + plane.getPlaneId(),"Info",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Selected Runway is not available", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }

            } else {
                JOptionPane.showMessageDialog(null, "Select a plane and type Runway Id", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e1) {
            JOptionPane.showMessageDialog(null, "Runway id must  be a number", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void resetHangar(PlaneManagementSystem pms, String hangarId) {
        try {
            ArrayList<Hangar> hangars = airport.getHangars();
            int hangarCode = Integer.valueOf(hangarId);
            Hangar hangar=findHangar(hangars, hangarCode);
            if (hangar.getPlane()==null) {
                JOptionPane.showMessageDialog(null, "Hangar id already empty", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                hangar.setPlane(null);
                airport.setHangars(hangars);
            }
        } catch (NumberFormatException e1) {
            JOptionPane.showMessageDialog(null, "Hangar id must  be a number", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void resetRunway(PlaneManagementSystem pms,String runwayId) {
        try {
            ArrayList<Runway> runways = airport.getRunways();
            int runwayCode = Integer.valueOf(runwayId);
            Runway runway=findRunway(runways, runwayCode);
            if (runway.getPlane()==null) {
                JOptionPane.showMessageDialog(null, "Runway id already empty", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                runway.setPlane(null);
                airport.setRunways(runways);
            }
        } catch (NumberFormatException e1) {
            JOptionPane.showMessageDialog(null, "Runway id must  be a number", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public ArrayList<Hangar> getHangars() {
        return airport.getHangars();
    }
    public ArrayList<Runway> getRunways() {
        return airport.getRunways();
    }

}
