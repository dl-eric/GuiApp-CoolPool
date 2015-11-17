package edu.andover.coolpool.model;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import edu.andover.coolpool.GameManager;
import javafx.animation.AnimationTimer;

public class PoolGame implements Observer {
	//TODO: Add Comments

	// Create a reference to game manager here
	private GameManager gameManager;
	private PoolBoard poolBoard;
	private Player[] players;
	
	int currPlayerInd = 0;
	boolean gameHasEnded = false;
	boolean sidesAreSet = false;
	boolean streak = false;
	
	AnimationTimer timer;
	
	PoolGameStatus poolGameStatus = new PoolGameStatus();

	public PoolGame() {
		gameManager = GameManager.getInstance();
		
		players = new Player[2];
		players[0] = new Player();
		players[1] = new Player();

		poolBoard = new PoolBoard();
		poolBoard.getCueStick().addObserver(this);
		
		timer = new AnimationTimer() {
			@Override
			public void handle(long timestamp) {
				poolBoard.update();
				if (poolBoard.stable()) { 
					this.stop();
					updatePoints(poolBoard.pocketedBalls());
					poolBoard.resetPocketedBalls();
					poolBoard.getCueStick().setCanMove(true);
					poolBoard.getCueStick().setCanReset(true);
				}
			}
		};
		
	}

	public void turn() {
		timer.start();
	}

	public void setSides(int ballId) {
		players[currPlayerInd].setBallType(ballId);
		players[(currPlayerInd+1)%2].setBallType((ballId + 1)%2);
		poolGameStatus.setBallColors(currPlayerInd, ballId);
		sidesAreSet = true;
	}

	private boolean pocketedOther(ArrayList<Ball> pocketedBalls){
		int playerBallType = players[currPlayerInd].getBallType();
		for (Ball b: pocketedBalls){
			if (playerBallType != -1 && playerBallType != b.getId()
					&& (b.getId() == 0 || b.getId() == 1)){
				return true;
			}
		}
		return false;
	}

	public void switchPlayer() {
		currPlayerInd = (currPlayerInd + 1)%2;
		streak = false;
		poolGameStatus.setTurnStatus(currPlayerInd, streak,
				players[currPlayerInd].canPocketEightBall());
	}

	public boolean pocketedCueBall(ArrayList<Ball> pocketedBalls) {
		for (Ball b: pocketedBalls){
			if ( b.getId() == 2) return true;
		}
		return false;
	}

	public boolean pocketedEightBall(ArrayList<Ball> pocketedBalls) {
		for (Ball b: pocketedBalls){
			if ( b.getId() == 3) return true;
		}
		return false;
	}

	public void continuePlayer() {
		streak = true;
		poolGameStatus.setTurnStatus(currPlayerInd, streak, 
				players[currPlayerInd].canPocketEightBall());
	}

	public void updatePoints(ArrayList<Ball> pocketedBalls) {
		int size = pocketedBalls.size();
		if (poolBoard.getNumBumperCollisions() < 4 && size == 0) {
			poolBoard.rackBalls(poolBoard.getBalls());
			poolGameStatus.setLastTurnStatusPlayerIllegalBreak(currPlayerInd);
			switchPlayer();
		}
		else if (size == 0){
			poolGameStatus.setLastTurnStatusPlayerFailed(currPlayerInd);
			switchPlayer();
		}
		
		else {
			for (int i = 0; i < size; i ++) {
				int ballId = pocketedBalls.get(i).getId();
				if (ballId == 0 || ballId == 1){
					if (!sidesAreSet){
						setSides(ballId);
					}
					if (players[currPlayerInd].getBallType() == ballId){
						players[currPlayerInd].addPoint();
					}
					else{
						players[(currPlayerInd+1)%2].addPoint();
					}
				}
			}

			poolGameStatus.setPoints(players[0].getPoints(), 
					players[1].getPoints());

			if (pocketedEightBall(pocketedBalls)) {
				gameHasEnded = true;
				gameManager.initEndScreen();
			}
			
			else if (pocketedCueBall(pocketedBalls)) {
				scratch();
			}		
			else if (pocketedOther(pocketedBalls)) {
				poolGameStatus.setLastTurnStatusPocketedOther(currPlayerInd);
				switchPlayer();
			}
			else{
				poolGameStatus.setLastTurnStatusPlayerSucceeded(currPlayerInd);
				continuePlayer();
			}
		}
	}
	
	public void scratch(){
		poolGameStatus.setLastTurnStatusPocketedCueBall(currPlayerInd);
		poolBoard.resetCueBall();
		switchPlayer();
	}
	

	public PoolBoard getPoolBoard() {
		return poolBoard;
	}

	public Player[] getPlayers() { 
		return players; 
	}
	
	public PoolGameStatus getPoolGameStatus(){
		return poolGameStatus;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if (o == poolBoard.getCueStick()){
			if (poolBoard.getCueStick().hasHit()){
				this.turn();
			}
		}
		
	}
}