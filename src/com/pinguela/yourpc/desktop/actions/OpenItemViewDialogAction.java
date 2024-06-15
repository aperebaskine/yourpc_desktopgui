package com.pinguela.yourpc.desktop.actions;

import java.awt.event.ActionEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.yourpc.desktop.components.ItemSelector;
import com.pinguela.yourpc.desktop.dialog.YPCDialog;
import com.pinguela.yourpc.desktop.view.EntityView;
import com.pinguela.yourpc.desktop.view.YPCView;

public class OpenItemViewDialogAction<T>
extends OpenDialogAction<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6918342053262863916L;
	
	private static Logger logger = LogManager.getLogger(OpenItemViewDialogAction.class);
	
	private Class<? extends EntityView<T>> target;
	private ItemSelector<T> selector;
	
	public <V extends EntityView<T>, S extends ItemSelector<T>> OpenItemViewDialogAction(Class<V> target, S selector) {
		super("View");
		this.target = target;
		this.selector = selector;
	}
	
	protected EntityView<T> initializeView() throws Exception {
		return (EntityView<T>) target.getDeclaredConstructor().newInstance();
	}

	@Override
	protected YPCDialog createDialog(ActionEvent e) {
		EntityView<T> dialogView;
		
		try {
			dialogView = initializeView();
			dialogView.setItem(selector.getItem());
		} catch (Exception e1) {
			logger.error(e1.getMessage(), e1);
			throw new IllegalStateException(e1.getMessage(), e1);
		} 
		
		YPCDialog dialog = new YPCDialog(null, (YPCView) dialogView);
		dialog.setTitle("Item view");
		return dialog;
	}

	@Override
	protected boolean shouldSetRelativeLocation() {
		return false;
	}

	@Override
	protected void onClose() {}

}
