package edu.andover.coolpool.controller;

import com.sun.javafx.geom.Shape;

import edu.andover.coolpool.GameConstants;
import edu.andover.coolpool.model.CueStick;
import edu.andover.coolpool.view.PoolBoardView;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class CueStickController {

	private boolean isMousePressed;
	private double mouseX;
	private double mouseY;
	
	public void addMousePressedEventHandler(PoolBoardView pbv, CueStick cueStick) {
	    Line l = (Line) cueStick.getView();
		l.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
	    	@Override
	    	public void handle(MouseEvent me) {
	    		isMousePressed = true;
	    		cueStick.getView().setStroke(Color.PINK);
	    		
	    	}
	    });
		
		Rectangle r = pbv.getCueStickRectangle();
		r.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
	    	@Override
	    	public void handle(MouseEvent me) {
	    		isMousePressed = true;
	    		cueStick.getView().setStroke(Color.PINK);
	    	}
	    });
	}
	
	public void addMouseHoverEventHandler(PoolBoardView pbv, CueStick cueStick) {
	    Rectangle r = pbv.getCueStickRectangle();
		r.addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
			@Override
	    	public void handle(MouseEvent me) {
				if (!isMousePressed) {
	    		mouseX = me.getX()*GameConstants.PIXEL_TO_IN;
	    		mouseY = me.getY()*GameConstants.PIXEL_TO_IN;
	    		cueStick.setHoverCueStickLocation(mouseX, mouseY);
				}
	    	}
	    });
	}

	public void addMouseDraggedEventHandler(PoolBoardView pbv, CueStick cueStick) {
	    Rectangle r = pbv.getCueStickRectangle();
		r.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
	    	@Override
	    	public void handle(MouseEvent me) {
	    		double initMouseX = mouseX;
	    		double initMouseY = mouseY;
	    		double endMouseX = me.getX()*GameConstants.PIXEL_TO_IN;
	    		double endMouseY = me.getY()*GameConstants.PIXEL_TO_IN;
	    		if (cueStick.getDistance(initMouseX, initMouseY, endMouseX, endMouseY) < 20) {
	    		cueStick.setCueStickLocationOnDrag(initMouseX, initMouseY, endMouseX, endMouseY);
	    		cueStick.getView().setStroke(Color.BLUE);
	    		}
	    	}
	    });
		
	    Line l = (Line) cueStick.getView();
		l.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
	    	@Override
	    	public void handle(MouseEvent me) {
	    		double initMouseX = mouseX;
	    		double initMouseY = mouseY;
	    		double endMouseX = me.getX()*GameConstants.PIXEL_TO_IN;
	    		double endMouseY = me.getY()*GameConstants.PIXEL_TO_IN;
	    		if (cueStick.getDistance(initMouseX, initMouseY, endMouseX, endMouseY) < 20) {
	    		cueStick.setCueStickLocationOnDrag(initMouseX, initMouseY, endMouseX, endMouseY);
	    		cueStick.getView().setStroke(Color.BLUE);
	    		}
	    	}
	    });
	}
	
	public void addMouseReleasedEventHandler(PoolBoardView pbv, CueStick cueStick) {
	  Line l = (Line) cueStick.getView();
		l.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
	    	@Override
	    	public void handle(MouseEvent me) {
	    		double initMouseDragX = mouseX;
	    		double initMouseDragY = mouseY;
	    		
	    		double endMouseDragX = me.getX()*GameConstants.PIXEL_TO_IN;
	    		double endMouseDragY = me.getY()*GameConstants.PIXEL_TO_IN;
	    		
	    		cueStick.setCueStickLocationAfterHit(mouseX, mouseY);
	    		cueStick.updateCueBallVelocity(initMouseDragX, endMouseDragX, 
	    				initMouseDragY, endMouseDragY);
	    		
	    		isMousePressed = false;
	    		cueStick.getView().setStroke(Color.BROWN);
	    	}
	    });
		Rectangle r = pbv.getCueStickRectangle();
		r.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
	    	@Override
	    	public void handle(MouseEvent me) {
	    		double initMouseDragX = mouseX;
	    		double initMouseDragY = mouseY;
	    		
	    		double endMouseDragX = me.getX()*GameConstants.PIXEL_TO_IN;
	    		double endMouseDragY = me.getY()*GameConstants.PIXEL_TO_IN;
	    		
	    		cueStick.setCueStickLocationAfterHit(mouseX, mouseY);
	    		cueStick.updateCueBallVelocity(initMouseDragX, endMouseDragX, 
	    				initMouseDragY, endMouseDragY);
	    		
	    		cueStick.updateCueBallVelocity(initMouseDragX, endMouseDragX, 
	    				initMouseDragY, endMouseDragY);
	    		
	    		isMousePressed = false;
	    		cueStick.getView().setStroke(Color.BROWN);
	    	}
	    });
		
		
	}
}