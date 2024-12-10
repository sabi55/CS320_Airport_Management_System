import javax.swing.table.DefaultTableModel;

public interface ControllerInterface<T, E> {
	public void addData(T data);
	public void updateData(T data, Integer index);
	public void deleteData(Integer index);
	public DefaultTableModel refresh();

}
