package FinalProject.Strategy;

import FinalProject.Cell;

public class HorizontalFirstStrategy implements RouteStrategy {
	
	private String moveUntilIntersection = null;
	
	/**
	 * A Car will always try to first move horizontally towards their Destination.
	 * If they cannot do so they will instead opt to move vertically.
	 *
	 * @param current the Cell representing a Car's current position of the grid
	 * @param destination the Cell representing a Car's travel Destination
	 * @return the next Cell the Car should move to
	 */
	@Override
	public Cell nextCell(Cell current, Cell destination) {
		
		// Forces this Car to move in a given direction until an intersection is reached
		if(this.moveUntilIntersection != null) {
			switch(this.moveUntilIntersection){
				case "north":
					Cell north = current.getNorth();
					if(north.isTypeIntersection()) {
						this.moveUntilIntersection = null;
					}
					return north;
				case "east":
					Cell east = current.getEast();
					if(east.isTypeIntersection()) {
						this.moveUntilIntersection = null;
					}
					return east;
				case "south":
					Cell south = current.getSouth();
					if(south.isTypeIntersection()) {
						this.moveUntilIntersection = null;
					}
					return south;
				case "west":
					Cell west = current.getWest();
					if(west.isTypeIntersection()) {
						this.moveUntilIntersection = null;
					}
					return west;
			}
		}
		
		// Move horizontally first
		if (current.getCol() < destination.getCol()) {
			Cell east = current.getEast();
			if (east != null && east.canDriveOn()) {
				return east;
			}
		}
		
		if(current.getCol() > destination.getCol()) {
			Cell west = current.getWest();
			if (west != null && west.canDriveOn()) {
				return west;
			}
		}
		
		// Move vertically if the Car cannot move horizontally
		if (current.getRow() < destination.getRow()) {
			Cell north = current.getNorth();
			if (north != null && north.canDriveOn()) {
				return north;
			}
		}
		
		if (current.getRow() > destination.getRow()) {
			Cell south = current.getSouth();
			if (south != null && south.canDriveOn()) {
				return south;
			}
		}
		
		// If all else fails then move in a vertical direction until you reach an intersection
		Cell north = current.getNorth();
		if(north != null && north.canDriveOn()) {
			this.moveUntilIntersection = "north";
			return current;
		}
		Cell east = current.getEast();
		if(east != null && east.canDriveOn()) {
			this.moveUntilIntersection = "east";
			return current;
		}
		Cell south = current.getSouth();
		if(south != null && south.canDriveOn()) {
			this.moveUntilIntersection = "south";
			return current;
		}
		Cell west = current.getWest();
		if(west != null && west.canDriveOn()) {
			this.moveUntilIntersection = "west";
			return current;
		}
		
		// Car is stuck...
		return current;
	}
}