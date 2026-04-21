package FinalProject.Factory;

import FinalProject.Car;
import FinalProject.Cell;

public abstract class Building {
	
	Cell cell;
	String buildingName;
	
	public Building(Cell cell, String buildingName) {
		this.cell = cell;
		this.buildingName = buildingName;
	}
	
	public Cell getCell() {
		return this.cell;
	}
	
	public String getBuildingName() {
		return this.buildingName;
	}
	
	/**
	 * Cars can interact with Buildings by moving to a Cell on the Grid of type "building" and then using this method.
	 *
	 * @param car  the Car interacting with this building
	 * @param time the time at which this interaction occurred
	 */
	public abstract void interact(Car car, int time);
}
