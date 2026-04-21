package FinalProject.Strategy;

import FinalProject.Cell;

public class ZigzagStrategy implements RouteStrategy {
	
	// Start moving horizontally
	private boolean movingHorizontally = true;
	
	/**
	 * A Car will start of by trying to move vertically towards their Destination.
	 * If they cannot move vertically or if they move on top of a Cell of type "intersection", they will then alternate their movement.
	 * For example, a Car moves north until they hit an intersection, after which they turn east.
	 * Then the Car moves east until reaching another intersection, where they turn north.
	 * Then the Car moves until they reach their Destination.
	 *
	 * @param current the Cell representing a Car's current position of the grid
	 * @param destination the Cell representing a Car's travel Destination
	 * @return the next Cell the Car should move to
	 */
	@Override
	public Cell nextCell(Cell current, Cell destination) {
		
		if(this.movingHorizontally) {
			
			// Move horizontally
			if(current.getCol() < destination.getCol()) {
				Cell east = current.getEast();
				if(east != null && east.canDriveOn()) {
					return east;
				}
			}
			
			if(current.getCol() > destination.getCol()) {
				Cell west = current.getWest();
				if(west != null && west.canDriveOn()) {
					return west;
				}
			}
			
			// If you cannot move horizontally, then move vertically
			this.movingHorizontally = false;
		}
		
		if (current.getRow() > destination.getRow()) {
			Cell south = current.getSouth();
			if (south != null && south.canDriveOn()) {
				return south;
			}
		}
		
		if(current.getRow() < destination.getRow()) {
			Cell north = current.getNorth();
			if(north != null && north.canDriveOn()) {
				return north;
			}
		}
		
		// If you cannot move vertically, then move horizontally
		this.movingHorizontally = true;
		
		// Move horizontally
		if(current.getCol() < destination.getCol()) {
			Cell east = current.getEast();
			if(east != null && east.canDriveOn()) {
				return east;
			}
		}
		
		if(current.getCol() > destination.getCol()) {
			Cell west = current.getWest();
			if(west != null && west.canDriveOn()) {
				return west;
			}
		}
		
		// If you cannot move horizontally, then move vertically
		this.movingHorizontally = false;
		
		// Car is stuck...
		return current;
	}
}