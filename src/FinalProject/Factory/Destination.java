package FinalProject.Factory;

import FinalProject.Observer.Car;
import FinalProject.Cell;
import FinalProject.Timer;

public class Destination extends Building{
	int openingTime;
	
	public Destination(Cell cell, String buildingName, int openingTime) {
		super(cell, buildingName);
		this.openingTime = openingTime;
	}
	
	/**
	 * Prints out a message on how late/early/on-time an arriving Car is relative to this Destination's opening time.
	 *
	 * @param car the Car interacting with this building
	 * @param time the time at which this interaction occurred
	 */
	public void interact(Car car, int time) {
		int arrivalTime = this.openingTime - time;
		if(arrivalTime > 0) {
			System.out.println("Car \"" + car.getName() + "\" has reached Destination \"" + this.buildingName + "\" at time " + Timer.getTime() + " (row = " + this.cell.getRow() + ", col = " + this.cell.getCol() + ")" + ".");
			System.out.println("They are early by " + (arrivalTime) + " minutes!");
			return;
		} else if(arrivalTime == 0) {
			System.out.println("Car \"" + car.getName() + "\" has reached Destination \"" + this.buildingName + "\" at time " + Timer.getTime() + " (row = " + this.cell.getRow() + ", col = " + this.cell.getCol() + ")" + ".");
			System.out.println("They are right on time!");
			return;
		}
		System.out.println("Car \"" + car.getName() + "\" has reached Destination \"" + this.buildingName + "\" at time " + Timer.getTime() + " (row = " + this.cell.getRow() + ", col = " + this.cell.getCol() + ")" + ".");
		System.out.println("They are late by " + (arrivalTime * -1) + " minutes!");
	}
}