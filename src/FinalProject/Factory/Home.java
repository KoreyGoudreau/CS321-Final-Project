package FinalProject.Factory;

import FinalProject.Observer.Car;
import FinalProject.Cell;
import FinalProject.Timer;

public class Home extends Building{
	
	public Home(Cell cell, String buildingName) {
		super(cell, buildingName);
	}
	
	/**
	 * Prints out a message saying the name and time that a Car leaves this building. 
	 *
	 * @param car the Car interacting with this building
	 * @param time the time at which this interaction occurred
	 */
	public void interact(Car car, int time) {
		System.out.println("Car \"" + car.getName() + "\" is exiting Home \"" + this.buildingName + "\" at time " + Timer.getTime() + " (row = " + this.cell.getRow() + ", col = " + this.cell.getCol() + ")" + ".");
	}
}
