package com.pinguela.yourpc.desktop.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.Action;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import com.pinguela.yourpc.desktop.actions.ResetCriteriaAction;
import com.pinguela.yourpc.desktop.actions.SearchAction;
import com.pinguela.yourpc.desktop.actions.SearchActionBuilder;
import com.pinguela.yourpc.desktop.components.ActionPane;

@SuppressWarnings("serial")
public abstract class AbstractSearchView<T> 
extends YPCView
implements SearchView<T> {

	private JPanel searchCriteriaPanel;
	private ActionPane actionPane;
	private JScrollPane resultScrollPane;
	private JTable table;
	private JButton searchButton;

	public AbstractSearchView(SearchActionBuilder<T, ? extends SearchAction<T>> builder) {
		
		JPanel searchPanel = new JPanel();
		add(searchPanel, BorderLayout.NORTH);
		searchPanel.setLayout(new BorderLayout(0, 0));

		searchCriteriaPanel = new JPanel();
		searchPanel.add(searchCriteriaPanel);

		actionPane = new ActionPane();
		FlowLayout flowLayout = (FlowLayout) actionPane.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		searchPanel.add(actionPane, BorderLayout.SOUTH);

		searchButton = new JButton(builder.setView(this).build());
		actionPane.add(searchButton);
		addActionButton(new ResetCriteriaAction(this));

		Component verticalStrut = Box.createVerticalStrut(40);
		verticalStrut.setPreferredSize(new Dimension(0, 50));
		actionPane.add(verticalStrut);

		JPanel resultPanel = new JPanel();
		resultPanel.setVisible(true);
		add(resultPanel, BorderLayout.CENTER);
		resultPanel.setLayout(new BorderLayout(0, 0));

		resultScrollPane = new JScrollPane();
		resultPanel.add(resultScrollPane);
		
		this.table = new JTable();
		resultScrollPane.setViewportView(table);
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				if (getRootPane() != null) {
					getRootPane().setDefaultButton(searchButton);
				}
			}
			@Override
			public void componentHidden(ComponentEvent e) {
				if (getRootPane() != null) {
					getRootPane().setDefaultButton(null);
				}
			}
		});
	}

	protected JPanel getCriteriaPanel() {
		return searchCriteriaPanel;
	}

	// TODO: Delegate methods
	public JTable getTable() {
		return table;
	}
	
	public final void addSearchAction(ActionListener listener) {
		searchButton.addActionListener(listener);
	}
	
	public final void addActionButton(Action action) {
		actionPane.addAction(action);
	}

	@Override
	public final void setModel(TableModel model) {
		TableModel old = table.getModel();
		table.setModel(model);
		firePropertyChange(TABLE_MODEL_PROPERTY, old, model);
	}
	
	@Override
	public final void resetCriteriaFields(Object source) {
		doResetCriteriaFields(source);
		firePropertyChange(CRITERIA_PROPERTY, null, null);
	}
	
	@Override
	public void doSearch() {
		searchButton.getAction().actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
	}
	
	protected abstract void doResetCriteriaFields(Object source);

}
