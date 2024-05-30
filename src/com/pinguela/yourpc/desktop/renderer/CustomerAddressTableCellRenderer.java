package com.pinguela.yourpc.desktop.renderer;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;

import com.pinguela.yourpc.desktop.constants.CustomerAddressTableConstants;
import com.pinguela.yourpc.desktop.util.SwingUtils;
import com.pinguela.yourpc.model.Address;

public class CustomerAddressTableCellRenderer 
extends DefaultTableCellRenderer 
implements CustomerAddressTableConstants {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8433379604113474071L;
	
	public static final Border HIGHLIGHT_BORDER = UIManager.getBorder("Table.focusCellHighlightBorder");
	public static final Border NO_HIGHLIGHT_BORDER = UIManager.getBorder("Table.cellNoFocusBorder");
	
	private JCheckBox checkBox;
	
	public CustomerAddressTableCellRenderer() {
		checkBox = new JCheckBox();
		checkBox.setHorizontalAlignment(JLabel.CENTER);
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int column) {

		Address a = (Address) value;
		Object columnValue = null;
		
		switch (column) {
		case ID_COLUMN_INDEX:
			columnValue = a.getId();
			break;
		case CREATION_DATE_COLUMN_INDEX:
			columnValue = SwingUtils.formatDateTime(a.getCreationDate());
			break;
		case IS_DEFAULT_COLUMN_INDEX:
			checkBox.setSelected(a.isDefault());
			
			if (isSelected) {
				checkBox.setForeground(table.getSelectionForeground());
				checkBox.setBackground(table.getSelectionBackground());

				if (hasFocus) {
					checkBox.setBorder(HIGHLIGHT_BORDER);
				} else {
					checkBox.setBorder(NO_HIGHLIGHT_BORDER);
				}
			} else {
				checkBox.setForeground(table.getForeground());
				checkBox.setBackground(table.getBackground());
				checkBox.setBorder(NO_HIGHLIGHT_BORDER);
			}
			
			return checkBox;
		}
		
		return super.getTableCellRendererComponent(table, columnValue, isSelected, hasFocus, row, column);
	}

}
