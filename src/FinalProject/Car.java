package FinalProject;

import Skeleton.Unit;
import Skeleton.SimulationInput;

public class Car extends Unit {
	private int rowPosition;
	private int colPosition;
	private String facingDirection;
	private int waitTime = 0;
	
	public Car(String name, SimulationInput input, int startRow, int startCol, String facingDirection) {
		super(name, input);
		this.rowPosition = startRow;
		this.colPosition = startCol;
		this.facingDirection = facingDirection;
	}
	
	public String getFacingDirection(){
		return this.facingDirection;
	}
	
	@Override
	public void performAction() {
		Grid grid = GridManager.getGrid(); // global grid reference
		
		int nextRow = rowPosition + facingDirection.dRow;
		int nextCol = colPosition + facingDirection.dCol;
		
		GridCell nextCell = grid.getCell(nextRow, nextCol);
		
		// Can't move on empty cells
		if (nextCell.getType() != null) {
			
			// Check if another car is already there
			if (!grid.carPresent(nextRow, nextCol)) {
				// Move
				grid.moveCar(this, rowPosition, colPosition, nextRow, nextCol);
				rowPosition = nextRow;
				colPosition = nextCol;
			} else {
				// blocked by another car
				this.waitTime++;
			}
			
		} else {
			// Cannot move on empty cells
			this.waitTime++;
		}
	}
	
	@Override
	public void submitStatistics() {
		// Example: submit wait time each tick
		this.getStats().getStatistic("CarWaitTime").addValue(this.waitTime);
	}
}