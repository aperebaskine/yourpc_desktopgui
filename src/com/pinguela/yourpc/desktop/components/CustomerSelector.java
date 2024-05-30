package com.pinguela.yourpc.desktop.components;

import com.pinguela.yourpc.desktop.actions.OpenItemViewDialogAction;
import com.pinguela.yourpc.desktop.actions.YPCAction;
import com.pinguela.yourpc.desktop.util.SwingUtils;
import com.pinguela.yourpc.desktop.view.CustomerView;
import com.pinguela.yourpc.model.Customer;

public class CustomerSelector 
extends ItemSelector<Customer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4428898194837703970L;

	@Override
	protected YPCAction initializeSelectAction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected YPCAction initializeViewAction() {
		return new OpenItemViewDialogAction<>(CustomerView.class, this);
	}

	@Override
	protected String formatItemLabel() {
		Customer c = getItem();
		return String.format("%s - %s", c.getId(), SwingUtils.formatFullName(c));
	}
	
	public Integer getCustomerId() {
		return getItem().getId();
	}

}