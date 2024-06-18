package com.pinguela.yourpc.desktop.components;

import com.pinguela.yourpc.desktop.actions.OpenCustomerAddressViewDialogAction;
import com.pinguela.yourpc.desktop.actions.YPCAction;
import com.pinguela.yourpc.model.AbstractPerson;
import com.pinguela.yourpc.model.Address;

@SuppressWarnings("serial")
public class CustomerAddressSelector 
extends ItemSelector<Address> {
	
	private Integer ownerId;
	
	public CustomerAddressSelector(Integer ownerId) {
		this.ownerId = ownerId;
	}
	
	public CustomerAddressSelector(AbstractPerson person) {
		this.ownerId = person.getId();
	}
	
	public CustomerAddressSelector(ItemSelector<? extends AbstractPerson> ownerSelector) {
		ownerSelector.addPropertyChangeListener(ITEM_PROPERTY, (evt) -> {
			AbstractPerson customer = (AbstractPerson) evt.getNewValue();
			
			if (customer != null) {
				setCustomerId(customer.getId());
			}
		});
	}
	
	public Integer getCustomerId() {
		return ownerId;
	}
	
	public void setCustomerId(Integer customerId) {
		this.ownerId = customerId;
	}
	
	@Override
	protected YPCAction initializeSelectAction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected YPCAction initializeViewAction() {
		return new OpenCustomerAddressViewDialogAction(this);
	}

	@Override
	protected String formatItemLabel() {
		return getItem().getId().toString();
	}

}
