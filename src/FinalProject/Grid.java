package FinalProject;

import java.util.ArrayList;
import java.util.List;

public class Grid {
	private final int intersectionsPerRow;
	private final int roadLength;
	private final int size;
	
	private GridCell[][] cells;
	
	// Track cars on the grid, each cell can hold up to 2 cars
	private List<Car>[][] carMap;
	
	public Grid(int intersectionsPerRow, int roadLength) {
		this.intersectionsPerRow = intersectionsPerRow;
		this.roadLength = roadLength;
		this.size = intersectionsPerRow * (roadLength + 1);
		
		// Initialize roads and intersections on the grid
		this.cells = new GridCell[this.size][this.size];
		for (int row = 0; row < this.size; row++) {
			for (int col = 0; col < this.size; col++) {
				
				// If a cell has position (n,x), where n and x are multiples of (roadLength + 1), then the cell is an intersection between 2 roads.
				// If a cell has position (n,x) where either n or x are multiples of (roadLength + 1), then the cell is a road.
				// If none of the above applies, then the cell is empty.
				if (row % (roadLength + 1) == 0 && col % (roadLength + 1) == 0) {
					this.cells[row][col] = new GridCell(row, col, "intersection");
				} else if ((row % (roadLength + 1) == 0) || (col % (roadLength + 1) == 0)) {
					this.cells[row][col] = new GridCell(row, col, "road");
				} else {
					this.cells[row][col] = new GridCell(row, col, null);
				}
			}
		}
		
		// Setup carMap with empty Array Lists
		this.carMap = new List[this.size][this.size];
		for (int row = 0; row < this.size; row++) {
			for (int col = 0; col < this.size; col++) {
				this.carMap[row][col] = new ArrayList<>();
			}
		}
	}
	
	public void setCell(int row, int col, String type) {
		this.cells[row][col].setType(type);
	}
	
	public GridCell getCell(int row, int col) {
		return this.cells[row][col];
	}
	
	public int getSize() {
		return this.size;
	}
	
	/**
	 * Returns true if a car is present on the given cell with the same direction provided.
	 * If it returns true, then that means the cell cannot accept another car going that direction.
	 *
	 * @param row
	 * @param col
	 * @param direction
	 * @return
	 */
	public boolean carPresentSameDirection(int row, int col, String direction) {
		boolean output = false;
		for(Car car : this.carMap[row][col]){
			if(car.getFacingDirection().equals(direction)){
				output = true;
			}
		}
		return output;
	}
	
	public boolean canEnter(int row, int col, String direction) {
		
		// Retrieve the list of cars at the given cell on the grid
		List<Car> cars = this.carMap[row][col];
		
		if(cars.isEmpty()) {
			return true;
		}
		
		// Prevents there being more than 2 cars on the same cell
		if(cars.size() >= 2) {
			return false;
		}
		
		// Return true if there's 1 car on the cell facing the opposite direction
		String otherCarDirection = cars.get(0).getFacingDirection();
		return areOppositeDirections(direction, otherCarDirection);
	}
	
	private boolean areOppositeDirections(String firstDirection, String secondDirection) {
		if(firstDirection.equals("north") && secondDirection.equals("south")) {
			return true;
		} else if(firstDirection.equals("south") && secondDirection.equals("north")){
			return true;
		} else if(firstDirection.equals("east")  && secondDirection.equals("west")) {
			return true;
		} else if (firstDirection.equals("west")  && secondDirection.equals("east")){
			return true;
		}
		return false;
	}
	
	/**
	 * Returns true if successfully set car.
	 *
	 * @param car
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean setCar(Car car, int row, int col) {
		if(this.canEnter(row, col, car.getFacingDirection())) {
			this.carMap[row][col].add(car);
			return true;
		}
		return false;
	}
	
	public boolean moveCar(Car car, int previousRowPosition, int previousColPosition, String moveDirection) {
		int nextRowPosition = previousRowPosition;
		int nextColPosition = previousColPosition;
		
		// Move car to new position
		if(moveDirection.equals("north")) { // Move North
			nextRowPosition++;
		} else if (moveDirection.equals("east")) { // Move East
			nextColPosition++;
		} else if (moveDirection.equals("south")) { // Move South
			nextRowPosition--;
		} else if (moveDirection.equals("west")) { // Move West
			nextColPosition--;
		}
		
		// Prevent moving car out of the grid
		if (nextRowPosition < 0 || nextRowPosition >= this.size || nextColPosition < 0 || nextColPosition >= this.size) {
			return false;
		}
		
		
		if (canEnter(nextRowPosition, nextRowPosition, moveDirection)) {
			
			// Move car out of the current position on the grid
			carMap[previousRowPosition][previousColPosition].remove(car);
			
			// Move car to new position
			carMap[nextRowPosition][nextRowPosition].add(car);
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * Called once per simulation minute.
	 * Cars and buildings interact with this grid.
	 */
	public void update() {
		// ADD CODE HERE
	}
}