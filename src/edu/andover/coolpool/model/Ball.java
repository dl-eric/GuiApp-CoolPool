package edu.andover.coolpool.model;

import edu.andover.coolpool.view.BallView;
import javafx.scene.shape.Shape;

// Model class for a pool ball. Can represent a "solid" ball, "striped" ball,
// cue ball, or 8 ball depending on the ID passed into the constructor. The
// ID of the ball does not matter until we implement players.

public class Ball {
	private boolean isPocketed;
	private double centerX;
	private double centerY;
	private double xVelocity; //in in/s
	private double yVelocity; //in in/s
	private final double radius = 1.125; //in in
	private int id;

	private BallView ballView;

	public Ball(double centerX, double centerY, int id) {
		this.centerX = centerX;
		this.centerY = centerY;

		ballView = new BallView(centerX, centerY, radius, id);
		isPocketed = false;
	
		xVelocity = 0;
		yVelocity = 0;
		this.id = id;
	}

	public boolean getIsPocketed(){ return isPocketed; }
	public double getXVelocity(){ return xVelocity; }
	public double getYVelocity(){ return yVelocity; }
	public double getRadius(){ return radius; }
	public double getCenterX(){ return centerX; }
	public double getCenterY() { return centerY; }
	
	public Shape getView(){ return ballView.getCircle(); }

	public void setCenterX(double centerX) {
		this.centerX = centerX;
		ballView.setCenterX(this.centerX);
	}

	public void setCenterY(double centerY) {
		this.centerY = centerY;
		ballView.setCenterY(this.centerY);
	}

	public void setXVelocity(double xVel) { xVelocity = xVel;}
	public void setYVelocity(double yVel) { yVelocity = yVel;}
	
	// pockets or unpockets a ball
	// TODO: allow only cue ball to be unpocketed
	public void setPocketed() {
		isPocketed = !isPocketed;
		if (isPocketed) {
			ballView.remove();
			xVelocity = 0;
			yVelocity = 0;
		}
	}
	
	public int getId(){ return id;}
}
