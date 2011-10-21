package pizzaprogram.gui.table;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class ReadOnlyTableModel extends AbstractTableModel {
	private ArrayList<String> columns = new ArrayList<String>();
	private ArrayList<ArrayList<Object>> rows = new ArrayList<ArrayList<Object>>();
	
	public int getColumnCount() {
		return this.columns.size();
	}
	
	public int getRowCount() {
		return rows.size();
	}

	public String getColumnName(int col) {
        return this.columns.get(col);
    }
	
    public boolean isCellEditable(int row, int col)
    { 
    	return false; 
    }
    
    public Object getValueAt(int row, int column)
    {
    	return this.rows.get(row).get(column);
    }
    
    public void setValueAt(Object value, int row, int col) {
        this.rows.get(col).set(row, value);
        fireTableCellUpdated(row, col);
    }
	
	
}