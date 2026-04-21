package FinalProject.Factory;

import FinalProject.Car;
import FinalProject.Cell;
import Skeleton.Statistic;
import Skeleton.StatisticsContainer;

public class Home extends Building{
	
	public Home(Cell cell, String buildingName) {
		super(cell, buildingName);
	}
	
	/**
	 * Adds a message into a Car's log (from Statistics) when and where a Car left this building.
	 *
	 * @param car the Car interacting with this building
	 * @param time the time at which this interaction occurred
	 */
	public void interact(Car car, int time) {
		// Add a message into Car's statistic CarLog
		Statistic stat = StatisticsContainer.getInstance(car.getSimInput()).getComponent(car.getName()).getStatistic("CarLog");
		stat.addValue("(Time = " + time + ", row = " + this.cell.getRow() + ", col = " + this.cell.getCol() + ", Action = Exiting Home \"" + this.getBuildingName() + "\")");
	}
}
