package com.pinguela.yourpc.desktop.view;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.pinguela.yourpc.desktop.components.ActionPane;

@SuppressWarnings("serial")
public abstract class AbstractEntityView<T> 
extends YPCView 
implements EntityView<T> {
	
	protected static final String CARD_PROPERTY = "card";
	protected static final String IS_EDITABLE_PROPERTY = "isEditable";
	
	private boolean isEditable = false;
	
	private PropertyChangeListener cardListener = (evt) -> {
		setEditable(EDITOR_CARD.equals(evt.getNewValue()));
	};
	
	private PropertyChangeListener editableListener = (evt) -> {
		setFieldsEditable(isEditable);
	};

	private PropertyChangeListener itemListener = (evt) -> {
		toDefaultState();
	};

	private T item;

	private JPanel centerPanel;
	private JPanel southPanel;

	private Map<String, ActionPane> cards;
	
	public AbstractEntityView() {
		centerPanel = new JPanel();
		add(centerPanel, BorderLayout.CENTER);
		GridBagLayout gbl_viewerPanel = new GridBagLayout();
		gbl_viewerPanel.columnWidths = new int[]{0};
		gbl_viewerPanel.rowHeights = new int[]{0};
		gbl_viewerPanel.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_viewerPanel.rowWeights = new double[]{Double.MIN_VALUE};
		centerPanel.setLayout(gbl_viewerPanel);

		cards = new HashMap<String, ActionPane>();
		
		addPropertyChangeListener(IS_EDITABLE_PROPERTY, editableListener);
		addPropertyChangeListener(ITEM_PROPERTY, editableListener);
		
		addPropertyChangeListener(CARD_PROPERTY, cardListener);
		addPropertyChangeListener(ITEM_PROPERTY, itemListener);
	}

	private void initializeSouthPanel() {
		southPanel = new JPanel();
		southPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
		southPanel.setLayout(new CardLayout(0, 0));
		add(southPanel, BorderLayout.SOUTH);
	}

	private void initializeCard(String name) {
		ActionPane actionPane = new ActionPane();
		FlowLayout fl_actionPane = (FlowLayout) actionPane.getLayout();
		fl_actionPane.setAlignment(FlowLayout.RIGHT);
		cards.put(name, actionPane);
		
		southPanel.add(actionPane, name);
		if (cards.size() == 1) {
			showCard(name);
		}
	}

	@Override
	public T getCurrentEntity() {
		return item;
	}

	@Override
	public void setEntity(T item) {
		T old = this.item;
		this.item = item;
		firePropertyChange(ITEM_PROPERTY, old, item);
	}

	protected JPanel getViewPanel() {
		return centerPanel;
	}

	@Override
	public void addAction(Action action) {
		addAction(action, VIEW_CARD); 
	}

	@Override
	public void addAction(Action action, String card) {
		if (southPanel == null) {
			initializeSouthPanel();
		}
		if (!cards.containsKey(card)) {	
			initializeCard(card);
		}
		cards.get(card).addAction(action);
	}

	@Override
	public boolean showCard(String cardName) {
		if (!cards.containsKey(cardName)) {
			return false;
		}
		((CardLayout) southPanel.getLayout()).show(southPanel, cardName);
		firePropertyChange(CARD_PROPERTY, null, cardName);
		return true;
	}
	
	public boolean isEditable() {
		return isEditable;
	}
	
	public void setEditable(boolean isEditable) {
		boolean old = this.isEditable;
		this.isEditable = isEditable;
		firePropertyChange(IS_EDITABLE_PROPERTY, old, isEditable);
	}
	
	public void toDefaultState() {
		if (getCurrentEntity() == null) {
			resetFields();
		} else {
			loadItemData();
		}
	}
	
	protected abstract void setFieldsEditable(boolean isEditable);
	
	protected abstract void loadItemData();

}
