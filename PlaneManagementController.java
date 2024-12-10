
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

public class PlaneManagementController<T, E> implements ControllerInterface<Plane, PlaneManagementSystem> {
    private PlaneManagementSystem planeManagementSystem;

    public PlaneManagementController(PlaneManagementSystem pms) {

        this.planeManagementSystem = pms;
    }

    @Override
    public void deleteData(Integer index) {
        // TODO Auto-generated method stub
        ArrayList<Plane> planes = planeManagementSystem.getPlaneList();
        planeManagementSystem.removePlane(planes.get(index));

    }

    @Override
    public void addData(Plane plane) {
        // TODO Auto-generated method stub
        planeManagementSystem.addPlane(plane);
    }

    @Override
    public void updateData(Plane plane, Integer index) {
        // TODO Auto-generated method stub

    }

    @Override
    public DefaultTableModel refresh() {
        // TODO Auto-generated method stub
        String[] columnNames = { "Plane ID", "Available", "Capacity" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (Plane plane : planeManagementSystem.getPlaneList()) {
            model.addRow(new String[] { plane.getPlaneId(), String.valueOf(plane.isAvailable()),
                    String.valueOf(plane.getCapacity()) });
        }
        return model;
    }

}
