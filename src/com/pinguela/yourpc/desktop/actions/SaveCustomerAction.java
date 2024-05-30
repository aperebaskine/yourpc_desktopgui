package com.pinguela.yourpc.desktop.actions;

import javax.swing.Action;

import com.pinguela.YPCException;
import com.pinguela.yourpc.desktop.constants.Icons;
import com.pinguela.yourpc.desktop.view.CustomerView;
import com.pinguela.yourpc.model.Customer;
import com.pinguela.yourpc.service.CustomerService;
import com.pinguela.yourpc.service.impl.CustomerServiceImpl;

public class SaveCustomerAction 
extends SaveItemAction<Customer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2287828731774496866L;

	private CustomerService customerService;

	public SaveCustomerAction(CustomerView view) {
		super(view, "Save", Icons.OK_ICON);
		this.customerService = new CustomerServiceImpl();
	}
	
	@Override
	protected void doCreate(Customer item) throws YPCException {
		customerService.register(item);
		getView().setItem(customerService.findById(item.getId()));
	}
	
	@Override
	protected void doUpdate(Customer item) throws YPCException {
		customerService.update(item);
		getView().setItem(customerService.findById(item.getId()));
	}
	
	@Override
	protected Action[] getViewerActions() {
		return new Action[]{new DeleteCustomerAction(getView()), new EditItemAction<Customer>(getView())};
	}
	
}
