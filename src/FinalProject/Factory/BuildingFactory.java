package FinalProject.Factory;

import FinalProject.Cell;

public class BuildingFactory {
	public static Building createHome(Cell cell, String buildingName) {
		return new Home(cell, buildingName);
	}
	
	public static Building createDestination(Cell cell, String buildingName, int openingTime) {
		return new Destination(cell, buildingName, openingTime);
	}
}
