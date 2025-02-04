package com.pinguela.yourpc.desktop.components;

import java.awt.BorderLayout;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.JPanel;

import com.pinguela.yourpc.model.Attribute;

@SuppressWarnings("serial")
public class AttributeValueInputPane 
extends InputPane<Attribute<?>> {
	
	private JPanel contentPane;
	private AttributeEditorPane<?> editorPane;
	
	private Attribute<?> attribute;
	
	protected AttributeValueInputPane(Attribute<?> attribute) {
		this(attribute, null, true);
	}
	
	public AttributeValueInputPane(Attribute<?> attribute, boolean showUnassignedValues) {
		this(attribute, null, showUnassignedValues);
	}
	
	public AttributeValueInputPane(Attribute<?> attribute, Integer handlingMode, boolean showUnassignedValues) {
		super(String.format("Select values for %s:", attribute.getName()));
		this.attribute = attribute;
		initializeEditor(attribute, handlingMode, showUnassignedValues);
	}

	@Override
	protected JPanel initializeContentPane() {
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		return contentPane;
	}
	
	private void initializeEditor(Attribute<?> attribute, Integer handlingMode, boolean showUnassignedValues) {
		editorPane = (AttributeEditorPane<?>) AttributeEditorPane.getInstance(attribute, handlingMode, showUnassignedValues);
		contentPane.add(editorPane, BorderLayout.CENTER);
	}

	@Override
	public Attribute<?> getInput() {
		
		Collection<?> editorPaneValues = editorPane.getEditorValues();
		
		Iterator<?> iterator = editorPaneValues.iterator();
		Object[] array = (Object[]) Array.newInstance(attribute.getTypeParameterClass(), editorPaneValues.size());
		for (int i = 0; i < array.length; i++) {
			array[i] = iterator.next();
		}
		return Attribute.getInstance(attribute.getName(), array);
	}

}
