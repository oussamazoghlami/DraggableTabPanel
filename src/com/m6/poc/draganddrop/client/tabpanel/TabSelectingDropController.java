package com.m6.poc.draganddrop.client.tabpanel;

import com.allen_sauer.gwt.dnd.client.AbstractDragController;
import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.drop.AbstractDropController;
import com.google.gwt.user.client.ui.Widget;

/**
 * Class modeling the drop controller associated to each TabPanel Header
 * 
 * @author ozoghlami
 * 
 */
public class TabSelectingDropController extends AbstractDropController {

	private final int tabIndex;
	private DraggableTabPanel tabPanel;

	public TabSelectingDropController(Widget tabWidgetPanel, DraggableTabPanel tabPanel, int tabIndex) {
		super(tabWidgetPanel);
		this.tabPanel = tabPanel;
		this.tabIndex = tabIndex;
	}

	@Override
	public void onDrop(DragContext context) {
		Widget tabWidget = context.draggable;
		if (null != tabWidget) {
			int widgetIndex = tabPanel.getTabIndex(tabWidget);
			tabPanel.insert(tabPanel.getWidget(widgetIndex), tabWidget, tabIndex);
			tabPanel.selectTab(tabPanel.getTabIndex(tabWidget));
		}
		super.onDrop(context);
	}

	@Override
	public void onEnter(DragContext context) {
		super.onEnter(context);
		tabPanel.selectTab(tabIndex);
		((AbstractDragController) context.dragController).resetCache();
	}

}
