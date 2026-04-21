package FinalProject.Strategy;

import FinalProject.Cell;

public class HorizontalFirstStrategy implements RouteStrategy {
	
	private boolean moveNorthFlag = false;
	private boolean moveEastFlag = false;
	private boolean moveSouthFlag = false;
	private boolean moveWestFlag = false;
	
	/**
	 * A Car will move horizontally if doing so brings it closer to its Destination's Grid col position.
	 * If it cannot do so it will move vertically if doing so brings it closer to its Destination's Grid row position.
	 * If the Car cannot move in a way that brings it closer to the Destination, it will pick a vertical direction to move towards until hitting an intersection.
	 * However, if there is no moveable vertical direction it will try to do so with a horizontal direction.
	 *
	 * @param current     the Cell representing a Car's current position of the grid
	 * @param destination the Cell representing a Car's travel Destination
	 * @return the next Cell the Car should move to
	 */
	@Override
	public Cell nextCell(Cell current, Cell destination) {
		
		Cell north = current.getNorth();
		Cell east = current.getEast();
		Cell south = current.getSouth();
		Cell west = current.getWest();
		
		// Using Flags, move Car towards a direction until an Intersection is reached
		if (this.moveNorthFlag) {
			if (north.isTypeIntersection()) {
				this.moveNorthFlag = false;
			}
			return north;
		} else if (this.moveEastFlag) {
			if (east.isTypeIntersection()) {
				this.moveEastFlag = false;
			}
			return east;
		} else if (this.moveSouthFlag) {
			if (south.isTypeIntersection()) {
				this.moveSouthFlag = false;
			}
			return south;
		} else if (this.moveWestFlag) {
			if (west.isTypeIntersection()) {
				this.moveWestFlag = false;
			}
			return west;
		}
		
		// Try to move horizontally first
		if (current.getCol() < destination.getCol()) {
			if (east != null && east.canDriveOn()) {
				return east;
			}
		}
		
		if (current.getCol() > destination.getCol()) {
			if (west != null && west.canDriveOn()) {
				return west;
			}
			
		}
		
		// Move Car vertically if it cannot move horizontally
		if (current.getRow() < destination.getRow()) {
			if (north != null && north.canDriveOn()) {
				return north;
			}
		}
		
		if (current.getRow() > destination.getRow()) {
			if (south != null && south.canDriveOn()) {
				return south;
			}
		}
		
		// If a Car cannot move anywhere, then pick a vertical direction to move towards until an Intersection is reached
		// If there is no valid vertical direction, then pick a horizontal direction to move towards until an Intersection is reached
		if (north != null && north.canDriveOn()) {
			this.moveNorthFlag = true;
			return north;
		} else if (south != null && south.canDriveOn()) {
			this.moveSouthFlag = true;
			return south;
		} else if (east != null && east.canDriveOn()) {
			this.moveEastFlag = true;
			return east;
		} else if (west != null && west.canDriveOn()) {
			this.moveWestFlag = true;
			return west;
		}
		
		// Car is stuck...
		return current;
	}
}
