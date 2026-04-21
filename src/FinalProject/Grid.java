package FinalProject;

import FinalProject.Observer.Stoplight;
import Skeleton.SimulationInput;

import java.util.ArrayList;

// Keeps track of the cells and which are roads, buildings and intersections
public class Grid {
	
	private final Cell[][] cells;
	private final int size;
	
	public Grid(SimulationInput input) {
		
		// Retrieve size inputs
		int intersectionsPerRow = input.getIntegerInput("GridIntersectionsPerRow");
		int roadLength = input.getIntegerInput("GridRoadLength");
		
		// Ensure minimum values are good
		if (intersectionsPerRow < 2) {
			throw new IllegalArgumentException("Error: GridIntersectionsPerRow has minimum value of 2.");
		} else if (roadLength < 3) {
			throw new IllegalArgumentException("Error: GridRoadLength has minimum value of 3.");
		}
		
		// Calculate grid size from inputs
		this.size = roadLength * (intersectionsPerRow - 1) + intersectionsPerRow;
		
		// Initialize all cells in the grid
		this.cells = new Cell[this.size][this.size];
		
		for (int row = 0; row < this.size; row++) {
			
			for (int col = 0; col < this.size; col++) {
				
				boolean rowContainsIntersections = row % (roadLength + 1) == 0;
				boolean colContainsIntersections = col % (roadLength + 1) == 0;
				
				if (rowContainsIntersections && colContainsIntersections) {
					
					// Setup Cell of type "intersection"
					Cell cell = new Cell(row, col, "intersection", this);
					this.cells[row][col] = cell;
					
					// Create and assign an Intersection to the Cell
					Intersection intersection = new Intersection(input);
					if (!cell.setIntersection(intersection)) {
						throw new IllegalArgumentException("Error: Cannot assign an Intersection to row = " + cell.getRow() + " and col = " + cell.getCol() + ".");
					}
					
				} else if (rowContainsIntersections || colContainsIntersections) {
					
					// Setup Cell of type "road"
					this.cells[row][col] = new Cell(row, col, "road", this);
				} else {
					// Setup Cell of type "none"
					this.cells[row][col] = new Cell(row, col, "none", this);
				}
			}
		}
		
		// Print out the grid layout visually
		this.printGrid();
	}
	
	/**
	 * Retrieve the Cell from the given row and col in the grid.
	 * Returns null if the row or col inputs are out of bounds.
	 * The row and col values must be between 0 and (n-1) inclusively, where n is the size of this grid.
	 *
	 * @param row row of the Cell being requested
	 * @param col column of the Cell being requested
	 * @return the requested Cell, or null value
	 */
	public Cell getCell(int row, int col) {
		if (row < 0 || col < 0 || row >= this.size || col >= this.size) {
			return null;
		}
		return this.cells[row][col];
	}
	
	/**
	 * Searches through all the Cells in the Grid.
	 * Finds all the cells of type "intersection".
	 * Then finds that Cell's assigned intersection.
	 * Then retrieves that intersection's assigned Stoplight to add to the output array.
	 *
	 * @return an ArrayList containing all the Stoplights in the grid
	 */
	public ArrayList<Stoplight> getStoplights() {
		ArrayList<Stoplight> stoplights = new ArrayList<>();
		for (Cell[] cell : this.cells) {
			for (int col = 0; col < this.cells.length; col++) {
				if (cell[col].isTypeIntersection()) {
					stoplights.add(cell[col].getIntersection().getStoplight());
				}
			}
		}
		return stoplights;
	}
	
	/**
	 * Prints out the grid visually.
	 * Each cell of type "intersection" is represented as I.
	 * Each cell of type "road" is represented as R.
	 * Each cell of type "none" is represented as a period.
	 */
	public void printGrid() {
		System.out.println("Printing out a Grid of size " + this.size + ":");
		
		for (int row = 0; row < this.size; row++) {
			for (int col = 0; col < this.size; col++) {
				Cell c = this.cells[row][col];
				if (c.isTypeIntersection()) {
					System.out.print(" I ");
				} else if (c.isTypeRoad()) {
					System.out.print(" R ");
				} else {
					System.out.print(" . ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}
}