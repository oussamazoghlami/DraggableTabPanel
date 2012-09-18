package com.m6.poc.draganddrop.client.tabpanel;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.util.DragClientBundle;
import com.allen_sauer.gwt.dnd.client.util.WidgetArea;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author ozoghlami
 *
 */
public class CustomDragController  extends PickupDragController {

	public CustomDragController(AbsolutePanel boundaryPanel, boolean allowDroppingOnBoundaryPanel) {
		super(boundaryPanel, allowDroppingOnBoundaryPanel);
	}
	
	@Override
	protected Widget newDragProxy(DragContext context) {
		AbsolutePanel container = new AbsolutePanel();
	    container.getElement().getStyle().setProperty("overflow", "visible");

	    WidgetArea draggableArea = new WidgetArea(context.draggable, null);
	    for (Widget widget : context.selectedWidgets) {
	      WidgetArea widgetArea = new WidgetArea(widget, null);
	      SimplePanel proxy = new SimplePanel();
	      if (widget instanceof HasText) {
	    	  proxy.add(new Label(((HasText) widget).getText()));
	      }
	      proxy.setPixelSize(widget.getOffsetWidth(), widget.getOffsetHeight());
	      proxy.addStyleName(DragClientBundle.INSTANCE.css().proxy());
	      container.add(proxy, widgetArea.getLeft() - draggableArea.getLeft(), widgetArea.getTop()
	          - draggableArea.getTop());
	    }

	    return container;
	}

}
