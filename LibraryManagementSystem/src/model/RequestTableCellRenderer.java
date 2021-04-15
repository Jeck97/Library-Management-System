package model;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * 
 * This class designs the table in server
 * 
 * @author Chon Yao Jun
 *
 */
public class RequestTableCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;
	
	// RGB value of each color 
	private static final Color BORROW_COLOR = new Color(0, 187, 249);
	private static final Color RETURN_COLOR = new Color(0, 245, 212);
	private static final Color SELECTED_COLOR = new Color(94, 96, 206);

	
	public RequestTableCellRenderer() {
		
		// Set the text in cell align to center
		this.setHorizontalAlignment(JLabel.CENTER);
		
	}
	
	/**
	 * 
	 * This method manages the cell of table
	 * 
	 * @return Component
	 * @param tale
	 * @param value
	 * @param isSelected
	 * @param hasFocus
	 * @param row
	 * @param column
	 * 
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, 
			boolean isSelected, boolean hasFocus, int row, int column) {

		// Get value of request number
		String requestNo = table.getValueAt
				(row, RequestTableModel.REQUESTNO).toString();
		
		// Get the request type
		char requestType = requestNo.charAt(0);
		
		// Decide the color
		Color color = requestType == 'B' ? BORROW_COLOR : RETURN_COLOR;
		
		// Set color to selected row
		if (isSelected)
			color = SELECTED_COLOR;

		// Get the design
		Component component = super.getTableCellRendererComponent
				(table, value, isSelected, hasFocus, row, column);
		component.setBackground(color);

		return component;
	}

}
