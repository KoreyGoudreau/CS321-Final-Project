package FinalProject.Factory;

import FinalProject.Car;
import FinalProject.Cell;
import FinalProject.Skeleton.Statistic;
import FinalProject.Skeleton.StatisticsContainer;

public class Destination extends Building {
	int openingTime;
	
	public Destination(Cell cell, String buildingName, int openingTime) {
		super(cell, buildingName);
		this.openingTime = openingTime;
	}
	
	/**
	 * Adds a message into a Car's log (from Statistics) on how late/early/on-time an arriving Car is relative to this Destination's opening time.
	 *
	 * @param car  the Car interacting with this building
	 * @param time the time at which this interaction occurred
	 */
	public void interact(Car car, int time) {
		int arrivalTime = this.openingTime - time;
		
		// Add a message into Car's statistic CarLog
		Statistic stat = StatisticsContainer.getInstance(car.getSimInput()).getComponent(car.getName()).getStatistic("CarLog");
		stat.addValue("(Time = " + time + ", row = " + this.cell.getRow() + ", col = " + this.cell.getCol() + ", Action = Reached Destination \"" + this.getBuildingName() + "\")");
		
		if (arrivalTime > 0) {
			stat.addValue("This Car is early by " + (arrivalTime) + " minutes!");
			return;
		} else if (arrivalTime == 0) {
			stat.addValue("This Car is right on time!");
			return;
		}
		stat.addValue("This Car is late by " + (arrivalTime * -1) + " minutes!");
	}
}