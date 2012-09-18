package com.m6.poc.draganddrop.client.tabpanel;

import java.util.HashMap;
import java.util.Map;

import com.allen_sauer.gwt.dnd.client.DragEndEvent;
import com.allen_sauer.gwt.dnd.client.DragHandler;
import com.allen_sauer.gwt.dnd.client.DragStartEvent;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is our custom Tab Panel widget allowing to drag tab between them
 * 
 * @author Oussama ZOGHLAMI
 * 
 */
public class DraggableTabPanel extends TabLayoutPanel {

	CustomDragController dragController;
	Map<Widget, Integer> tabIndex = new HashMap<Widget, Integer>();

	/**
	 * 
	 * @param barHeight
	 * @param barUnit
	 */
	public DraggableTabPanel(double barHeight, Unit barUnit) {
		super(barHeight, barUnit);
		dragController = new CustomDragController(RootPanel.get(), false);
		dragController.setBehaviorDragProxy(true);
		addDragHandler();
	}

	@Override
	public boolean remove(int index) {
		boolean result = super.remove(index);
		if (result) {
			refreshDropControllers();
		}
		return result;
	}

	@Override
	public boolean remove(Widget w) {
		boolean result = super.remove(w);
		if (result) {
			refreshDropControllers();
		}
		return result;
	}

	@Override
	public void insert(Widget child, Widget tab, int beforeIndex) {
		AbsolutePanel panel = new AbsolutePanel();
		panel.add(tab);
		super.insert(child, panel, beforeIndex);
		dragController.makeDraggable(tab);
		this.refreshDropControllers();
	}

	@Override
	public void clear() {
		super.clear();
		this.refreshDropControllers();
	}

	@Override
	public Widget getTabWidget(int index) {
		return getTabWidgetPanel(index).getWidget(0);
	}

	/**
	 * Method allowing to return the widget panel associated to the Tab's header
	 * 
	 * @param index
	 * @return
	 */
	public AbsolutePanel getTabWidgetPanel(int index) {
		return (AbsolutePanel) super.getTabWidget(index);
	}

	/**
	 * Method allowing to refresh the drop controllers and the indexes associated to each tab header
	 */
	public void refreshDropControllers() {
		dragController.unregisterDropControllers();
		tabIndex.clear();
		for (int i = 0; i < this.getWidgetCount(); i++) {
			// refresh the controllers
			AbsolutePanel headerPanel = this.getTabWidgetPanel(i);
			TabSelectingDropController tabSelectingDropController = new TabSelectingDropController(headerPanel, this, i);
			dragController.registerDropController(tabSelectingDropController);

			// refresh the header index
			Widget headerTab = headerPanel.getWidget(0);
			tabIndex.put(headerTab, i);
		}
	}

	/**
	 * Method allowing to return the index associated to a given tab header widget
	 * 
	 * @param tabHeader
	 * @return associated tab panel
	 */
	public int getTabIndex(Widget tabHeader) {
		return tabIndex.get(tabHeader);
	}

	/**
	 * Add a drag handler
	 */
	private void addDragHandler() {
		dragController.addDragHandler(new DragHandler() {

			@Override
			public void onPreviewDragStart(DragStartEvent event) throws VetoDragException {
			}

			@Override
			public void onPreviewDragEnd(DragEndEvent event) throws VetoDragException {
			}

			@Override
			public void onDragStart(DragStartEvent event) {
				Widget draggableWidget = event.getContext().draggable;
				if (draggableWidget != null) {
					DraggableTabPanel.this.selectTab(tabIndex.get(draggableWidget));
				}
			}

			@Override
			public void onDragEnd(DragEndEvent event) {
			}
		});
	}
}
