package FinalProject.Strategy;

import FinalProject.Cell;

public class ZigzagStrategy implements RouteStrategy {
	
	// Start moving horizontally
	private boolean movingHorizontally = true;
	
	private boolean moveNorthFlag = false;
	private boolean moveEastFlag = false;
	private boolean moveSouthFlag = false;
	private boolean moveWestFlag = false;
	
	/**
	 * A Car will start of by trying to move vertically towards their Destination.
	 * If they cannot move vertically or if they move on top of a Cell of type "intersection", they will then alternate their movement to move horizontally.
	 * This movement alternation repeats until the Destination is reached or the Car gets stuck and no longer moves.
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
		
		Cell north = current.getNorth();
		Cell east = current.getEast();
		Cell south = current.getSouth();
		Cell west = current.getWest();
		
		// Using Flags, move Car towards a direction until an Intersection is reached
		if(this.moveNorthFlag) {
			if(north.isTypeIntersection()) {
				this.moveNorthFlag = false;
			}
			return north;
		} else if(this.moveEastFlag) {
			if(east.isTypeIntersection()) {
				this.moveEastFlag = false;
			}
			return east;
		} else if(this.moveSouthFlag) {
			if(south.isTypeIntersection()) {
				this.moveSouthFlag = false;
			}
			return south;
		} else if(this.moveWestFlag) {
			if(west.isTypeIntersection()) {
				this.moveWestFlag = false;
			}
			return west;
		}
		
		if(this.movingHorizontally) {
			
			// Try to move horizontally first
			if (current.getCol() < destination.getCol()) {
				if(east != null && east.canDriveOn()) {
					this.moveEastFlag = true;
					return east;
				}
			}
			
			if(current.getCol() > destination.getCol()) {
				if(west != null && west.canDriveOn()) {
					this.moveWestFlag = true;
					return west;
				}
				
			}
			
			// If you cannot move horizontally, then move vertically
			this.movingHorizontally = false;
		} else {
			// Move vertically
			if(current.getRow() < destination.getRow()) {
				if(north != null && north.canDriveOn()) {
					this.moveNorthFlag = true;
					return north;
				}
			}
			
			if (current.getRow() > destination.getRow()) {
				if (south != null && south.canDriveOn()) {
					this.moveSouthFlag = true;
					return south;
				}
			}
			
			// If you cannot move vertically, then move horizontally
			this.movingHorizontally = true;
		}
		
		if(this.movingHorizontally) {
			
			// Try to pick a horizontal direction
			if (current.getCol() < destination.getCol()) {
				if(east != null && east.canDriveOn()) {
					this.moveEastFlag = true;
					return east;
				}
			}
			
			if(current.getCol() > destination.getCol()) {
				if(west != null && west.canDriveOn()) {
					this.moveWestFlag = true;
					return west;
				}
				
			}
			
			// If you cannot pick a horizontal direction, then try a vertical direction
			this.movingHorizontally = false;
			
			// Try to pick a vertical direction
			if(current.getRow() < destination.getRow()) {
				if(north != null && north.canDriveOn()) {
					this.moveNorthFlag = true;
					return north;
				}
			}
			
			if (current.getRow() > destination.getRow()) {
				if (south != null && south.canDriveOn()) {
					this.moveSouthFlag = true;
					return south;
				}
			}
			
			// If you cannot pick a vertical direction, then try a horizontal direction
			this.movingHorizontally = true;
		} else {
			
			// Try to pick a vertical direction
			if(current.getRow() < destination.getRow()) {
				if(north != null && north.canDriveOn()) {
					this.moveNorthFlag = true;
					return north;
				}
			}
			
			if (current.getRow() > destination.getRow()) {
				if (south != null && south.canDriveOn()) {
					this.moveSouthFlag = true;
					return south;
				}
			}
			
			// If you cannot pick a vertical direction, then try a horizontal direction
			this.movingHorizontally = true;
			
			// Try to pick a horizontal direction
			if (current.getCol() < destination.getCol()) {
				if(east != null && east.canDriveOn()) {
					this.moveEastFlag = true;
					return east;
				}
			}
			
			if(current.getCol() > destination.getCol()) {
				if(west != null && west.canDriveOn()) {
					this.moveWestFlag = true;
					return west;
				}
				
			}
		}
		
		// Car is stuck...
		return current;
	}
}