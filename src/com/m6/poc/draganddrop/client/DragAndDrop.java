package com.m6.poc.draganddrop.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.m6.poc.draganddrop.client.tabpanel.DraggableTabPanel;

/**
 * POC Entry Point allowing to test the DraggableTabPanel Widget
 * 
 * @author Oussama ZOGHLAMI
 *
 *
 */
@SuppressWarnings("deprecation")
public class DragAndDrop implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		// set uncaught exception handler
		GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
			public void onUncaughtException(Throwable throwable) {
				String text = "Uncaught exception: ";
				while (throwable != null) {
					StackTraceElement[] stackTraceElements = throwable.getStackTrace();
					text += throwable.toString() + "\n";
					for (int i = 0; i < stackTraceElements.length; i++) {
						text += "    at " + stackTraceElements[i] + "\n";
					}
					throwable = throwable.getCause();
					if (throwable != null) {
						text += "Caused by: ";
					}
				}
				DialogBox dialogBox = new DialogBox(true, false);
				DOM.setStyleAttribute(dialogBox.getElement(), "backgroundColor", "#ABCDEF");
				System.err.print(text);
				text = text.replaceAll(" ", "&nbsp;");
				dialogBox.setHTML("<pre>" + text + "</pre>");
				dialogBox.center();
			}
		});

		// use a deferred command so that the handler catches onModuleLoad2() exceptions
		DeferredCommand.addCommand(new Command() {
			public void execute() {
				testDraggablePanel();
			}
		});
	}

	private void testDraggablePanel() {
		DraggableTabPanel draggableTabPanel = new DraggableTabPanel(2.5, Unit.EM);
		draggableTabPanel.add(getContentPanel("#FFFFFF"), new HTML("Blanc"));
		draggableTabPanel.add(getContentPanel("#D80000"), new HTML("Rouge"));
		draggableTabPanel.add(getContentPanel("#D8D8D8"), new HTML("Gris"));
		draggableTabPanel.add(getContentPanel("#556B2F"), new HTML("Vert"));
		draggableTabPanel.add(getContentPanel("#FFFF00"), new HTML("Jaune"));
		draggableTabPanel.add(getContentPanel("#AFEEEE"), new HTML("Bleu"));
		draggableTabPanel.setPixelSize(500, 300);
		RootPanel.get().add(draggableTabPanel);
	}

	private AbsolutePanel getContentPanel(String backgroundColor) {
		AbsolutePanel contentPanel = new AbsolutePanel();
		contentPanel.getElement().getStyle().setProperty("backgroundColor", backgroundColor);
		return contentPanel;
	}

}
