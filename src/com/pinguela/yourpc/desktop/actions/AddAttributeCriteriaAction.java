package com.pinguela.yourpc.desktop.actions;

import com.pinguela.yourpc.desktop.view.ProductSearchView;
import com.pinguela.yourpc.model.Attribute;
import com.pinguela.yourpc.model.ProductCriteria;

public class AddAttributeCriteriaAction 
extends GetAttributeInputAction<ProductSearchView> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9059215477248422723L;
	
	public AddAttributeCriteriaAction(ProductSearchView view) {
		super("Add...", view);
	}

	@Override
	protected Short getCategoryId() {
		ProductCriteria criteria = getView().getCriteria();
		return criteria.getCategoryId();
	}

	@Override
	protected void onConfirm() {
		Attribute<?> attribute = getInput();
		getView().addAttribute(attribute);
	}

}
