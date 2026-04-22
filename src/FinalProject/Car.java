package FinalProject;

import FinalProject.Factory.Destination;
import FinalProject.Factory.Home;
import FinalProject.Observer.CarLine;
import FinalProject.Strategy.*;
import FinalProject.Skeleton.*;

public class Car extends Unit {
	
	private final int speed;
	private final RouteStrategy routeStrategy;
	private Home home;
	private Destination destination;
	private Cell currentCell;
	private final Statistic carLog;
	private boolean waitingAtIntersection = false;
	private boolean DestinationReached = false;
	
	public Car(String name, int speed, String routeStrategy, SimulationInput input) {
		super(name, input);
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
		
		// Register Statistic for number of car moves (CarMoves)
		this.getStats().addStatistic("CarMoves", new WorkerStatistic("CarMoves"));
		
		// Register Statistic for total wait time (TotalWaitTime)
		this.getStats().addStatistic("TotalWaitTime", new WorkerStatistic("TotalWaitTime"));
		
		// Register Statistic for a chronological log of actions a Car takes
		this.carLog = this.getStats().addStatistic("CarLog", new LogStatistic("CarLog"));
		
		// Log a message containing info on Car name and strategy used
		this.carLog.addValue("Car \"" + this.getName() + "\" is using Strategy \"" + routeStrategy + "\".");
	}
	
	public void setHome(Home home) {
		this.home = home;
		this.currentCell = home.getCell();
		
		// Log in a message about this Car's assigned Home
		this.getStats().getStatistic("CarLog").addValue("Assigned Home \"" + this.home.getBuildingName() + "\" (row = " + this.home.getCell().getCol() + ", row = " + this.home.getCell().getRow() + ")");
	}
	
	public void setDestination(Destination destination) {
		this.destination = destination;
		
		// Log in a message about this Car's assigned Destination
		this.getStats().getStatistic("CarLog").addValue("Assigned Destination \"" + this.destination.getBuildingName() + "\" (row = " + this.destination.getCell().getCol() + ", row = " + this.destination.getCell().getRow() + ")");
	}
	
	/**
	 * When a Car reaches an intersection, it adds itself to the appropriate CarLine, which is a line of waiting Cars.
	 * When a CarLine is notified by the Stoplight it will allow Cars at the front of the line to cross the intersection.
	 * To do so it will call this method, letting this Car know that it can start moving again.
	 * Only 1 Car can cross at a time, needing to first wait for the Car that is currently crossing to move once.
	 *
	 * @param carLine the line of Cars at the intersection
	 */
	public void crossIntersection(CarLine carLine) {
		
		this.waitingAtIntersection = false;
		
		// Move into Intersection
		this.currentCell = this.routeStrategy.nextCell(this.currentCell, this.destination.getCell());
		try {
			Thread.sleep(this.speed * 1000L);
		} catch (InterruptedException e) {
			e.printStackTrace(System.out);
		}
		
		// Log in a message about this Car's movement
		this.carLog.addValue("(Time = " + Timer.getTime() + ", row = " + this.currentCell.getRow() + ", col = " + this.currentCell.getCol() + ", Action = Moved)");
		
		// Notify CarLine that the next Car in the line can start crossing
		carLine.notifyCarMoved();
	}
	
	@Override
	public void performAction() {
		
		// If Car has already reached its destination, do nothing
		if (this.DestinationReached) {
			return;
		}
		
		// If Destination is reached, interact with it and set DestinationReached to true
		if (this.currentCell.equals(this.destination.getCell())) {
			this.destination.interact(this, Timer.getTime());
			this.DestinationReached = true;
			return;
		}
		
		// At start of simulation, interact with Home (Car's starting point)
		if (this.currentCell.equals(this.home.getCell())) {
			this.home.interact(this, Timer.getTime());
		}
		
		// Use routeStrategy to determine next Cell to move towards.
		Cell nextCell = this.routeStrategy.nextCell(this.currentCell, this.destination.getCell());
		
		// Determine Which direction Car is facing based on where it is headed
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
		
		// If the next cell is an Intersection determine if the Car should move or not
		if (nextCell.isTypeIntersection()) {
			
			// If this Car was already waiting at this Intersection, then continue waiting
			if (this.waitingAtIntersection) {
				// Log in a message about this Car's waiting
				this.carLog.addValue("(Time = " + Timer.getTime() + ", row = " + this.currentCell.getRow() + ", col = " + this.currentCell.getCol() + ", Action = Waited at Intersection)");
				return;
			}
			
			// First time reaching this Intersection, set this Car to waiting and join the appropriate CarLine
			this.waitingAtIntersection = true;
			nextCell.getIntersection().getCarLine(direction).addCar(this);
			
			// Log in a message about this Car reaching an intersection
			this.carLog.addValue("(Time = " + Timer.getTime() + ", row = " + this.currentCell.getRow() + ", col = " + this.currentCell.getCol() + ", Action = Reached Intersection)");
		} else {
			// Move the Car if the next Cell is not an Intersection
			
			// The Car takes a number of seconds to move equal to its speed
			this.currentCell = nextCell;
			try {
				Thread.sleep(this.speed * 1000L);
			} catch (InterruptedException e) {
				e.printStackTrace(System.out);
			}
			
			// Log in a message about this Car's movement
			this.carLog.addValue("(Time = " + Timer.getTime() + ", row = " + this.currentCell.getRow() + ", col = " + this.currentCell.getCol() + ", Action = Moved)");
		}
	}
	
	@Override
	public void submitStatistics() {
		
		// If the car moved, then increment CarMoves in statistics
		if (!this.waitingAtIntersection && !this.DestinationReached) {
			this.getStats().getStatistic("CarMoves").addValue(1);
		}
		
		// If the car waited at an Intersection, then increment CarWaits in statistics
		if (this.waitingAtIntersection) {
			this.getStats().getStatistic("TotalWaitTime").addValue(1);
		}
	}
}