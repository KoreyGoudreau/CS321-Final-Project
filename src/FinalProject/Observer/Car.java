package FinalProject.Observer;

import FinalProject.Cell;
import FinalProject.Factory.Destination;
import FinalProject.Factory.Home;
import FinalProject.Grid;
import FinalProject.Strategy.*;
import FinalProject.Timer;
import Skeleton.*;

public class Car extends Unit {
	
	Grid grid;
	int speed;
	RouteStrategy routeStrategy;
	Home home;
	Destination destination;
	private Cell currentCell;
	private boolean waitingAtIntersection = false;
	private boolean reachedDestination = false;
	private String logMessage = null;
	
	public Car(Grid grid, String name, int speed, String routeStrategy, SimulationInput input) {
		super(name, input);
		this.grid = grid;
		this.speed = speed;
		
		switch (routeStrategy) {
			case "horizontalfirst":
				this.routeStrategy = new HorizontalFirstStrategy();
				break;
			case "verticalfirst":
				this.routeStrategy = new VerticalFirstStrategy();
				break;
			case "zigzag":
				this.routeStrategy = new ZigzagStrategy();
				break;
			default:
				throw new IllegalArgumentException("Error: Cannot use invalid routeStrategy " + routeStrategy + ".");
		}
		
		// Register statistics
		this.getStats().addStatistic("CarMoves", new WorkerStatistic("CarMoves"));
		this.getStats().addStatistic("CarWaits", new WorkerStatistic("CarWaits"));
		this.getStats().addStatistic("TotalWaitTime", new WorkerStatistic("TotalWaitTime"));
		this.getStats().addStatistic("CarLog", new LogStatistic("CarLog"));
	}
	
	public void setHome(Home home) {
		this.home = home;
		this.currentCell = home.getCell();
	}
	
	public void setDestination(Destination destination) {
		this.destination = destination;
	}
	
	public void crossIntersection(CarLine carLine) {
		
		this.waitingAtIntersection = false;
		
		// Move into intersection
		this.currentCell = this.routeStrategy.nextCell(this.currentCell, this.destination.getCell());
		
		try {
			Thread.sleep(this.speed * 1000L);
		} catch (InterruptedException e) {
			e.printStackTrace(System.out);
		}
		
		// Notify CarLine that the intersection is free
		carLine.notifyCarMoved();
	}
	
	
	@Override
	public void performAction() {
		
		// Interact with home if on Home Cell
		if (this.currentCell.equals(this.home.getCell())) {
			this.home.interact(this, Timer.getTime());
		}
		
		// If already at destination, do nothing
		if (this.reachedDestination) {
			return;
		}
		
		// Check if Car just arrived at Destination
		if (this.currentCell.equals(this.destination.getCell())) {
			this.destination.interact(this, Timer.getTime());
			this.reachedDestination = true;
			this.logMessage = "Car \"" + this.getName() + "\" has reached destination \"" + this.destination + "\" at time " + Timer.getTime() + " (row = " + this.currentCell.getRow() + ", col = " + this.currentCell.getCol() + ")" + ".";
			return;
		}
		
		// Decide next cell using route strategy
		Cell nextCell = this.routeStrategy.nextCell(this.currentCell, this.destination.getCell());
		
		// If next cell is NOT an intersection, move Car
		if (!nextCell.isTypeIntersection()) {
			this.currentCell = nextCell;
			try {
				Thread.sleep(this.speed * 1000L);
			} catch (InterruptedException e) {
				e.printStackTrace(System.out);
			}
			
			this.logMessage = "Car \"" + this.getName() + "\" has moved to (row = " + currentCell.getRow() + ", col = " + currentCell.getCol() + ").";
			return;
		}
		
		// The next cell is an intersection, have Car figure out which direction it is currently facing to use CarLine
		Intersection intersection = nextCell.getIntersection();
		String direction;
		
		if (this.currentCell.getRow() > nextCell.getRow()) {
			direction = "north";
		} else if (this.currentCell.getRow() < nextCell.getRow()) {
			direction = "south";
		} else if (this.currentCell.getCol() < nextCell.getCol()) {
			direction = "east";
		} else {
			direction = "west";
		}
		
		// Car is already waiting in a CarLine, do nothing
		if (this.waitingAtIntersection) {
			this.logMessage = "Car \"" + getName() + "\" waited at intersection at time " + Timer.getTime() + " (row = " + this.currentCell.getRow() + ", col = " + this.currentCell.getCol() + ")" + ".";
			return;
		}
		
		// First time reaching this intersection, join the appropriate CarLine
		this.waitingAtIntersection = true;
		intersection.getCarLine(direction).addCar(this);
		this.logMessage = "Car \"" + getName() + "\" reached intersection at time " + Timer.getTime() + " (row = " + this.currentCell.getRow() + ", col = " + this.currentCell.getCol() + ")" + ".";
	}
	
	@Override
	public void submitStatistics() {
		StatisticsContainer stats = StatisticsContainer.getInstance();
		
		// Log message in Chronological Order
		if (this.logMessage != null) {
			this.getStats().getStatistic("CarLog").addValue(this.logMessage);
			this.logMessage = null;
		}
		
		// If the car moved, then increment CarMoves in statistics
		if (!this.waitingAtIntersection && !this.reachedDestination) {
			this.getStats().getStatistic("CarMoves").addValue(1);
		}
		
		// If the car waited at an intersection, then increment CarWaits in statistics, calculate total time waiting in TotalWaitTime.
		if (this.waitingAtIntersection) {
			this.getStats().getStatistic("CarWaits").addValue(1);
			this.getStats().getStatistic("TotalWaitTime").addValue(this.speed);
		}
	}
}