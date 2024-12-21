package Systems;

import Modules.Airport;
import Modules.Hangar;
import Modules.Plane;
import Modules.Runway;

import java.util.ArrayList;


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
            int hangarCode = Integer.parseInt(hangarId);

            if (planeId.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Plane ID must not be empty", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ArrayList<Plane> planes = pms.getPlaneList();
            System.out.println("Planes in system: " + planes);

            Plane plane = null;
            for (Plane p : planes) {
                System.out.println("Checking plane: " + p.getPlaneId());
                if (String.valueOf(p.getPlaneId()).equals(planeId)) {
                    plane = p;
                    break;
                }
            }

            if (plane == null) {
                JOptionPane.showMessageDialog(null, "Plane ID not found", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ArrayList<Hangar> hangars = airport.getHangars();
            Hangar hangar = findHangar(hangars, hangarCode);
            if (hangar == null) {
                JOptionPane.showMessageDialog(null, "Invalid Hangar ID", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (hangar.getPlane() != null) {
                JOptionPane.showMessageDialog(null, "Selected Hangar is already reserved", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            hangar.setPlane(plane);
            airport.setHangars(hangars);

            JOptionPane.showMessageDialog(null, "Hangar " + hangarId + " is reserved by " + planeId, "Info",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Hangar ID must be a number", "Error", JOptionPane.ERROR_MESSAGE);
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
            int runwayCode = Integer.parseInt(runwayId);

            if (planeId.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Plane ID must not be empty", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ArrayList<Plane> planes = pms.getPlaneList();
            ArrayList<Runway> runways = airport.getRunways();

            Plane plane = null;
            for (Plane p : planes) {
                if (p.getPlaneId().equals(planeId)) {
                    plane = p;
                    break;
                }
            }

            if (plane == null) {
                JOptionPane.showMessageDialog(null, "Plane ID not found", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Runway runway = findRunway(runways, runwayCode);
            if (runway == null) {
                JOptionPane.showMessageDialog(null, "Invalid Runway ID", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (runway.getPlane() != null) {
                JOptionPane.showMessageDialog(null, "Selected Runway is already reserved", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            runway.setPlane(plane);
            airport.setRunways(runways);
            JOptionPane.showMessageDialog(null, "Runway " + runwayId + " is reserved by " + planeId, "Info",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Runway ID must be a number", "Error", JOptionPane.ERROR_MESSAGE);
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
