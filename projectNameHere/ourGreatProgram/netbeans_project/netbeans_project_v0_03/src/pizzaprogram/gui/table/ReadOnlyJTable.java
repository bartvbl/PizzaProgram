package pizzaprogram.gui.table;

import javax.swing.JTable;

public class ReadOnlyJTable extends JTable {
	public final ReadOnlyTableModel tableModel;
	
	public static ReadOnlyJTable createInstance()
	{
		return new ReadOnlyJTable(new ReadOnlyTableModel());
	}
	public ReadOnlyJTable(ReadOnlyTableModel model)
	{
		super(model);
		this.tableModel = model;
	}
}
