package com.pinguela.yourpc.desktop.actions;

import javax.swing.Action;

import com.pinguela.YPCException;
import com.pinguela.yourpc.desktop.constants.Icons;
import com.pinguela.yourpc.desktop.view.CustomerOrderView;
import com.pinguela.yourpc.model.CustomerOrder;
import com.pinguela.yourpc.service.CustomerOrderService;
import com.pinguela.yourpc.service.impl.CustomerOrderServiceImpl;

public class SaveCustomerOrderAction 
extends SaveItemAction<CustomerOrder> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4360296673674907154L;
	
	private CustomerOrderService customerOrderService;

	public SaveCustomerOrderAction(CustomerOrderView view) {
		super(view, "Save", Icons.OK_ICON);
		this.customerOrderService = new CustomerOrderServiceImpl();
	}

	@Override
	protected void doSave() throws YPCException {
		CustomerOrder newOrder = getView().getNewItem();
		customerOrderService.update(newOrder);
		getView().setItem(customerOrderService.findById(newOrder.getId()));
	}
	
	@Override
	protected Action[] getViewerActions() {
		return new Action[]{new EditItemAction<CustomerOrder>(getView())};
	}

}
