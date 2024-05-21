package com.pinguela.yourpc.desktop.view;

import java.awt.BorderLayout;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.border.EmptyBorder;

import com.pinguela.yourpc.desktop.components.ImageGallery;
import com.pinguela.yourpc.model.ImageEntry;

public abstract class AbstractImageGalleryItemView<T>
extends AbstractItemView<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3702324745329166746L;

	private ImageGallery imageGalleryPanel;
	private PropertyChangeListener editorListener = (evt) -> {
		imageGalleryPanel.setEditable(isEditable());
	};

	public AbstractImageGalleryItemView() {
		addPropertyChangeListener(CARD_PROPERTY, editorListener);
		add(getViewPanel(), BorderLayout.WEST);

		imageGalleryPanel = new ImageGallery();
		imageGalleryPanel.setBorder(new EmptyBorder(0, 16, 0, 0));
		add(imageGalleryPanel, BorderLayout.CENTER);

		addPropertyChangeListener(CARD_PROPERTY, (evt) -> {
			imageGalleryPanel.setEditable(EDITOR_CARD.equals(evt.getNewValue()));
		});
	}

	public void addImage(ImageEntry entry) {
		imageGalleryPanel.addImage(entry);
	}

	public void addImages(List<ImageEntry> entries) {
		imageGalleryPanel.addImages(entries);
	}
	
	public void clearImages() {
		imageGalleryPanel.clearImages();
	}

	public List<ImageEntry> getModifiedImageEntries() {
		return imageGalleryPanel.getImages();
	}

}
